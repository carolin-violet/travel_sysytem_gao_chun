import request from '@/utils/request'

export default {

  // 添加坐标
  getCoordinate(mark_id) {
    return request({
      url: `/travel_system/coordinate/${mark_id}`,
      method: 'get'
    })
  },


  // 添加坐标
  addCoordinate(data) {
    return request({
      url: `/travel_system/coordinate`,
      method: 'post',
      data
    })
  },

  // 修改坐标
  updateCoordinate(data) {
    return request({
      url: `/travel_system/coordinate`,
      method: 'put',
      data
    })
  }
}
