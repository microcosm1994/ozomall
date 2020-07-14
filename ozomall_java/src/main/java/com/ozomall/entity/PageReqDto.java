package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PageReqDto {
    @JsonIgnore
    @TableField(exist = false, select = false)
    private int page;

    @JsonIgnore
    @TableField(exist = false, select = false)
    private int size;
}
