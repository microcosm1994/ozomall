package com.ozomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ozomall.dao.GoodsBrandMapper;
import com.ozomall.dao.GoodsMapper;
import com.ozomall.dao.GoodsPicMapper;
import com.ozomall.dao.mall.MallGoodsMapper;
import com.ozomall.entity.GoodsBrandDto;
import com.ozomall.entity.GoodsDto;
import com.ozomall.entity.GoodsPicDto;
import com.ozomall.entity.Result;
import com.ozomall.service.GoodsService;
import com.ozomall.utils.Oss;
import com.ozomall.utils.ResultGenerate;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsPicMapper goodsPicMapper;

    @Resource
    private GoodsBrandMapper goodsBrandMapper;

    @Resource
    private MallGoodsMapper mallGoodsMapper;

    /**
     * 添加商品信息
     *
     * @param form
     */
    @Override
    public Result addGoods(GoodsDto form) {
        int row = goodsMapper.insert(form);
        if (row > 0) {
            mallGoodsMapper.save(form);
            return ResultGenerate.genSuccessResult(form);
        } else {
            return ResultGenerate.genErroResult("商品信息添加失败，请重试！");
        }
    }

    /**
     * 获取商品列表
     *
     * @param form
     */
    @Override
    public Result goodsList(GoodsDto form) {
        Page page = new Page();
        page.setCurrent(form.getPage());
        page.setSize(form.getSize());
        IPage<Map> rows = goodsMapper.goodsList(page, form);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("商品信息添加失败，请重试！");
        }
    }

    /**
     * 搜索商品
     *
     * @param form
     */
    @Override
    public Result searchGoods(GoodsDto form) {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(QueryBuilders.matchQuery("goodsName", form.getGoodsName()));
        searchQueryBuilder.withPageable(PageRequest.of(form.getPage(), form.getSize()));
        org.springframework.data.domain.Page<GoodsDto> rows = mallGoodsMapper.search(searchQueryBuilder.build());
        if (rows != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("page", rows.getNumber());
            data.put("size", rows.getSize());
            data.put("total", rows.getTotalElements());
            data.put("records", rows.getContent());
            return ResultGenerate.genSuccessResult(data);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }

    /**
     * 商城商品列表
     *
     * @param form
     */
    @Override
    public Result mallGoodsList(GoodsDto form) {
        Page page = new Page();
        page.setCurrent(form.getPage());
        page.setSize(form.getSize());
        LambdaQueryWrapper<GoodsDto> wrapper = new LambdaQueryWrapper<>();
        Map<SFunction<GoodsDto, ?>, Object> map = new HashMap<>();
        map.put(GoodsDto::getClassify1Id, form.getClassify1Id());
        map.put(GoodsDto::getClassify2Id, form.getClassify2Id());
        map.put(GoodsDto::getClassify3Id, form.getClassify3Id());
        map.put(GoodsDto::getGoodsName, form.getGoodsName());
        map.put(GoodsDto::getDel, form.getDel());
        map.put(GoodsDto::getStatus, form.getStatus());
        wrapper.allEq(map, false);
        IPage<Map> rows = goodsMapper.selectPage(page, wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("商品信息添加失败，请重试！");
        }
    }

    /**
     * 获取商品数量
     *
     * @param form
     */
    @Override
    public Result getGoodsCount(GoodsDto form) {
        LambdaQueryWrapper<GoodsDto> wrapper = new LambdaQueryWrapper<>();
        Map<SFunction<GoodsDto, ?>, Object> map = new HashMap<>();
        map.put(GoodsDto::getBrandId, form.getBrandId());
        map.put(GoodsDto::getStatus, form.getStatus());
        map.put(GoodsDto::getClassify1Id, form.getClassify1Id());
        map.put(GoodsDto::getClassify2Id, form.getClassify2Id());
        map.put(GoodsDto::getClassify3Id, form.getClassify3Id());
        wrapper.allEq(map, false);
        int row = goodsMapper.selectCount(wrapper);
        return ResultGenerate.genSuccessResult(row);
    }

    /**
     * 根据id获取商品信息
     *
     * @param id
     */
    @Override
    public Result getGoods(int id) {
        GoodsDto rows = goodsMapper.getGoodsById(id);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("商品信息添加失败，请重试！");
        }
    }

    /**
     * 修改商品信息
     *
     * @param form
     */
    @Override
    public Result putGoods(GoodsDto form) {
        int rows = goodsMapper.updateById(form);
        if (rows > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("更新失败");
        }
    }

    /**
     * 删除商品信息
     *
     * @param form
     */
    @Override
    public Result delGoods(GoodsDto form) {
        LambdaQueryWrapper<GoodsDto> wrapper = new LambdaQueryWrapper();
        wrapper.eq(GoodsDto::getId, form.getId());
        GoodsDto query = new GoodsDto();
        query.setDel(1);
        int rows = goodsMapper.update(query, wrapper);
        if (rows > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("删除失败");
        }
    }

    /**
     * 上传商品封面
     *
     * @param file
     */
    @Override
    public Result uploadCover(MultipartFile file) throws IOException {
        Oss ossClient = new Oss();
        ossClient.init("ozomall-goods-pic", "goods/cover/");
        //将文件上传
        String name = ossClient.uploadImg2Oss(file);
        //获取文件的URl地址  以便前台  显示
        String imgUrl = ossClient.getImgUrl(name);
        ossClient.destory();
        Map<String, String> map = new HashMap();
        map.put("url", imgUrl);
        // 存储图片链接到数据库
        if (!StringUtils.isEmpty(imgUrl)) {
            return ResultGenerate.genSuccessResult(map);
        } else {
            return ResultGenerate.genErroResult("图片上传失败,请重新上传。");
        }
    }

    /**
     * 上传商品图片
     *
     * @param file
     */
    @Override
    public Result upload(MultipartFile file, int goodsId) throws IOException {
        Oss ossClient = new Oss();
        ossClient.init("ozomall-goods-pic", "goods/pics/");
        //将文件上传
        String name = ossClient.uploadImg2Oss(file);
        //获取文件的URl地址  以便前台  显示
        String imgUrl = ossClient.getImgUrl(name);
        ossClient.destory();
        GoodsPicDto pic = new GoodsPicDto();
        pic.setGoodsId(goodsId);
        pic.setUrl(imgUrl);
        pic.setName(name);
        // 存储图片链接到数据库
        int rows = goodsPicMapper.insert(pic);
        if (rows > 0) {
            return ResultGenerate.genSuccessResult(pic);
        } else {
            return ResultGenerate.genErroResult("图片上传失败,请重新上传。");
        }
    }

    /**
     * 获取商品图片
     *
     * @param form
     */
    @Override
    public Result getGoodsPic(GoodsPicDto form) {
        LambdaQueryWrapper<GoodsPicDto> wrapper = new LambdaQueryWrapper();
        wrapper.eq(GoodsPicDto::getGoodsId, form.getGoodsId());
        List<GoodsPicDto> rows = goodsPicMapper.selectList(wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("获取失败");
        }
    }

    /**
     * 删除商品图片
     *
     * @param form
     */
    @Override
    public Result delGoodsPic(GoodsPicDto form) {
        Oss ossClient = new Oss();
        ossClient.init("ozomall-goods-pic", "goods/pics/");
        int rows = goodsPicMapper.deleteById(form.getId());
        if (rows > 0) {
            ossClient.delPic(form.getName());
            ossClient.destory();
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("删除失败");
        }
    }

    /**
     * 上传商品详情图片
     *
     * @param file
     */
    @Override
    public Result detailsUpload(MultipartFile file) throws IOException {
        Oss ossClient = new Oss();
        ossClient.init("ozomall-goods-pic", "goods/details/");
        //将文件上传
        String name = ossClient.uploadImg2Oss(file);
        //获取文件的URl地址  以便前台  显示
        String imgUrl = ossClient.getImgUrl(name);
        ossClient.destory();
        if (!StringUtils.isEmpty(imgUrl)) {
            GoodsPicDto pic = new GoodsPicDto();
            pic.setUrl(imgUrl);
            pic.setName(name);
            return ResultGenerate.genSuccessResult(pic);
        } else {
            return ResultGenerate.genErroResult("图片上传失败,请重新上传。");
        }
    }


    /**
     * 添加品牌
     *
     * @param form
     */
    @Override
    public Result addGoodsBrand(GoodsBrandDto form) {
        int row = goodsBrandMapper.insert(form);
        if (row > 0) {
            return ResultGenerate.genSuccessResult(form);
        } else {
            return ResultGenerate.genErroResult("品牌添加失败，请重试！");
        }
    }

    /**
     * 上传品牌logo
     *
     * @param file
     */
    @Override
    public Result uploadGoodsBrand(MultipartFile file) throws IOException {
        Oss ossClient = new Oss();
        ossClient.init("ozomall-goods-pic", "goods/brand/");
        //将文件上传
        String name = ossClient.uploadImg2Oss(file);
        //获取文件的URl地址  以便前台  显示
        String imgUrl = ossClient.getImgUrl(name);
        ossClient.destory();
        if (!StringUtils.isEmpty(imgUrl)) {
            Map<String, String> data = new HashMap<>();
            data.put("url", imgUrl);
            return ResultGenerate.genSuccessResult(data);
        } else {
            return ResultGenerate.genErroResult("图片上传失败,请重新上传。");
        }
    }


    /**
     * 获取品牌列表
     *
     * @param form
     */
    @Override
    public Result getGoodsBrand(GoodsBrandDto form) {
        Page page = new Page();
        QueryWrapper<GoodsBrandDto> wrapper = new QueryWrapper<>();
        page.setSize(form.getSize());
        page.setCurrent(form.getPage());
        Map<String, Object> map = new HashMap();
        map.put("name", form.getName());
        wrapper.allEq(map, false);
        IPage<GoodsBrandDto> rows = goodsBrandMapper.selectPage(page, wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("品牌添加失败，请重试！");
        }
    }


    /**
     * 修改品牌
     *
     * @param form
     */
    @Override
    public Result putGoodsBrand(GoodsBrandDto form) {
        int row = goodsBrandMapper.updateById(form);
        if (row > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("修改失败");
        }
    }

    /**
     * 删除品牌
     *
     * @param form
     */
    @Override
    public Result delGoodsBrand(GoodsBrandDto form) {
        int row = goodsBrandMapper.deleteById(form.getId());
        if (row > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("删除失败");
        }
    }

}
