import service from '../utils/service'

// 添加订单
export function addOrder(data) {
    return service({
        url: '/order/add',
        method: 'post',
        data
    })
}