import request from '@/utils/request'

export default {

  // 根据线路id查询该线路所有站点信息
  getAllSitesByRouteId(id) {
    return request({
      url: `/travel_system/route-detail/${id}/findAll`,
      method: 'get'
    })
  },
  // 添加站点信息
  addSite(data) {
    return request({
      url: '/travel_system/route-detail/addSite',
      method: 'post',
      data
    })
  },

  // 修改站点信息
  updateSite(data) {
    return request({
      url: '/travel_system/route-detail/updateSite',
      method: 'put',
      data
    })
  },

  // 根据id删除站点
  removeSite(id) {
    return request({
      url: `/travel_system/route-detail/${id}`,
      method: 'delete'
    })
  },

  // 获取所有分类及分类的数据名称
  getAllItems() {
    return request({
      url: '/travel_system/route-detail/getAllItems',
      method: 'get'
    })
  },

}
