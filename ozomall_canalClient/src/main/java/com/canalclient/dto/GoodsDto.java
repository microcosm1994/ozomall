package com.canalclient.dto;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;

@Data
@Document(indexName = "ozomall", type = "goods", shards = 1, replicas = 0)
public class GoodsDto {
    private int id;

    @Field(type = FieldType.Text)
    private String goodsName;

    @Field(type = FieldType.Text)
    private String goodsPrice;

    private Integer commentCount;

    private Integer sales;

    private Integer classify1Id;

    private Integer classify2Id;

    private Integer classify3Id;

    @Field(type = FieldType.Text)
    private String classify1Name;

    @Field(type = FieldType.Text)
    private String classify2Name;

    @Field(type = FieldType.Text)
    private String classify3Name;

    private Integer brandId;

    @Field(type = FieldType.Text)
    private String brandName;

    private Timestamp createTime;

    private Integer del;

    private Integer status;

    private Integer step;

    private String cover;

    @Field(type = FieldType.Text)
    private String details;
}
