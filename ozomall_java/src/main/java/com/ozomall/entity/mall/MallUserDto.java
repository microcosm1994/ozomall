package com.ozomall.entity.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ozomall.entity.PageReqDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("mall_users")
@ApiModel
@Data
public class MallUserDto extends PageReqDto {
    @ApiModelProperty(value = "用户id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "是否vip：0否，1是")
    private Integer vip;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;

    @ApiModelProperty(value = "性别0未知；1男；2女")
    private Integer gender;

    @ApiModelProperty(value = "用户签名")
    private String sign;

    @TableField(exist = false)
    private String code;
}
