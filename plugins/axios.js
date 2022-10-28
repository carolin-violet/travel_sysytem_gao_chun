export default function({ $axios, store, $cookies }, inject) {

  $axios.defaults.baseURL = process.env.baseUrl + '/travel_system'
  $axios.defaults.timeout = 30000

  $axios.interceptors.request.use(config => {
    if (store.state.token) {
      config.headers['token'] = store.state.token
    } else {
      const token = $cookies.get('travel-system-front-token')
      if (token) {
        config.headers['token'] = token
      }
    }
    return config
  })
  $axios.interceptors.response.use(response => {
    if (response.code === 20001) {
      console.log('response.statusText:', response.statusText);
      return Promise.reject(response.statusText)
    }
    return response.data
  })
}
