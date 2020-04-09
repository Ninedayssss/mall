package com.weimok.common.api.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author itsNine
 * @create 2020-04-04-15:48
 */
@Data
public class UserVo implements Serializable {
    private Long id;

    private String username;
    private String password;
    private Date created;
    private String salt;
}
