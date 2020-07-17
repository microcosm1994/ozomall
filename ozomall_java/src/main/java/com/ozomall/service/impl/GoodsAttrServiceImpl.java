package com.ozomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ozomall.dao.GoodsAttrMapper;
import com.ozomall.entity.GoodsAttrDto;
import com.ozomall.entity.Result;
import com.ozomall.service.GoodsAttrService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsAttrServiceImpl implements GoodsAttrService {
    @Resource
    GoodsAttrMapper goodsAttrMapper;

    /**
     * 添加商品属性
     *
     * @param form
     */
    @Override
    public Result addGoodsAttr(GoodsAttrDto form) {
        int rows = goodsAttrMapper.insert(form);
        if (rows > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("商品属性添加失败，请重试！");
        }
    }

    /**
     * 获取商品属性
     *
     * @param form
     */
    @Override
    public Result getGoodsAttr(GoodsAttrDto form) {
        LambdaQueryWrapper<GoodsAttrDto> wrapper = new LambdaQueryWrapper();
        wrapper.eq(GoodsAttrDto::getGoodsId, form.getGoodsId());
        List<GoodsAttrDto> rows = goodsAttrMapper.selectList(wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("商品属性添加失败，请重试！");
        }
    }
}
