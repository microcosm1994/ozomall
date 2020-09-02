package com.ozomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ozomall.dao.OrderMapper;
import com.ozomall.entity.OrderDto;
import com.ozomall.entity.Result;
import com.ozomall.entity.WxPayDto;
import com.ozomall.service.PayService;
import com.ozomall.utils.RandomUtil;
import com.ozomall.utils.ResultGenerate;
import com.ozomall.utils.SignUtils;
import com.ozomall.utils.XmlUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RestTemplate restTemplate;

    private String appid = "wxd2c7f2caf86e4478"; // 微信分配的小程序ID
    private String mch_id = "1602379149"; // 微信支付分配的商户号
    private String body = "门门板板-商城购物支付"; // 商品简单描述
    private String notify_url = "https://www.dubo.world/mall/orders/notify"; // 异步接收微信支付结果通知的回调地址
    private String trade_type = "JSAPI"; // 小程序取值如下：JSAPI
    private String key = "BvhMe8dJkziJu1EUFbMLhq3deOcHsQhp"; // key为商户平台设置的密钥key

    /**
     * 创建微信支付订单
     */
    @Override
    public Result unifiedCreateOrder(Map form) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_no", form.get("orderNo"));
        OrderDto row = orderMapper.selectOne(wrapper);
        if (row != null) {
            WxPayDto wxDto = new WxPayDto();
            System.out.println(wxDto.toString());
            String nonce_str = RandomUtil.getRandomStr(); // 32位随机字符串
            Long totalFee = row.getPayAmount().longValue() * 100;
            wxDto.setAppid(appid); // 微信分配的小程序ID
            wxDto.setMch_id(mch_id); // 微信支付分配的商户号
            wxDto.setNonce_str(nonce_str); // 32位随机字符串
            wxDto.setBody(body); // 商品简单描述
            wxDto.setOut_trade_no(row.getOrderNo()); // 商户系统内部订单号
            wxDto.setTotal_fee(totalFee); // 订单总金额
            wxDto.setNotify_url(notify_url); // 异步接收微信支付结果通知的回调地址
            wxDto.setTrade_type(trade_type); // 小程序取值如下: JSAPI
            wxDto.setSpbill_create_ip("127.0.0.1"); // ip
            wxDto.setOpenid((String) form.get("openid")); // openid
            String sign = SignUtils.genSignStr(wxDto, key); // 签名
            wxDto.setSign(sign);
            return this.unifiedorder(wxDto);
        } else {
            return ResultGenerate.genErroResult("订单不存在");
        }
    }

    /**
     * 调用微信下单接口
     */
    @Override
    public Result unifiedorder(WxPayDto form) {
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        StringBuffer xmlString = new StringBuffer();
        xmlString.append("<xml>")
                .append("<appid>" + form.getAppid() + "</appid>")
                .append("<body>" + form.getBody() + "</body>")
                .append("<mch_id>" + form.getMch_id() + "</mch_id>")
                .append("<nonce_str>" + form.getNonce_str() + "</nonce_str>")
                .append("<notify_url>" + form.getNotify_url() + "</notify_url>")
                .append("<openid>" + form.getOpenid() + "</openid>")
                .append("<out_trade_no>" + form.getOut_trade_no() + "</out_trade_no>")
                .append("<spbill_create_ip>" + form.getSpbill_create_ip() + "</spbill_create_ip>")
                .append("<total_fee>" + form.getTotal_fee() + "</total_fee>")
                .append("<trade_type>" + form.getTrade_type() + "</trade_type>")
                .append("<sign>" + form.getSign() + "</sign>")
                .append("</xml>");

//请求体
        HttpEntity<String> formEntity = new HttpEntity<>(xmlString.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.mch.weixin.qq.com/pay/unifiedorder", formEntity, String.class);
        String body = response.getBody();
        Map res = XmlUtils.xmlToMap(body);
        System.out.println(res.toString());
        if (res.get("result_code").equals("SUCCESS")) {
            return ResultGenerate.genSuccessResult(res);
        } else {
            return ResultGenerate.genErroResult((String) res.get("return_msg"));
        }
    }
}
