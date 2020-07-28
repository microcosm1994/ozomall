package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@ApiModel
@TableName("goods")
@Data
public class GoodsDto extends PageReqDto {
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品价格")
    private String goodsPrice;

    @ApiModelProperty(value = "商品评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "商品销量")
    private Integer sales;

    @ApiModelProperty(value = "商品1级分类id")
    private Integer classify1Id;

    @ApiModelProperty(value = "商品2级分类id")
    private Integer classify2Id;

    @ApiModelProperty(value = "商品3级分类id")
    private Integer classify3Id;

    @ApiModelProperty(value = "商品品牌id")
    private Integer brandId;

    @ApiModelProperty(value = "商品创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "删除标记")
    private Integer del;

    @ApiModelProperty(value = "商品状态")
    private Integer status;

    @ApiModelProperty(value = "填写商品步骤")
    private Integer step;

    @ApiModelProperty(value = "商品封面")
    private String cover;

    @ApiModelProperty(value = "商品详情")
    private String details;
}
