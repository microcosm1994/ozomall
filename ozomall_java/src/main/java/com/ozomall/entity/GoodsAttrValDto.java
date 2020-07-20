package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("goods_attr_val")
@Data
public class GoodsAttrValDto {
    @ApiModelProperty(value = "商品属性值id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "关联商品属性id")
    private int goodsAttrId;

    @ApiModelProperty(value = "属性值")
    private String value;
}
