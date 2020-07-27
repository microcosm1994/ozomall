package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("goods_brand")
public class GoodsBrandDto extends PageReqDto {
    @ApiModelProperty(value = "品牌id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "品牌logo")
    private String url;
}
