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
}
