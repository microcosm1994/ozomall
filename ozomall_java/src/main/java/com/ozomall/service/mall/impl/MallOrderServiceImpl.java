package com.ozomall.service.mall.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ozomall.dao.OrderMapper;
import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.process.MsgProducer;
import com.ozomall.service.PayService;
import com.ozomall.service.mall.MallOrderService;
import com.ozomall.utils.OrderUtils;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class MallOrderServiceImpl implements MallOrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private MsgProducer msgProducer;

    @Resource
    private JedisPool jedisPool;

    @Resource
    private OrderUtils orderUtils;

    @Resource
    private PayService payService;

    /**
     * 添加订单
     */
    @Override
    public Result addOrder(OrderDto form) {
        // 订单编号
        String orderNo = orderUtils.generateOrderNo(form);
        form.setOrderNo(orderNo);
        // 占库存
        Jedis jedis = jedisPool.getResource();
        jedis.select(1);
        int stock = Integer.parseInt(jedis.get(String.valueOf(form.getGoodsSkuId())));
        if (stock >= 1) {
            // 减库存
//            int count = goodsSkuMapper.reduceStock(form.getGoodsSkuId(), 1);
            stock--;
            jedis.set(String.valueOf(form.getGoodsSkuId()), String.valueOf(stock));
            // 创建订单步骤放到mq中处理
            String formJson = JSON.toJSONString(form);
            msgProducer.sendOrderAddMsg(formJson); // 发送到创建订单队列
            // 设置redis倒计时
            orderUtils.setRedisTimer(orderNo);
            // 先返回订单编号给用户，用户使用订单编号查询订单是否创建完成，没有创建完成就显示订单正在创建
            Map data = new HashMap();
            data.put("orderNo", orderNo);
            return ResultGenerate.genSuccessResult(data);
        } else {
            return ResultGenerate.genErroResult("库存不足！");
        }
    }

    /**
     * 根据订单编号查询订单
     */
    @Override
    public Result getOrderNo(OrderDto form) {
        OrderDto row = orderMapper.getOrderDetail(form.getOrderNo());
        if (row != null) {
            return ResultGenerate.genSuccessResult(row);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }

    /**
     * 获取订单
     */
    @Override
    public Result getOrder(OrderDto form) {
        LambdaQueryWrapper<OrderDto> wrapper = new LambdaQueryWrapper<>();
        Map<SFunction<OrderDto, ?>, Object> map = new HashMap<>();
        map.put(OrderDto::getId, form.getId());
        map.put(OrderDto::getOrderNo, form.getOrderNo());
        map.put(OrderDto::getUserId, form.getUserId());
        map.put(OrderDto::getGoodsName, form.getGoodsName());
        map.put(OrderDto::getPayType, form.getPayType());
        map.put(OrderDto::getSourceType, form.getSourceType());
        map.put(OrderDto::getStatus, form.getStatus());
        map.put(OrderDto::getDel, form.getDel());
        wrapper.allEq(map, false);
        wrapper.orderByDesc(OrderDto::getId);
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

    /**
     * 获取最近购买订单
     */
    @Override
    public Result getBuyList(OrderDto form) {
        Page page = new Page<>();
        page.setCurrent(form.getPage());
        page.setSize(form.getSize());
        IPage<OrderDto> row = orderMapper.getBuyList(page, form);
        if (row != null) {
            return ResultGenerate.genSuccessResult(row);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }

    /**
     * 关闭订单
     */
    @Override
    public Result closeOrder(String orderNo) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_no", orderNo);
        OrderDto row = orderMapper.selectOne(wrapper);
        if (row != null && row.getStatus() == 0) {
            row.setStatus(4); // 订单状态改为关闭状态
            int n = orderMapper.updateById(row);
            if (n > 0) {
                // 清除redis倒计时缓存
                orderUtils.clearRedisTimer(orderNo);
                // 归还预占库存
                orderUtils.revertStock(row);
                // 关闭微信支付订单
                payService.closeorder(row.getOrderNo());
                return ResultGenerate.genSuccessResult();
            } else {
                return ResultGenerate.genErroResult("失败！");
            }
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }

}
