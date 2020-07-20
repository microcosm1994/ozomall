package com.ozomall.controller.admin;

import com.ozomall.entity.GoodsAttrDto;
import com.ozomall.entity.GoodsAttrValDto;
import com.ozomall.entity.GoodsDto;
import com.ozomall.entity.Result;
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
}
