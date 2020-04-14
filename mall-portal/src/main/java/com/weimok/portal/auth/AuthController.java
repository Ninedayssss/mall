package com.weimok.portal.auth;

import com.weimok.auth.api.AuthService;
import com.weimok.auth.entity.UserInfo;
import com.weimok.auth.utils.JwtUtils;
import com.weimok.portal.config.JwtProperties;
import com.weimok.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @author itsNine
 * @create 2020-04-10-10:54
 */
@RestController
@RequestMapping("/auth")
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {
    @Reference(version = "1.0.0")
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 登录 并将用户登录信息写入cookie
     * @param username
     * @param password
     * @param response
     * @param request
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<String> login(
            @RequestParam("username")String username, @RequestParam("password")String password,
            HttpServletResponse response, HttpServletRequest request
    ){
        String token = authService.login(username, password);

        if (StringUtils.isBlank(token)){
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        }
        //将token写入cookie，指定httpOnly为true，防止通过js获取和修改
        CookieUtils.newBuilder(response).httpOnly().request(request).build(jwtProperties.getCookieName(),token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("verify")
    public ResponseEntity<UserInfo> verifyUser(@CookieValue("MALL_TOKEN")String token,
                                               HttpServletRequest request, HttpServletResponse response){
        try {
            //从token中解析token信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            //解析成功重新刷新token
            String newToken = JwtUtils.generateToken(userInfo, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());
            //更新cookie中的token
            CookieUtils.newBuilder(response).httpOnly().request(request).build(jwtProperties.getCookieName(),newToken);

            //返回解析成功的用户信息
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //出现异常
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
