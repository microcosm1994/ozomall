package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;

@ApiModel
@TableName("goods")
@Data
@Document(indexName = "ozomall", type = "goods", shards = 1, replicas = 0)
public class GoodsDto extends PageReqDto {
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "商品名称")
    @Field(type = FieldType.Text)
    private String goodsName;

    @ApiModelProperty(value = "商品价格")
    @Field(type = FieldType.Text)
    private String goodsPrice;

    @ApiModelProperty(value = "商品评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "商品销量")
    @Field(type = FieldType.Long)
    private Integer sales;

    @ApiModelProperty(value = "商品1级分类id")
    private Integer classify1Id;

    @ApiModelProperty(value = "商品2级分类id")
    private Integer classify2Id;

    @ApiModelProperty(value = "商品3级分类id")
    private Integer classify3Id;

    @ApiModelProperty(value = "商品1级分类id名称")
    @Field(type = FieldType.Text)
    private String classify1Name;

    @ApiModelProperty(value = "商品2级分类id名称")
    @Field(type = FieldType.Text)
    private String classify2Name;

    @ApiModelProperty(value = "商品3级分类id名称")
    @Field(type = FieldType.Text)
    private String classify3Name;

    @ApiModelProperty(value = "商品品牌id")
    private Integer brandId;

    @ApiModelProperty(value = "商品品牌名称")
    @Field(type = FieldType.Text)
    private String brandName;

    @ApiModelProperty(value = "商品创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp createTime;

    @ApiModelProperty(value = "删除标记")
    private Integer del;

    @ApiModelProperty(value = "商品状态:0下架；1上架；")
    private Integer status;

    @ApiModelProperty(value = "填写商品步骤")
    private Integer step;

    @ApiModelProperty(value = "商品封面")
    private String cover;

    @ApiModelProperty(value = "商品详情")
    @Field(type = FieldType.Text)
    private String details;
}
