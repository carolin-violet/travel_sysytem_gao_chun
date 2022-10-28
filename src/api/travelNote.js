import request from '@/utils/request'

export default {
  /*
  * 分页查询游记
  * @current 当前页码
  * @limit 每页的记录数
  * */
  getPageNote(current, limit, params) {
    return request({
      url: `/travel_system/travel-note/pageNote/${current}/${limit}`,
      method: 'get',
      params
    })
  },

  // 根据id删除游记
  removeNote(id) {
    return request({
      url: `/travel_system/travel-note/${id}`,
      method: 'delete'
    })
  }
}
