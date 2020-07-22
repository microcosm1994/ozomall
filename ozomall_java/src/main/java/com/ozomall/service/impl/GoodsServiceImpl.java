package com.ozomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ozomall.dao.GoodsMapper;
import com.ozomall.entity.GoodsDto;
import com.ozomall.entity.Result;
import com.ozomall.service.GoodsService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    GoodsMapper goodsMapper;

    /**
     * 添加商品信息
     *
     * @param form
     */
    @Override
    public Result addGoods(GoodsDto form) {
        int row = goodsMapper.insert(form);
        if (row > 0) {
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

}
