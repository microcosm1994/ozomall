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

// 添加商品信息
export function addGoods(data) {
    return request({
        url: '/admin/goods/add',
        method: 'post',
        data
    })
}

// 获取商品列表
export function getGoodsList(params) {
    return request({
        url: '/admin/goods/list',
        method: 'get',
        params
    })
}

// 根据id获取商品信息
export function getGoods(params) {
    return request({
        url: '/admin/goods/get',
        method: 'get',
        params
    })
}

// 添加商品属性
export function addGoodsAttr(data) {
    return request({
        url: '/admin/goods/addGoodsAttr',
        method: 'post',
        data
    })
}

// 获取商品属性
export function getGoodsAttr(params) {
    return request({
        url: '/admin/goods/getGoodsAttr',
        method: 'get',
        params
    })
}