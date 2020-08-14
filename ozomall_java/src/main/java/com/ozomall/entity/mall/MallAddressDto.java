package com.ozomall.entity.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("address")
public class MallAddressDto {
    @ApiModelProperty(value = "地址id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "关联用户id")
    private Integer userId;

    @ApiModelProperty(value = "收货人")
    private String consignee;

    @ApiModelProperty(value = "所在区域")
    private String region;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "区域码")
    private String areaCode;
}
