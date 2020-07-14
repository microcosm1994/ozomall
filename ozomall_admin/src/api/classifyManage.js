import request from '@/utils/request'

// 新建分类
export function addClassify(data) {
    return request({
        url: '/admin/classify/add',
        method: 'post',
        data
    })
}

// 获取分类列表
export function getClassifyList(params) {
    return request({
        url: '/admin/classify/list',
        method: 'get',
        params
    })
}
