package com.ozomall.entity;

import lombok.Data;

@Data
public class WxPayDto {
    private String appid; // 微信分配的小程序ID
    private String mch_id; // 微信支付分配的商户号
    private String nonce_str; // 随机字符串，长度要求在32位以内。
    private String sign; // 通过签名算法计算得出的签名值
    private String body; // 商品简单描述
    private String out_trade_no; // 商户系统内部订单号
    private Long total_fee; // 订单总金额，单位为分
    private String spbill_create_ip; // 调用微信支付API的机器IP
    private String notify_url; // 异步接收微信支付结果通知的回调地址
    private String trade_type; // 小程序取值如下：
    private String openid; // trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
}
