import request from '@/utils/request'

// 获取订单列表
export function getOrder(params) {
    return request({
        url: '/admin/orders/get',
        method: 'get',
        params
    })
}

// 发货
export function handle(data) {
    return request({
        url: '/admin/orders/handle',
        method: 'post',
        data
    })
}