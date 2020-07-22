package com.ozomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ozomall.dao.GoodsAttrMapper;
import com.ozomall.dao.GoodsAttrValMapper;
import com.ozomall.dao.GoodsParamsMapper;
import com.ozomall.dao.GoodsSkuMapper;
import com.ozomall.entity.*;
import com.ozomall.service.GoodsAttrService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsAttrServiceImpl implements GoodsAttrService {
    @Resource
    GoodsAttrMapper goodsAttrMapper;

    @Resource
    GoodsAttrValMapper goodsAttrValMapper;

    @Resource
    GoodsSkuMapper goodsSkuMapper;

    @Resource
    GoodsParamsMapper goodsParamsMapper;

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
        List<GoodsAttrDto> rows = goodsAttrMapper.goodsAttr(null, form);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }


    /**
     * 删除商品属性
     *
     * @param form
     */
    @Override
    public Result delGoodsAttr(GoodsAttrDto form) {
        LambdaQueryWrapper<GoodsAttrDto> wrapper = new LambdaQueryWrapper();
        wrapper.eq(GoodsAttrDto::getGoodsId, form.getGoodsId());
        wrapper.eq(GoodsAttrDto::getName, form.getName());
        int rows = goodsAttrMapper.delete(wrapper);
        if (rows > 0) {
            return ResultGenerate.genSuccessResult("删除成功");
        } else {
            return ResultGenerate.genErroResult("删除失败！");
        }
    }

    /**
     * 添加商品属性值
     *
     * @param form
     */
    @Override
    public Result addGoodsAttrVal(GoodsAttrValDto form) {
        int rows = goodsAttrValMapper.insert(form);
        if (rows > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("属性值添加失败，请重试！");
        }
    }


    /**
     * 删除商品属性值
     *
     * @param form
     */
    @Override
    public Result delGoodsAttrVal(GoodsAttrValDto form) {
        LambdaQueryWrapper<GoodsAttrValDto> wrapper = new LambdaQueryWrapper();
        wrapper.eq(GoodsAttrValDto::getGoodsAttrId, form.getGoodsAttrId());
        wrapper.eq(GoodsAttrValDto::getValue, form.getValue());
        int rows = goodsAttrValMapper.delete(wrapper);
        if (rows > 0) {
            return ResultGenerate.genSuccessResult("删除成功");
        } else {
            return ResultGenerate.genErroResult("删除失败！");
        }
    }

    /**
     * 添加商品价格
     *
     * @param form
     */
    @Override
    public Result addGoodsSku(GoodsSkuDto form) {
        int rows = goodsSkuMapper.insert(form);
        if (rows > 0) {
            return ResultGenerate.genSuccessResult("保存成功");
        } else {
            return ResultGenerate.genErroResult("保存失败！");
        }
    }

    /**
     * 获取商品价格
     *
     * @param form
     */
    @Override
    public Result getGoodsSkuList(GoodsSkuDto form) {
        LambdaQueryWrapper<GoodsSkuDto> wrapper = new LambdaQueryWrapper();
        wrapper.eq(GoodsSkuDto::getGoodsId, form.getGoodsId());
        wrapper.orderByAsc(GoodsSkuDto::getId);
        List<GoodsSkuDto> rows = goodsSkuMapper.selectList(wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("获取失败！");
        }
    }

    /**
     * 修改商品价格
     *
     * @param form
     */
    @Override
    public Result putGoodsSku(GoodsSkuDto form) {
        int rows = goodsSkuMapper.updateById(form);
        if (rows > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("更新失败！");
        }
    }

    /**
     * 删除商品价格
     *
     * @param form
     */
    @Override
    public Result delGoodsSku(GoodsSkuDto form) {
        int rows = goodsSkuMapper.deleteById(form.getId());
        if (rows > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("删除失败！");
        }
    }

    /**
     * 添加商品参数
     *
     * @param form
     */
    @Override
    public Result addGoodsParams(GoodsParamsDto form) {
        int rows = goodsParamsMapper.insert(form);
        if (rows > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("删除失败！");
        }
    }

    /**
     * 获取商品参数
     *
     * @param form
     */
    @Override
    public Result getGoodsParams(GoodsParamsDto form) {
        LambdaQueryWrapper<GoodsParamsDto> wrapper = new LambdaQueryWrapper();
        wrapper.eq(GoodsParamsDto::getGoodsId, form.getGoodsId());
        wrapper.orderByAsc(GoodsParamsDto::getId);
        List<GoodsParamsDto> rows = goodsParamsMapper.selectList(wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("获取失败！");
        }
    }

    /**
     * 修改商品参数
     *
     * @param form
     */
    @Override
    public Result putGoodsParams(GoodsParamsDto form) {
        int rows = goodsParamsMapper.updateById(form);
        if (rows > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("更新失败！");
        }
    }

    /**
     * 删除商品参数
     *
     * @param form
     */
    @Override
    public Result delGoodsParams(GoodsParamsDto form) {
        int rows = goodsParamsMapper.deleteById(form.getId());
        if (rows > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("删除失败！");
        }
    }

}
