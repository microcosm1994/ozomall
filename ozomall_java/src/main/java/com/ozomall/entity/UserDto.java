package com.ozomall.entity;
import lombok.*;

@Data
public class UserDto {
    /**
     * 主键
     * */
    private long id;
    /**
     * 用户名
     * */
    private String userName;
    /**
     * 密码
     * */
    private String passWord;
    /**
     * 用户昵称
     * */
    private String nickName;
    /**
     * 用户权限
     * */
    private Integer role;
    /**
     * 用户手机号
     * */
    private String phone;
}
