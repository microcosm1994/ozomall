package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("sys_users")
@ApiModel
@Data
public class UserDto extends PageReqDto {
    @ApiModelProperty(value = "用户id")
    @TableId(type = IdType.AUTO)
    private long id;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String passWord;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户权限id")
    private Integer roleId;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户头像")
    private String avator;

    @ApiModelProperty(value = "用户类型 0：管理系统用户；1：商城用户；")
    private Integer type;
}
