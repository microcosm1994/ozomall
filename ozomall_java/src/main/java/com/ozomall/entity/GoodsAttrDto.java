package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
@TableName("goods_attr")
public class GoodsAttrDto {
    @ApiModelProperty(value = "商品属性id")
    @TableId(type = IdType.AUTO)
    private int id;
    @ApiModelProperty(value = "关联的商品id")
    private int goodsId;
    @ApiModelProperty(value = "属性名称")
    private String name;
}
