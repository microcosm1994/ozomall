package com.ozomall.service;

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
     * 添加商品信息
     */
    Result goodsList(GoodsDto form);

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
}
