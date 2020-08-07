import service from '../utils/service'

// 获取banner
export function getBanner(params) {
    return service({
        url: '/banner/get',
        method: 'get',
        params
    })
}

// 获取分类
export function queryClassify(params) {
    return service({
        url: '/classify/list',
        method: 'get',
        params
    })
}

// 获取2、3级分类
export function childrenList(params) {
    return service({
        url: '/classify/childrenList',
        method: 'get',
        params
    })
}
