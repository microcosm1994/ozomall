package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@TableName("goods_sku")
@Data
public class GoodsSkuDto {
    @ApiModelProperty(value = "sku id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "关联商品id")
    private int goodsId;

    @ApiModelProperty(value = "关联属性 id1")
    private int spe1Id;

    @ApiModelProperty(value = "关联属性 id2")
    private int spe2Id;

    @ApiModelProperty(value = "关联属性 id3")
    private int spe3Id;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    private int stock;

    @ApiModelProperty(value = "销量")
    private int sales;

    @ApiModelProperty(value = "展示图片")
    private String pic;
}
