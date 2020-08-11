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
