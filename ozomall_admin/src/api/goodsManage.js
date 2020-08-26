import request from '@/utils/request'

// 新建分类
export function addClassify(data) {
    return request({
        url: '/classify/add',
        method: 'post',
        data
    })
}

// 修改分类
export function putClassify(data) {
    return request({
        url: '/classify/put',
        method: 'post',
        data
    })
}

// 获取分类列表
export function getClassifyList(params) {
    return request({
        url: '/classify/list',
        method: 'get',
        params
    })
}

// 添加商品信息
export function addGoods(data) {
    return request({
        url: '/goods/add',
        method: 'post',
        data
    })
}

// 获取商品列表
export function getGoodsList(params) {
    return request({
        url: '/goods/list',
        method: 'get',
        params
    })
}

// 根据id获取商品信息
export function getGoods(params) {
    return request({
        url: '/goods/get',
        method: 'get',
        params
    })
}

// 修改商品信息
export function putGoods(data) {
    return request({
        url: '/goods/put',
        method: 'post',
        data
    })
}

// 上架/下架商品
export function handleGoods(data) {
    return request({
        url: '/goods/handle',
        method: 'post',
        data
    })
}

// 删除商品信息
export function delGoods(data) {
    return request({
        url: '/goods/del',
        method: 'post',
        data
    })
}

// 获取商品图片
export function getGoodsPic(params) {
    return request({
        url: '/goods/getGoodsPic',
        method: 'get',
        params
    })
}

// 删除商品图片
export function delGoodsPic(data) {
    return request({
        url: '/goods/delGoodsPic',
        method: 'post',
        data
    })
}

// 获取商品品牌列表
export function getGoodsBrand(params) {
    return request({
        url: '/goods/getGoodsBrand',
        method: 'get',
        params
    })
}

// 添加商品品牌
export function addGoodsBrand(data) {
    return request({
        url: '/goods/addGoodsBrand',
        method: 'post',
        data
    })
}

// 修改商品品牌
export function putGoodsBrand(data) {
    return request({
        url: '/goods/putGoodsBrand',
        method: 'post',
        data
    })
}

// 删除商品品牌
export function delGoodsBrand(data) {
    return request({
        url: '/goods/delGoodsBrand',
        method: 'post',
        data
    })
}

// 添加商品属性
export function addGoodsAttr(data) {
    return request({
        url: '/goods/addGoodsAttr',
        method: 'post',
        data
    })
}

// 获取商品属性
export function getGoodsAttr(params) {
    return request({
        url: '/goods/getGoodsAttr',
        method: 'get',
        params
    })
}

// 删除商品属性
export function delGoodsAttr(data) {
    return request({
        url: '/goods/delGoodsAttr',
        method: 'post',
        data
    })
}

// 添加商品属性值
export function addGoodsAttrVal(data) {
    return request({
        url: '/goods/addGoodsAttrVal',
        method: 'post',
        data
    })
}

// 删除商品属性值
export function delGoodsAttrVal(data) {
    return request({
        url: '/goods/delGoodsAttrVal',
        method: 'post',
        data
    })
}

// 添加商品价格
export function addGoodsSku(data) {
    return request({
        url: '/goods/addGoodsSku',
        method: 'post',
        data
    })
}

// 获取商品价格
export function getGoodsSkuList(params) {
    return request({
        url: '/goods/getGoodsSkuList',
        method: 'get',
        params
    })
}

// 修改商品价格
export function putGoodsSku(data) {
    return request({
        url: '/goods/putGoodsSku',
        method: 'post',
        data
    })
}

// 删除商品价格
export function delGoodsSku(data) {
    return request({
        url: '/goods/delGoodsSku',
        method: 'post',
        data
    })
}

// 添加商品参数
export function addGoodsParams(data) {
    return request({
        url: '/goods/addGoodsParams',
        method: 'post',
        data
    })
}

// 添加商品参数
export function getGoodsParams(params) {
    return request({
        url: '/goods/getGoodsParams',
        method: 'get',
        params
    })
}

// 修改商品参数
export function putGoodsParams(data) {
    return request({
        url: '/goods/putGoodsParams',
        method: 'post',
        data
    })
}

// 删除商品参数
export function delGoodsParams(data) {
    return request({
        url: '/goods/delGoodsParams',
        method: 'post',
        data
    })
}