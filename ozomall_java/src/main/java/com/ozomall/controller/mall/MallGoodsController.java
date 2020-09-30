package com.ozomall.controller.mall;

import com.ozomall.entity.GoodsDto;
import com.ozomall.entity.Result;
import com.ozomall.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/mall/goods")
@ResponseBody
@Api(tags = "商城商品相关接口")
public class MallGoodsController {

    @Resource
    GoodsService goodsService;

    @ApiOperation("获取商品列表")
    @GetMapping("/list")
    public Result goodsList(GoodsDto form) {
        return goodsService.mallGoodsList(form);
    }

    @ApiOperation("获取商品数量")
    @GetMapping("/getGoodsCount")
    public Result getGoodsCount(GoodsDto form) {
        form.setStatus(1);
        return goodsService.getGoodsCount(form);
    }

    @ApiOperation("搜索商品")
    @GetMapping("/search")
    public Result searchGoods(GoodsDto form) {
        form.setStatus(1);
        return goodsService.searchGoods(form);
    }
}
