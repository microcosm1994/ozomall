import service from '../utils/service'

// 添加地址
export function addAddress(data) {
    return service({
        url: '/mall/address/add',
        method: 'post',
        data
    })
}

// 修改地址
export function putAddress(data) {
    return service({
        url: '/mall/address/put',
        method: 'post',
        data
    })
}

// 获取地址
export function getAddress(params) {
    return service({
        url: '/mall/address/get',
        method: 'get',
        params
    })
}

