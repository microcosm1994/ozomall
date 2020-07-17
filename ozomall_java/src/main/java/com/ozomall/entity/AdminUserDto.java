package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("admin_user")
@ApiModel
@Data
public class AdminUserDto {
    @ApiModelProperty(value = "用户id")
    @TableId(type = IdType.AUTO)
    private long id;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    @JsonIgnore
    private String passWord;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户权限")
    private Integer role;

    @ApiModelProperty(value = "用户手机号")
    private String phone;
}
