import service from '../utils/service'

// 发送短信验证码
export function sendMessage(data) {
    return service({
        url: '/mall/user/sendMessage',
        method: 'post',
        data
    })
}

// 登录
export function login(data) {
    return service({
        url: '/mall/user/login',
        method: 'post',
        data
    })
}

// 登出
export function logout() {
    return service({
        url: '/mall/user/logout',
        method: 'post'
    })
}

// 获取用户设置
export function getSettings(params) {
    return service({
        url: '/mall/user/getSettings',
        method: 'get',
        params
    })
}

// 设置用户设置
export function setSettings(data) {
    return service({
        url: '/mall/user/setSettings',
        method: 'post',
        data
    })
}