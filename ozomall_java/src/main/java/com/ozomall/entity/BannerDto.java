package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("banner")
public class BannerDto {
    @ApiModelProperty(value = "banner id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "图片链接")
    private String url;

    @ApiModelProperty(value = "跳转链接")
    private String link;
}
