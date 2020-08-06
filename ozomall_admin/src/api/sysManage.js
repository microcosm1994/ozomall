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

// 登出
export function logout(data) {
  return request({
    url: '/admin/user/logout',
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

// 修改用户
export function putUser(data) {
  return request({
    url: '/admin/user/put',
    method: 'post',
    data
  })
}

// 删除用户
export function delUser(data) {
  return request({
    url: '/admin/user/del',
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

// 添加角色
export function putRole(data) {
  return request({
    url: '/admin/role/put',
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

// 添加角色
export function delRole(data) {
  return request({
    url: '/admin/role/del',
    method: 'post',
    data
  })
}