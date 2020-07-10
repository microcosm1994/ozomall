import request from '@/utils/request'

// 新建分类
export function addClassify(data) {
    return request({
        url: '/admin/classify/add',
        method: 'post',
        data
    })
}
