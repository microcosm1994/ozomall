import service from '../utils/service'

// 调用微信支付下单接口
export function unifiedorder(data) {
    return service({
        url: '/pay/unifiedorder',
        method: 'post',
        data
    })
}

// 查询微信订单状态
export function orderquery(params) {
    return service({
        url: '/pay/orderquery',
        method: 'get',
        params
    })
}
