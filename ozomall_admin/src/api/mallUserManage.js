import request from '@/utils/request'

/**
 * 商城用户
 * 
 */

// 获取商城用户列表
export function getMallUserList(params) {
    return request({
        url: '/admin/user/getMallUser',
        method: 'get',
        params
    })
}