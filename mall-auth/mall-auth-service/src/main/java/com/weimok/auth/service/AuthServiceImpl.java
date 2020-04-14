package com.weimok.auth.service;

import com.weimok.auth.api.AuthService;
import com.weimok.auth.entity.UserInfo;
import com.weimok.auth.utils.JwtUtils;
import com.weimok.common.api.user.UserService;
import com.weimok.common.api.user.vo.UserVo;
import com.weimok.common.enums.ExceptionEnum;
import com.weimok.common.exception.MallException;
import com.weimok.auth.config.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author itsNine
 * @create 2020-04-09-17:48
 */
@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
@Service(version = "1.0.0")
public class AuthServiceImpl implements AuthService {

    @Reference(version = "1.0.0")
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String login(String username, String password) {
        try {
            //验证用户名密码
            UserVo userVo = userService.queryUserByUsernameAndPassword(username, password);
            if (userVo == null) {
                throw new MallException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
            }

            System.out.println("userid:"+userVo.getId());
            //生成token
            String token = JwtUtils.generateToken(new UserInfo(userVo.getId(), username), jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            return token;
        } catch (Exception e) {
            log.error("生成token失败，用户名称：{}",username,e);
            throw new MallException(ExceptionEnum.CREATE_TOKEN_ERROR);
        }
    }
}
