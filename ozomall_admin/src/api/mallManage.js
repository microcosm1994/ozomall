import request from '@/utils/request'

// 新建banner
export function addBanner(data) {
    return request({
        url: '/banner/add',
        method: 'post',
        data
    })
}

// 获取banner
export function getBanner(params) {
    return request({
        url: '/banner/get',
        method: 'get',
        params
    })
}

// 修改banner
export function putBanner(data) {
    return request({
        url: '/banner/put',
        method: 'post',
        data
    })
}

// 删除banner
export function delBanner(data) {
    return request({
        url: '/banner/del',
        method: 'post',
        data
    })
}