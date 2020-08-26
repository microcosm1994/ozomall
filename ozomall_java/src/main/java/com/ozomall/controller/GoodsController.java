package com.ozomall.controller;

import com.ozomall.entity.*;
import com.ozomall.service.GoodsAttrService;
import com.ozomall.service.GoodsService;
import com.ozomall.utils.ResultGenerate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@ResponseBody
@RequestMapping("/goods")
@Api(tags = "商品管理接口")
public class GoodsController {
    @Resource
    GoodsService goodsService;

    @Resource
    GoodsAttrService goodsAttrService;

    /**
     * 商品
     */
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

    @ApiOperation("上架/下架商品")
    @PostMapping("/handle")
    public Result handleGoods(@RequestBody GoodsDto form) {
        return goodsService.handleGoods(form);
    }

    @ApiOperation("删除商品信息")
    @PostMapping("/del")
    public Result delGoods(@RequestBody GoodsDto form) {
        return goodsService.delGoods(form);
    }

    @ApiOperation("上传商品封面")
    @PostMapping("/uploadCover")
    public Result uploadCover(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null || file.getSize() <= 0) {
            return ResultGenerate.genErroResult("文件不能为空");
        } else {
            return goodsService.uploadCover(file);
        }
    }

    @ApiOperation("上传商品图片")
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("goodsId") int goodsId) throws IOException {
        if (file == null || file.getSize() <= 0) {
            return ResultGenerate.genErroResult("文件不能为空");
        } else {
            return goodsService.upload(file, goodsId);
        }
    }

    @ApiOperation("获取商品图片")
    @GetMapping("/getGoodsPic")
    public Result getGoodsPic(GoodsPicDto form) {
        return goodsService.getGoodsPic(form);
    }

    @ApiOperation("删除商品图片")
    @PostMapping("/delGoodsPic")
    public Result delGoodsPic(@RequestBody GoodsPicDto form) {
        return goodsService.delGoodsPic(form);
    }

    @ApiOperation("上传商品详情图片")
    @PostMapping("/detailsUpload")
    public Result detailsUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null || file.getSize() <= 0) {
            return ResultGenerate.genErroResult("文件不能为空");
        } else {
            return goodsService.detailsUpload(file);
        }
    }


    /**
     * 属性
     */
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
     * sku
     */
    @ApiOperation("添加商品价格")
    @PostMapping("/addGoodsSku")
    public Result addGoodsSku(@RequestBody GoodsSkuDto form) {
        return goodsAttrService.addGoodsSku(form);
    }

    @ApiOperation("上传sku展示图片")
    @PostMapping("/goodsSkuUpload")
    public Result goodsSkuUpload(MultipartFile file) throws IOException {
        return goodsAttrService.goodsSkuUpload(file);
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

    /**
     * 品牌
     */
    @ApiOperation("添加品牌")
    @PostMapping("/addGoodsBrand")
    public Result addGoodsBrand(@RequestBody GoodsBrandDto form) {
        return goodsService.addGoodsBrand(form);
    }

    @ApiOperation("上传品牌logo")
    @PostMapping("/uploadGoodsBrand")
    public Result uploadGoodsBrand(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null || file.getSize() <= 0) {
            return ResultGenerate.genErroResult("文件不能为空");
        } else {
            return goodsService.uploadGoodsBrand(file);
        }
    }

    @ApiOperation("获取品牌列表")
    @GetMapping("/getGoodsBrand")
    public Result getGoodsBrand(GoodsBrandDto form) {
        return goodsService.getGoodsBrand(form);
    }

    @ApiOperation("修改品牌")
    @PostMapping("/putGoodsBrand")
    public Result putGoodsBrand(@RequestBody GoodsBrandDto form) {
        return goodsService.putGoodsBrand(form);
    }

    @ApiOperation("删除品牌")
    @PostMapping("/delGoodsBrand")
    public Result delGoodsBrand(@RequestBody GoodsBrandDto form) {
        return goodsService.delGoodsBrand(form);
    }
}
