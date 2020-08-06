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