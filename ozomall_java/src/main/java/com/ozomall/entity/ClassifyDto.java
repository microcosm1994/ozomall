package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@ApiModel
@TableName("classify")
@Data
public class ClassifyDto extends PageReqDto {
    @ApiModelProperty(value = "分类id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "父级id")
    private Integer parentId;
    @ApiModelProperty(value = "分类名称")
    private String name;
    @ApiModelProperty(value = "分类级别")
    private Integer classifyLevel;
    @ApiModelProperty(value = "分类图片")
    private String url;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "删除标记")
    private int del;
}
