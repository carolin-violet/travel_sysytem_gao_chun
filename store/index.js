import { Message } from 'element-ui'

export const state = () => ({
  token:'',
  userInfo: {},
  failure_time: '',
  commodity_count: 0,
})

export const mutations = {
  setUserInfo(state, data) {
    state.userInfo = {...data.info}
    state.token = data.token
    state.failure_time = data.failure_time
  },
  setCommodityCount(state, data) {
    state.commodity_count = data
  },
  logout(state){
    state.userInfo = {}
    state.token = ''
    state.failure_time = ''
  }
}

export const getters = {
  isLogin(state) {
    return state.token && state.failure_time && state.failure_time>=new Date().getTime()
  },
  CommodityCount(state) {
    return state.commodity_count
  }
}

export const actions = {
  nuxtServerInit(store, { app: { $cookies } }) {
    // ...
    // 初始化相关数据存到 store 中
    const userInfo = $cookies.get('travel_gaochun_store') || ''
    store.commit('setUserInfo', userInfo)
  },
  hasAuth(store) {
    // 使用 每个接口调用前加上this.$store.dispatch('hasAuth')
    if (!store.state.failure_time || store.state.failure_time <= new Date().getTime()) {
      this.$router.replace('/login')
      // $nuxt.$router.replace('/login')
      Message({
        message: 'token失效',
        type: 'error',
        duration: 2000
      })
    }
  }
}
