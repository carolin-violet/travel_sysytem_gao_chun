import request from '@/utils/request'

export default {

  getPanel() {
    return request({
      url: '/travel_system/chart/panel',
      method: 'get'
    })
  },

  getBaseCount() {
    return request({
      url: '/travel_system/chart/baseCount',
      method: 'get'
    })
  },

  getIsPaid() {
    return request({
      url: '/travel_system/chart/isPaid',
      method: 'get'
    })
  },

  getScenicAndRoute() {
    return request({
      url: '/travel_system/chart/scenicAndRoute',
      method: 'get'
    })
  },

  getComment() {
    return request({
      url: '/travel_system/chart/comment',
      method: 'get'
    })
  },

  getIncome() {
    return request({
      url: '/travel_system/chart/income',
      method: 'get'
    })
  },

  getGender() {
    return request({
      url: '/travel_system/chart/gender',
      method: 'get'
    })
  },

  getAge() {
    return request({
      url: '/travel_system/chart/age',
      method: 'get'
    })
  },

}
