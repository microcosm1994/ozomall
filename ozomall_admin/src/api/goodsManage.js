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

// 删除商品属性
export function delGoodsAttr(data) {
    return request({
        url: '/admin/goods/delGoodsAttr',
        method: 'post',
        data
    })
}

// 添加商品属性值
export function addGoodsAttrVal(data) {
    return request({
        url: '/admin/goods/addGoodsAttrVal',
        method: 'post',
        data
    })
}

// 删除商品属性值
export function delGoodsAttrVal(data) {
    return request({
        url: '/admin/goods/delGoodsAttrVal',
        method: 'post',
        data
    })
}

// 添加商品价格
export function addGoodsSku(data) {
    return request({
        url: '/admin/goods/addGoodsSku',
        method: 'post',
        data
    })
}

// 获取商品价格
export function getGoodsSkuList(params) {
    return request({
        url: '/admin/goods/getGoodsSkuList',
        method: 'get',
        params
    })
}