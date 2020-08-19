import service from '../utils/service'

// 添加订单
export function addOrder(data) {
    return service({
        url: '/mall/orders/add',
        method: 'post',
        data
    })
}

// 获取订单
export function getOrder(params) {
    return service({
        url: '/mall/orders/get',
        method: 'get',
        params
    })
}