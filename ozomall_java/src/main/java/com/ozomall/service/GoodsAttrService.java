package com.ozomall.service;

import com.ozomall.entity.GoodsAttrDto;
import com.ozomall.entity.GoodsAttrValDto;
import com.ozomall.entity.Result;

public interface GoodsAttrService {

    /**
     * 添加商品属性
     */
    Result addGoodsAttr(GoodsAttrDto form);


    /**
     * 获取商品属性
     */
    Result getGoodsAttr(GoodsAttrDto form);

    /**
     * 删除商品属性
     */
    Result delGoodsAttr(GoodsAttrDto form);


    /**
     * 添加商品属性值
     */
    Result addGoodsAttrVal(GoodsAttrValDto form);


    /**
     * 删除商品属性值
     */
    Result delGoodsAttrVal(GoodsAttrValDto form);
}
