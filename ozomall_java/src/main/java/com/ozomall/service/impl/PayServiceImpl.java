package com.ozomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ozomall.dao.GoodsSkuMapper;
import com.ozomall.dao.OrderMapper;
import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.entity.WxPayDto;
import com.ozomall.service.PayService;
import com.ozomall.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

@Service
@PropertySource(value = "classpath:config.properties")
public class PayServiceImpl implements PayService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsSkuMapper goodsSkuMapper;

    @Resource
    private OrderUtils orderUtils;

    @Resource
    private RestTemplate restTemplate;

    @Value("${wechat.appid}")
    private String appid; // 微信分配的小程序ID

    @Value("${wechat.mch_id}")
    private String mch_id; // 微信支付分配的商户号

    @Value("${wechat.body}")
    private String body; // 商品简单描述

    @Value("${wechat.notify_url}")
    private String notify_url; // 异步接收微信支付结果通知的回调地址

    @Value("${wechat.trade_type}")
    private String trade_type; // 小程序取值如下：JSAPI

    @Value("${wechat.key}")
    private String key; // key为商户平台设置的密钥key

    /**
     * 创建微信支付订单
     */
    @Override
    public WxPayDto unifiedCreateOrder(Map form) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_no", form.get("orderNo"));
        OrderDto row = orderMapper.selectOne(wrapper);
        if (row != null) {
            WxPayDto wxDto = new WxPayDto();
            System.out.println(wxDto.toString());
            String nonce_str = RandomUtil.getRandomStr(); // 32位随机字符串
            Long totalFee = row.getPayAmount().longValue() * 100;
            wxDto.setOpenid((String) form.get("openid")); // openid
            wxDto.setAppid(appid); // 微信分配的小程序ID
            wxDto.setMch_id(mch_id); // 微信支付分配的商户号
            wxDto.setNonce_str(nonce_str); // 32位随机字符串
            wxDto.setBody(body); // 商品简单描述
            wxDto.setOut_trade_no(row.getOrderNo()); // 商户系统内部订单号
            wxDto.setTotal_fee(totalFee); // 订单总金额
            wxDto.setNotify_url(notify_url); // 异步接收微信支付结果通知的回调地址
            wxDto.setTrade_type(trade_type); // 小程序取值如下: JSAPI
            wxDto.setSpbill_create_ip("127.0.0.1"); // ip
            String sign = SignUtils.genSignStr(wxDto, key); // 签名
            wxDto.setSign(sign);
            return wxDto;
        } else {
            return null;
        }
    }

    /**
     * 调用微信下单接口
     */
    @Override
    public Result unifiedorder(Map form) {
        WxPayDto wxData = this.unifiedCreateOrder(form);
        if (wxData == null) {
            return ResultGenerate.genErroResult("失败");
        }
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        StringBuffer xmlString = new StringBuffer();
        xmlString.append("<xml>")
                .append("<appid>" + wxData.getAppid() + "</appid>")
                .append("<body>" + wxData.getBody() + "</body>")
                .append("<mch_id>" + wxData.getMch_id() + "</mch_id>")
                .append("<nonce_str>" + wxData.getNonce_str() + "</nonce_str>")
                .append("<notify_url>" + wxData.getNotify_url() + "</notify_url>")
                .append("<openid>" + wxData.getOpenid() + "</openid>")
                .append("<out_trade_no>" + wxData.getOut_trade_no() + "</out_trade_no>")
                .append("<spbill_create_ip>" + wxData.getSpbill_create_ip() + "</spbill_create_ip>")
                .append("<total_fee>" + wxData.getTotal_fee() + "</total_fee>")
                .append("<trade_type>" + wxData.getTrade_type() + "</trade_type>")
                .append("<sign>" + wxData.getSign() + "</sign>")
                .append("</xml>");

//请求体
        HttpEntity<String> formEntity = new HttpEntity<>(xmlString.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.mch.weixin.qq.com/pay/unifiedorder", formEntity, String.class);
        String body = response.getBody();
        Map res = XmlUtils.xmlToMap(body);
        System.out.println(res.toString());
        if (res.get("result_code").equals("SUCCESS")) {
            return ResultGenerate.genSuccessResult(res);
        } else if (res.get("result_code").equals("FAIL")) {
            // 判断错误码
            if (res.get("err_code").equals("ORDERPAID")) {
                // 订单已支付，修改订单
                return this.orderquery(wxData.getOut_trade_no());
            } else {
                return ResultGenerate.genErroResult((String) res.get("err_code_des"));
            }
        } else {
            return ResultGenerate.genErroResult((String) res.get("return_msg"));
        }
    }

    /**
     * 查询微信订单
     */
    @Override
    public Result orderquery(String orderNo) {
        WxPayDto wxData = new WxPayDto();
        String nonce_str = RandomUtil.getRandomStr(); // 32位随机字符串
        wxData.setAppid(appid);
        wxData.setMch_id(mch_id);
        wxData.setOut_trade_no(orderNo);
        wxData.setNonce_str(nonce_str);
        String sign = SignUtils.genSignStr(wxData, key); // 签名
        wxData.setSign(sign);
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        StringBuffer xmlString = new StringBuffer();
        xmlString.append("<xml>")
                .append("<appid>" + wxData.getAppid() + "</appid>")
                .append("<mch_id>" + wxData.getMch_id() + "</mch_id>")
                .append("<nonce_str>" + wxData.getNonce_str() + "</nonce_str>")
                .append("<out_trade_no>" + wxData.getOut_trade_no() + "</out_trade_no>")
                .append("<sign>" + wxData.getSign() + "</sign>")
                .append("</xml>");

//请求体
        HttpEntity<String> formEntity = new HttpEntity<>(xmlString.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.mch.weixin.qq.com/pay/orderquery", formEntity, String.class);
        String body = response.getBody();
        Map res = XmlUtils.xmlToMap(body);
        if (res.get("result_code").equals("SUCCESS")) {
            // 判断订单状态
            if (res.get("trade_state").equals("SUCCESS")) {
                // SUCCESS—支付成功
                return this.tradeSuccess(res);
            } else if (res.get("trade_state").equals("REFUND")) {
                // REFUND—转入退款
                return ResultGenerate.genSuccessResult();
            } else {
                // 其他
                return ResultGenerate.genSuccessResult();
            }
        } else {
            return ResultGenerate.genErroResult((String) res.get("return_msg"));
        }
    }

    /**
     * 支付成功，修改订单
     */
    @Override
    public Result tradeSuccess(Map res) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_no", res.get("out_trade_no"));
        OrderDto row = orderMapper.selectOne(wrapper);
        // 如果订单状态为待付款就更新订单状态，如果是其他就证明订单已经被更新过
        if (row != null && row.getStatus() == 0) {
            row.setPayType(2);
            row.setStatus(1);
//            row.setPaymentTime((Date) res.get("time_end"));
            row.setPaymentNo((String) res.get("transaction_id"));
            int rowNum = orderMapper.updateById(row);
            if (rowNum > 0) {
                // 支付成功减去真实库存
                goodsSkuMapper.reduceStock(row.getGoodsSkuId(), 1);
                // 清除redis倒计时缓存
                orderUtils.clearRedisTimer(row.getOrderNo());
                return ResultGenerate.genSuccessResult();
            } else {
                return ResultGenerate.genErroResult("失败");
            }
        } else {
            return ResultGenerate.genSuccessResult();
        }
    }

    /**
     * 关闭微信支付订单
     */
    @Override
    public Result closeorder(String orderNo) {
        WxPayDto wxData = new WxPayDto();
        String nonce_str = RandomUtil.getRandomStr(); // 32位随机字符串
        wxData.setAppid(appid);
        wxData.setMch_id(mch_id);
        wxData.setOut_trade_no(orderNo);
        wxData.setNonce_str(nonce_str);
        String sign = SignUtils.genSignStr(wxData, key); // 签名
        wxData.setSign(sign);
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        StringBuffer xmlString = new StringBuffer();
        xmlString.append("<xml>")
                .append("<appid>" + wxData.getAppid() + "</appid>")
                .append("<mch_id>" + wxData.getMch_id() + "</mch_id>")
                .append("<nonce_str>" + wxData.getNonce_str() + "</nonce_str>")
                .append("<out_trade_no>" + wxData.getOut_trade_no() + "</out_trade_no>")
                .append("<sign>" + wxData.getSign() + "</sign>")
                .append("</xml>");

//请求体
        HttpEntity<String> formEntity = new HttpEntity<>(xmlString.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.mch.weixin.qq.com/pay/closeorder", formEntity, String.class);
        String body = response.getBody();
        Map res = XmlUtils.xmlToMap(body);
        System.out.println(res.toString());
        if (res.get("result_code").equals("SUCCESS")) {
            return ResultGenerate.genSuccessResult(res);
        } else {
            return ResultGenerate.genErroResult((String) res.get("err_code_des"));
        }
    }
}
