package com.ozomall.vo;

import com.ozomall.entity.AdminClassifyDto;
import com.ozomall.entity.GoodsDto;
import lombok.Data;

@Data
public class GoodsVo extends GoodsDto {
    private AdminClassifyDto classify;
}
