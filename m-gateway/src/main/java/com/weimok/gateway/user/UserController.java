package com.weimok.gateway.user;

import com.weimok.common.api.user.UserService;
import com.weimok.common.api.user.vo.UserVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author itsNine
 * @create 2020-04-04-15:41
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Reference(version = "1.0.0")
    private UserService userService;

    //注册
    @PostMapping("register")
    public ResponseEntity<Void> register(UserVo user){
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //登录
    @GetMapping("/query")
    public ResponseEntity<UserVo> queryUserByUsernameAndPassword(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){
        return ResponseEntity.ok(userService.queryUserByUsernameAndPassword(username,password));
    }
}
