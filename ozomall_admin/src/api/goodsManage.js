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

// 修改商品信息
export function putGoods(data) {
    return request({
        url: '/admin/goods/put',
        method: 'post',
        data
    })
}

// 删除商品信息
export function delGoods(data) {
    return request({
        url: '/admin/goods/del',
        method: 'post',
        data
    })
}

// 获取商品图片
export function getGoodsPic(params) {
    return request({
        url: '/admin/goods/getGoodsPic',
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

// 修改商品价格
export function putGoodsSku(data) {
    return request({
        url: '/admin/goods/putGoodsSku',
        method: 'post',
        data
    })
}

// 删除商品价格
export function delGoodsSku(data) {
    return request({
        url: '/admin/goods/delGoodsSku',
        method: 'post',
        data
    })
}

// 添加商品参数
export function addGoodsParams(data) {
    return request({
        url: '/admin/goods/addGoodsParams',
        method: 'post',
        data
    })
}

// 添加商品参数
export function getGoodsParams(params) {
    return request({
        url: '/admin/goods/getGoodsParams',
        method: 'get',
        params
    })
}

// 修改商品参数
export function putGoodsParams(data) {
    return request({
        url: '/admin/goods/putGoodsParams',
        method: 'post',
        data
    })
}

// 删除商品参数
export function delGoodsParams(data) {
    return request({
        url: '/admin/goods/delGoodsParams',
        method: 'post',
        data
    })
}