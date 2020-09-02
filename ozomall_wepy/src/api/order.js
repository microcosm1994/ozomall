import service from '../utils/service'

// 添加订单
export function addOrder(data) {
    return service({
        url: '/mall/orders/add',
        method: 'post',
        data
    })
}

// 根据订单编号获取订单
export function getByOrderNo(params) {
    return service({
        url: '/mall/orders/getByOrderNo',
        method: 'get',
        params
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
// 获取最近购买订单
export function getBuyList(params) {
    return service({
        url: '/mall/orders/getBuyList',
        method: 'get',
        params
    })
}

// 根据订单编号查询未支付订单倒计时
export function getOrderTimer(params) {
    return service({
        url: '/mall/orders/getOrderTimer',
        method: 'get',
        params
    })
}
