import request from '@/utils/request'

export default {
  // 获取所有景点信息
  getPageScenicSpot(cur, limit, params) {
    return request({
      url: `/travel_system/scenic-spot/findAll/${cur}/${limit}`,
      method: 'get',
      params
    })
  },

  // 添加景点信息
  addScenicSpot(data) {
    return request({
      url: '/travel_system/scenic-spot/addScenicSpot',
      method: 'post',
      data
    })
  },

  // 修改景点信息
  updateScenicSpot(data) {
    return request({
      url: '/travel_system/scenic-spot/updateScenicSpot',
      method: 'put',
      data
    })
  },

  // 根据id删除景点
  removeScenicSpot(id) {
    return request({
      url: `/travel_system/scenic-spot/${id}`,
      method: 'delete'
    })
  }
}
