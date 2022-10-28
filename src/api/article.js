import request from '@/utils/request'

export default {
  // 发布文章
  addArticle(data) {
    return request({
      url: `/travel_system/article`,
      method: 'post',
      data
    })
  },

  // 根据mark_id获取文章
  getArticleById(id) {
    return request({
      url: `/travel_system/article/${id}`,
      method: 'get'
    })
  },

  // 根据id修改文章
  updateArticle(data) {
    return request({
      url: `/travel_system/article`,
      method: 'put',
      data
    })
  }
}
