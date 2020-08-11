package com.ozomall.service;

import com.ozomall.entity.GoodsBrandDto;
import com.ozomall.entity.GoodsDto;
import com.ozomall.entity.GoodsPicDto;
import com.ozomall.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GoodsService {
    /**
     * 添加商品信息
     */
    Result addGoods(GoodsDto form);

    /**
     * 获取商品列表
     */
    Result goodsList(GoodsDto form);

    /**
     * 商城商品列表
     */
    Result mallGoodsList(GoodsDto form);

    /**
     * 搜索商品
     */
    Result searchGoods(GoodsDto form);

    /**
     * 根据id查询商品信息
     */
    Result getGoods(int id);

    /**
     * 修改商品信息
     */
    Result putGoods(GoodsDto form);

    /**
     * 删除商品信息
     */
    Result delGoods(GoodsDto form);

    /**
     * 上传商品封面
     */
    Result uploadCover(MultipartFile file) throws IOException;

    /**
     * 上传商品图片
     */
    Result upload(MultipartFile file, int goodsId) throws IOException;

    /**
     * 获取商品图片
     */
    Result getGoodsPic(GoodsPicDto form);

    /**
     * 删除商品图片
     */
    Result delGoodsPic(GoodsPicDto form);

    /**
     * 上传商品详情图片
     */
    Result detailsUpload(MultipartFile file) throws IOException;

    /**
     * 添加品牌
     */
    Result addGoodsBrand(GoodsBrandDto form);

    /**
     * 上传品牌logo
     */
    Result uploadGoodsBrand(MultipartFile file) throws IOException;

    /**
     * 获取品牌列表
     */
    Result getGoodsBrand(GoodsBrandDto form);

    /**
     * 修改品牌
     */
    Result putGoodsBrand(GoodsBrandDto form);
    /**
     * 获取品牌列表
     */
    Result delGoodsBrand(GoodsBrandDto form);
}
