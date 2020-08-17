package com.ozomall.entity.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("mall_users_setting")
public class MallUserSettingDto {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "关联用户id")
    private Integer userId;

    @ApiModelProperty(value = "默认地址id")
    private Integer addressId;

}
