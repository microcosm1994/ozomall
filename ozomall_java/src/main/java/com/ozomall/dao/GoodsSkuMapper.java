package com.ozomall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ozomall.entity.GoodsSkuDto;

public interface GoodsSkuMapper extends BaseMapper<GoodsSkuDto> {
    int reduceStock(int id, int num);
}
