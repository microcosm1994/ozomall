import { logout, getInfo } from '@/api/sysManage'
import router from '@/router'

const state = {
  token: '',
  userInfo: null,
  introduction: '',
}

const mutations = {
  // 存储token
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  // 存储用户信息
  SET_UserInfo: (state, userInfo) => {
    state.userInfo = userInfo
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  }
}

const actions = {
  // 获取用户信息
  getInfo({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response
        if (data.code === 1) {
          commit('SET_UserInfo', data.data)
          resolve(data.data)
        } else {
          dispatch("logout")
          reject('Verification failed, please Login again.')
        }
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 用户登出
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        commit('SET_TOKEN', '')
        dispatch('tagsView/delAllViews', null, { root: true })

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 清除token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      resolve()
    })
  },

  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    const token = role + '-token'

    commit('SET_TOKEN', token)
    setToken(token)

    const { roles } = await dispatch('getInfo')


    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })
    // dynamically add accessible routes
    router.addRoutes(accessRoutes)

    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, { root: true })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
