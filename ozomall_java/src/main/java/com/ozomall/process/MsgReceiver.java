package com.ozomall.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ozomall.config.RabbitConfig;
import com.ozomall.dao.OrderMapper;
import com.ozomall.entity.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MsgReceiver {
    @Resource
    private OrderMapper orderMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = RabbitConfig.Order_QUEUE_Add)
    public void orderAddQUEUE(String content) {
        JSONObject jsonValue = JSON.parseObject(content);
        // 转实体对象
        OrderDto form = JSON.toJavaObject(jsonValue, OrderDto.class);
        orderMapper.insert(form); // 添加订单
        logger.info(form.getOrderNo() + "订单已创建");
    }

    @RabbitListener(queues = RabbitConfig.Order_QUEUE)
    public void orderQUEUE(String content) {
        JSONObject jsonValue = JSON.parseObject(content);
        // 转实体对象
        OrderDto form = JSON.toJavaObject(jsonValue, OrderDto.class);
        QueryWrapper<OrderDto> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", form.getOrderNo());
        // 查询订单
        OrderDto row = orderMapper.selectOne(wrapper);
        //订单状态：0->待付款；1->待发货；2->待收货；3->已完成；4->已关闭；
        if (row.getStatus() == 0) {
            // 订单超时未支付，关闭订单。
            row.setStatus(4);
            orderMapper.updateById(row);
            logger.info(row.getOrderNo() + "订单超时未支付，已关闭");
        }
    }
}
