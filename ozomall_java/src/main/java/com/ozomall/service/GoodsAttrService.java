package com.ozomall.service;

import com.ozomall.entity.*;

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

    /**
     * 添加商品价格
     */
    Result addGoodsSku(GoodsSkuDto form);

    /**
     * 获取商品价格
     */
    Result getGoodsSkuList(GoodsSkuDto form);

    /**
     * 修改商品价格
     */
    Result putGoodsSku(GoodsSkuDto form);

    /**
     * 删除商品价格
     */
    Result delGoodsSku(GoodsSkuDto form);

    /**
     * 添加商品参数
     */
    Result addGoodsParams(GoodsParamsDto form);

    /**
     * 获取商品参数
     */
    Result getGoodsParams(GoodsParamsDto form);


    /**
     * 修改商品参数
     */
    Result putGoodsParams(GoodsParamsDto form);


    /**
     * 删除商品参数
     */
    Result delGoodsParams(GoodsParamsDto form);
}
