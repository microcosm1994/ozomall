package com.ozomall.service.mall.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ozomall.dao.GoodsSkuMapper;
import com.ozomall.dao.OrderMapper;
import com.ozomall.entity.GoodsSkuDto;
import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.process.MsgProducer;
import com.ozomall.service.mall.MallOrderService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MallOrderServiceImpl implements MallOrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsSkuMapper goodsSkuMapper;

    @Resource
    private MsgProducer msgProducer;

    /**
     * 添加订单
     */
    @Override
    public Result addOrder(OrderDto form) {
        // 订单编号
        String userId = form.getUserId().toString();
        if (userId.length() < 6) {
            String str = "";
            for (int i = 0; i < 6 - userId.length(); i++) {
                str += "0";
            }
            userId = str + userId;
        }
        String orderNo = form.getSourceType() + "" + new Date().getTime() + userId;
        form.setOrderNo(orderNo);
        // 占库存
        QueryWrapper<GoodsSkuDto> wrapper = new QueryWrapper<>();
        wrapper.eq("id", form.getGoodsSkuId());
        GoodsSkuDto sku = goodsSkuMapper.selectOne(wrapper);
        int stock = sku.getStock();
        if (stock >= 1) {
            // 减库存
            int count = goodsSkuMapper.reduceStock(form.getGoodsSkuId(), 1);
            if (count > 0) {
                int row = orderMapper.insert(form); // 添加订单
                if (row > 0) {
                    return ResultGenerate.genSuccessResult();
                } else {
                    return ResultGenerate.genErroResult("失败！");
                }
            } else {
                return ResultGenerate.genErroResult("预占库存失败！");
            }
        } else {
            return ResultGenerate.genErroResult("库存不足！");
        }
    }

    /**
     * 获取订单
     */
    @Override
    public Result getOrder(OrderDto form) {
        msgProducer.sendMsg("sssss");
        LambdaQueryWrapper<OrderDto> wrapper = new LambdaQueryWrapper<>();
        Map<SFunction<OrderDto, ?>, Object> map = new HashMap<>();
        map.put(OrderDto::getId, form.getId());
        map.put(OrderDto::getUserId, form.getUserId());
        map.put(OrderDto::getGoodsName, form.getGoodsName());
        map.put(OrderDto::getPayType, form.getPayType());
        map.put(OrderDto::getSourceType, form.getSourceType());
        map.put(OrderDto::getStatus, form.getStatus());
        map.put(OrderDto::getDel, form.getDel());
        wrapper.allEq(map, false);
        Page page = new Page();
        page.setSize(form.getSize());
        page.setCurrent(form.getPage());
        IPage<OrderDto> rows = orderMapper.selectPage(page, wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }
}
