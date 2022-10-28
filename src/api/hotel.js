import request from '@/utils/request'

export default {
  // 根据不同条件获取所有旅馆信息
  getPageHotel(cur, limit, params) {
    return request({
      url: `/travel_system/hotel/findAll/${cur}/${limit}`,
      method: 'get',
      params
    })
  },


  // 添加旅馆信息
  addHotel(data) {
    return request({
      url: '/travel_system/hotel/addHotel',
      method: 'post',
      data
    })
  },

  // 修改旅馆信息
  updateHotel(data) {
    return request({
      url: '/travel_system/hotel/updateHotel',
      method: 'put',
      data
    })
  },

  // 根据id删除旅馆
  removeHotel(id) {
    return request({
      url: `/travel_system/hotel/${id}`,
      method: 'delete'
    })
  }
}
