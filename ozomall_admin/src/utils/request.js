import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'

const service = axios.create({
  baseURL: '/api',
  withCredentials: true,
  timeout: 5000
})

service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers['token'] = store.state.user.token
    }
    return config
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)
// response
service.interceptors.response.use(
  response => {
    const res = response.data
    switch (response.status) {
      case 401:
        Message({
          message: res.message || '登陆过期，请重新登录。',
          type: 'error',
        })
        store.dispatch('user/logout').then(() => {
          location.reload()
        })
        break
      default:
        break
    }
    return response
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
    })
    return Promise.reject(error)
  }
)

export default service
