import request from '@/utils/request'

export default {

  // 获取日志记录
  getPageLog(current, limit, params) {
    return request({
      url: `/travel_system/operate-log/${current}/${limit}`,
      method: 'get',
      params
    })
  },

  // 根据id获取一条记录的详情
  getLogDetail(id) {
    return request({
      url: `/travel_system/operate-log/detail/${id}`,
      method: 'get',
    })
  },
}
