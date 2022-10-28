import request from '@/utils/request'

export default {
  // 获取所有旅游线路信息
  getPageRoute(cur, limit, params) {
    return request({
      url: `/travel_system/tourist-route/findAll/${cur}/${limit}`,
      method: 'get',
      params
    })
  },

  // 添加旅游线路信息
  addRoute(data) {
    return request({
      url: '/travel_system/tourist-route/addRoute',
      method: 'post',
      data
    })
  },

  // 修改旅游线路信息
  updateRoute(data) {
    return request({
      url: '/travel_system/tourist-route/updateRoute',
      method: 'put',
      data
    })
  },

  // 根据id删除旅游线路
  removeRoute(id) {
    return request({
      url: `/travel_system/tourist-route/${id}`,
      method: 'delete'
    })
  }}
