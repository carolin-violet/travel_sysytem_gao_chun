import request from '@/utils/request'

export default {
  /*
  * 分页查询游客
  * @current 当前页码
  * @limit 每页的记录数
  * */
  getPageTourist(current, limit, params) {
    return request({
      url: `/travel_system/tourist/${current}/${limit}`,
      method: 'get',
      params
    })
  },

  // 修改密码
  changePassword(data) {
    return request({
      url: `/travel_system/tourist/changePassword`,
      method: 'patch',
      data
    })
  },

  // 根据id删除游客
  removeTourist(id) {
    return request({
      url: `/travel_system/tourist/${id}`,
      method: 'delete'
    })
  }
}
