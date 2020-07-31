import request from '@/utils/request'
/** 
 * users
 */
// 登陆
export function login(data) {
  return request({
    url: '/admin/user/login',
    method: 'post',
    data
  })
}

// 根据token查询用户信息
export function getInfo(token) {
  return request({
    url: '/admin/user/info',
    method: 'get',
    params: { token }
  })
}

// 登出
export function logout() {
  return request({
    url: '/vue-element-admin/user/logout',
    method: 'post'
  })
}

// 获取用户列表
export function getUserList(params) {
  return request({
    url: '/admin/user/get',
    method: 'get',
    params
  })
}

// 添加用户
export function addUser(data) {
  return request({
    url: '/admin/user/add',
    method: 'post',
    data
  })
}

/** 
 * roles
 */

// 添加角色
export function addRole(data) {
  return request({
    url: '/admin/role/add',
    method: 'post',
    data
  })
}

// 获取角色
export function getRole(params) {
  return request({
    url: '/admin/role/get',
    method: 'get',
    params
  })
}