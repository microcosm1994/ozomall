package com.ozomall.utils;

import com.ozomall.entity.OrderDto;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class OrderUtils {
    @Resource
    private JedisPool jedisPool;

    /**
     * 生成订单编号
     */
    public String generateOrderNo(OrderDto form) {
        String userId = form.getUserId().toString();
        if (userId.length() < 6) {
            String str = "";
            for (int i = 0; i < 6 - userId.length(); i++) {
                str += "0";
            }
            userId = str + userId;
        }
        String orderNo = form.getSourceType() + "" + new Date().getTime() + userId;
        return orderNo;
    }

    /**
     * 设置redis倒计时
     */
    public void setRedisTimer(String orderNo) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(2);
        long time = new Date().getTime();
        jedis.set(orderNo, String.valueOf(time));
    }

    /**
     * 清除redis倒计时缓存
     */
    public void clearRedisTimer(String orderNo) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(2);
        jedis.del(orderNo);
    }

    /**
     * 预占库存
     */
    public void campStock(OrderDto form) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(1);
        int stock = Integer.parseInt(jedis.get(String.valueOf(form.getGoodsSkuId())));
        stock--;
        jedis.set(String.valueOf(form.getGoodsSkuId()), String.valueOf(stock));
    }

    /**
     * 归还预占库存
     */
    public void revertStock(OrderDto form) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(1);
        int stock = Integer.parseInt(jedis.get(String.valueOf(form.getGoodsSkuId())));
        stock++;
        jedis.set(String.valueOf(form.getGoodsSkuId()), String.valueOf(stock));
    }
}
