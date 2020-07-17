package com.ozomall.service;

import com.ozomall.entity.GoodsDto;
import com.ozomall.entity.Result;

public interface GoodsService {
    /**
     * 添加商品信息
     */
    Result addGoods(GoodsDto form);

    /**
     * 添加商品信息
     */
    Result goodsList(GoodsDto form);

    /**
     * 根据id查询商品信息
     */
    Result getGoods(int id);
}
