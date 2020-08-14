import service from '../utils/service'

// 获取商品列表
export function goodsList(params) {
    return service({
        url: '/mall/goods/list',
        method: 'get',
        params
    })
}

// 搜索商品
export function searchGoods(params) {
    return service({
        url: '/mall/goods/search',
        method: 'get',
        params
    })
}

// 获取商品信息
export function getGoods(params) {
    return service({
        url: '/goods/get',
        method: 'get',
        params
    })
}

// 获取商品数量
export function getGoodsCount(params) {
    return service({
        url: '/mall/goods/getGoodsCount',
        method: 'get',
        params
    })
}

// 获取商品图片
export function getGoodsPic(params) {
    return service({
        url: '/goods/getGoodsPic',
        method: 'get',
        params
    })
}

// 获取商品规格
export function getGoodsAttr(params) {
    return service({
        url: '/goods/getGoodsAttr',
        method: 'get',
        params
    })
}

// 获取商品价格
export function getGoodsSkuList(params) {
    return service({
        url: '/goods/getGoodsSkuList',
        method: 'get',
        params
    })
}

// 获取商品参数
export function getGoodsParams(params) {
    return service({
        url: '/goods/getGoodsParams',
        method: 'get',
        params
    })
}