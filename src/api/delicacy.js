import request from '@/utils/request'

export default {
  // 获取所有美食信息
  getPageDelicacy(cur, limit, params) {
    return request({
      url: `/travel_system/delicacy/findAll/${cur}/${limit}`,
      method: 'get',
      params
    })
  },

  // 添加美食信息
  addDelicacy(data) {
    return request({
      url: '/travel_system/delicacy/addDelicacy',
      method: 'post',
      data
    })
  },

  // 修改美食信息
  updateDelicacy(data) {
    return request({
      url: '/travel_system/delicacy/updateDelicacy',
      method: 'put',
      data
    })
  },

  // 根据id删除美食
  removeDelicacy(id) {
    return request({
      url: `/travel_system/delicacy/${id}`,
      method: 'delete'
    })
  }
}
