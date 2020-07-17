package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@ApiModel
@Data
public class PageReqDto {
    @Transient
    @JsonIgnore
    @TableField(exist = false, select = false)
    private int page;

    @Transient
    @JsonIgnore
    @TableField(exist = false, select = false)
    private int size;
}
