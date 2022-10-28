import request from '@/utils/request'

export default {
  /*
  * 分页查询游客
  * @current 当前页码
  * @limit 每页的记录数
  * */
  getPageComment(current, limit, params) {
    return request({
      url: `/travel_system/comment/findAll/${current}/${limit}`,
      method: 'get',
      params
    })
  },

  // 根据id删除游客
  removeComment(id) {
    return request({
      url: `/travel_system/comment/${id}`,
      method: 'delete'
    })
  },

  // 更新情感倾向
  updateSentiment() {
    return request({
      url: '/travel_system/comment/analyse-sentiment',
      method: 'post'
    })
  }
}
