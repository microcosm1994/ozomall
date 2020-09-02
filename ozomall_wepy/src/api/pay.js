import service from '../utils/service'

// 调用微信支付下单接口
export function unifiedorder(data) {
    return service({
        url: '/pay/unifiedorder',
        method: 'post',
        data
    })
}
