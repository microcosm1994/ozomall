package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@TableName("admin_classify")
@Data
public class AdminClassifyDto extends PageReqDto {
    @TableId(type = IdType.AUTO)
    private int id;
    private int parentId;
    private String name;
    private int classifyLevel;
    private Timestamp createTime;
    private int del;
}
