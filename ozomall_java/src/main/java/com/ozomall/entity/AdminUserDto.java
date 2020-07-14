package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@TableName("admin_user")
@Data
public class AdminUserDto {
    /**
     * 主键
     * */
    @TableId(type = IdType.AUTO)
    private long id;
    /**
     * 用户名
     * */
    private String userName;
    /**
     * 密码
     * */
    @JsonIgnore
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
