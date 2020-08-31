import { getSign, getRandomStr } from '../utils/sgin'
export function placeOrder(param) {
    return new Promise((reslove, reject) => {
        let data = {
            appid: 'wxd2c7f2caf86e4478', // 小程序ID
            mch_id: '', // 商户号
            nonce_str: getRandomStr(32), // 随机字符串
            body: '门门板板-商城购物支付。', // 商品描述
            out_trade_no: param.out_trade_no, // 商户订单号
            total_fee: param.total_fee, // 标价金额
            spbill_create_ip: param.spbill_create_ip, // 终端IP
            notify_url: 'https://www.dubo.world/mall/orders/notify', // 通知地址
            trade_type: 'JSAPI', // 交易类型
        }
        let sign = getSign(data)
        wx.request({
            url: 'https://api.mch.weixin.qq.com/pay/unifiedorder',
            'content-type': 'text/xml',
            method: 'post',
            data: {
                ...data,
                sign: sign
            },
            success: function (res) {
                reslove(res)
            },
            fail: function (error) {
                reject(error)
            }
        });
    })
}