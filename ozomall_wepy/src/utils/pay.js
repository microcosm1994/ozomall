import md5 from "./md5.js";
import Toast from '../components/vant/toast/toast.js';
export default {
    appId: 'wxd2c7f2caf86e4478',
    signType: 'MD5',
    key: 'BvhMe8dJkziJu1EUFbMLhq3deOcHsQhp',
    orderNo: null,
    // 调用微信支付
    pay(res, orderNo) {
        this.orderNo = orderNo
        let data = {
            appId: this.appId,
            timeStamp: new Date().getTime() + '',
            nonceStr: this.getRandomStr(32),
            package: "prepay_id=" + res.prepay_id,
            signType: this.signType
        }
        let keyArr = Object.keys(data).sort()
        let stringA = ''
        for (let i = 0; i < keyArr.length; i++) {
            let key = keyArr[i]
            if (data[key] + '') {
                stringA += key + '=' + data[key] + '&'
            }
        }
        stringA = stringA + "key=" + this.key //注：key为商户平台设置的密钥key
        stringA = md5.md5(stringA).toUpperCase()
        wx.requestPayment({
            timeStamp: data.timeStamp,
            nonceStr: data.nonceStr,
            package: data.package,
            signType: data.signType,
            paySign: stringA,
            success: () => {
                Toast.success("支付成功")
            },
            fail: () => {
                Toast.fail("支付失败")
            },
            complete: () => {
                let self = this
                wx.navigateTo({
                    url: '/pages/order/detail',
                    success: function (res1) {
                        // 通过eventChannel向被打开页面传送数据
                        res1.eventChannel.emit('orderNo', {
                            orderNo: self.orderNo,
                        });
                    },
                });
            },
        })
    },
    // 随机字符串
    getRandomStr(len) {
        let base = "abcdefghijklmnopqrstuvwxyz0123456789";
        let str = ''
        for (let i = 0; i < len; i++) {
            str += base[this.getRandomArbitrary(0, 36)]
        }
        return str;
    },
    // 生成随机数
    getRandomArbitrary(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    }
}