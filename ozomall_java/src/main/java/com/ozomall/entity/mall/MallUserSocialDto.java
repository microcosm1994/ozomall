package com.ozomall.entity.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("mall_users_social")
@ApiModel
@Data
public class MallUserSocialDto {
    @ApiModelProperty(value = "用户id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "userId")
    private Integer userId;

    @ApiModelProperty(value = "socialType")
    private Integer socialType;
}
