package com.weimok.user.service;

import com.weimok.common.api.user.UserService;
import com.weimok.common.api.user.vo.UserVo;
import com.weimok.common.enums.ExceptionEnum;
import com.weimok.common.exception.MallException;
import com.weimok.common.utils.CodecUtils;
import com.weimok.user.mapper.UserMapper;
import com.weimok.user.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author itsNine
 * @create 2020-03-17-14:41
 */
@Service(version = "1.0.0")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void register(UserVo userModel){
        User user = new User();
        //生成盐
        String salt = CodecUtils.generate();
        user.setSalt(salt);

        user.setUsername(userModel.getUsername());
        user.setPassword(userModel.getPassword());

        //密码加密
        user.setPassword(CodecUtils.encryptPassword(user.getPassword(),salt));

        //持久化
        user.setCreated(new Date());
        userMapper.insert(user);
    }

    public UserVo queryUserByUsernameAndPassword(String username, String password){
        //查询用户
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);

        //检验用户是否存在
        if (user == null){
            throw new MallException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

        //检验密码是否正确
        if (!StringUtils.equals(user.getPassword()
                ,CodecUtils.encryptPassword(password,user.getSalt()))){
            throw new MallException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

        //将查找出来的值返回
        UserVo userVo = new UserVo();
        userVo.setUsername(user.getUsername());
        userVo.setPassword(user.getPassword());



        return userVo;
    }

}
