package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("goods_pic")
public class GoodsPicDto {
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "关联商品id")
    private int goodsId;

    @ApiModelProperty(value = "图片链接")
    private String url;

    @ApiModelProperty(value = "图片名称")
    private String name;
}
