package com.ozomall.vo;

import com.ozomall.entity.ClassifyDto;
import com.ozomall.entity.GoodsDto;
import lombok.Data;

@Data
public class GoodsVo extends GoodsDto {
    private ClassifyDto classify1;
    private ClassifyDto classify2;
    private ClassifyDto classify3;
}
