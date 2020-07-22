package com.ozomall.controller.admin;

import com.ozomall.entity.*;
import com.ozomall.service.GoodsAttrService;
import com.ozomall.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@ResponseBody
@RequestMapping("/admin/goods")
@Api(tags = "商品管理接口")
public class GoodsController {
    @Resource
    GoodsService goodsService;

    @Resource
    GoodsAttrService goodsAttrService;
    /**
     * 商品
     * */
    @ApiOperation("添加商品信息")
    @PostMapping("/add")
    public Result addGoods(@RequestBody GoodsDto form) {
        return goodsService.addGoods(form);
    }

    @ApiOperation("获取商品列表")
    @GetMapping("/list")
    public Result goodsList(GoodsDto form) {
        return goodsService.goodsList(form);
    }

    @ApiOperation("根据id获取商品信息")
    @GetMapping("/get")
    public Result getGoods(int id) {
        return goodsService.getGoods(id);
    }

    @ApiOperation("修改商品信息")
    @PostMapping("/put")
    public Result putGoods(@RequestBody GoodsDto form) {
        return goodsService.putGoods(form);
    }

    @ApiOperation("删除商品信息")
    @PostMapping("/del")
    public Result delGoods(@RequestBody GoodsDto form) {
        return goodsService.delGoods(form);
    }


    /**
     * 属性
     * */
    @ApiOperation("添加商品属性")
    @PostMapping("/addGoodsAttr")
    public Result addGoodsAttr(@RequestBody GoodsAttrDto form) {
        return goodsAttrService.addGoodsAttr(form);
    }

    @ApiOperation("获取商品属性")
    @GetMapping("/getGoodsAttr")
    public Result getGoodsAttr(GoodsAttrDto form) {
        return goodsAttrService.getGoodsAttr(form);
    }

    @ApiOperation("删除商品属性")
    @PostMapping("/delGoodsAttr")
    public Result delGoodsAttr(@RequestBody GoodsAttrDto form) {
        return goodsAttrService.delGoodsAttr(form);
    }

    @ApiOperation("添加商品属性值")
    @PostMapping("/addGoodsAttrVal")
    public Result addGoodsAttrVal(@RequestBody GoodsAttrValDto form) {
        return goodsAttrService.addGoodsAttrVal(form);
    }

    @ApiOperation("删除商品属性值")
    @PostMapping("/delGoodsAttrVal")
    public Result delGoodsAttrVal(@RequestBody GoodsAttrValDto form) {
        return goodsAttrService.delGoodsAttrVal(form);
    }

    /**
     * 价格
     * */
    @ApiOperation("添加商品价格")
    @PostMapping("/addGoodsSku")
    public Result addGoodsSku(@RequestBody GoodsSkuDto form) {
        return goodsAttrService.addGoodsSku(form);
    }

    @ApiOperation("获取商品价格")
    @GetMapping("/getGoodsSkuList")
    public Result getGoodsSkuList(GoodsSkuDto form) {
        return goodsAttrService.getGoodsSkuList(form);
    }

    @ApiOperation("修改商品价格")
    @PostMapping("/putGoodsSku")
    public Result putGoodsSku(@RequestBody GoodsSkuDto form) {
        return goodsAttrService.putGoodsSku(form);
    }

    @ApiOperation("删除商品价格")
    @PostMapping("/delGoodsSku")
    public Result delGoodsSku(@RequestBody GoodsSkuDto form) {
        return goodsAttrService.delGoodsSku(form);
    }

    @ApiOperation("添加商品参数")
    @PostMapping("/addGoodsParams")
    public Result addGoodsParams(@RequestBody GoodsParamsDto form) {
        return goodsAttrService.addGoodsParams(form);
    }

    @ApiOperation("获取商品参数")
    @GetMapping("/getGoodsParams")
    public Result getGoodsParams(GoodsParamsDto form) {
        return goodsAttrService.getGoodsParams(form);
    }

    @ApiOperation("修改商品参数")
    @PostMapping("/putGoodsParams")
    public Result putGoodsParams(@RequestBody GoodsParamsDto form) {
        return goodsAttrService.putGoodsParams(form);
    }

    @ApiOperation("删除商品参数")
    @PostMapping("/delGoodsParams")
    public Result delGoodsParams(@RequestBody GoodsParamsDto form) {
        return goodsAttrService.delGoodsParams(form);
    }

}
