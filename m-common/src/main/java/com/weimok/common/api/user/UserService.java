package com.weimok.common.api.user;
import com.weimok.common.api.user.vo.UserVo;

/**
 * @author itsNine
 * @create 2020-04-04-15:46
 */
public interface UserService {
    void register(UserVo user);

    UserVo queryUserByUsernameAndPassword(String username,String password);
}
