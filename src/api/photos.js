import request from "@/utils/request";

export default {
  // 根据mark_id查询所有图片
  getParamsPhotos(mark, name) {
    return request({
      url: `/travel_system/propagandist-photos`,
      method: 'get',
      params: {
        mark,
        name
      }
    })
  },

  // 根据id删除一张图片
  delPhoto(id) {
    return request({
      url: `/travel_system/propagandist-photos/${id}`,
      method: 'delete',
    })
  },

  // 添加图片
  addPhotos(data) {
    return request({
      url: `/travel_system/propagandist-photos`,
      method: 'post',
      data
    })
  },

  // 根据图片分类获取图片所属分类的所有元素名称
  getMarkNames(mark) {
    return request({
      url: `/travel_system/propagandist-photos/markNames`,
      method: 'get',
      params: {
        mark
      }
    })
  },

  // 根据图片mark_id获取对应分页图片
  getPhotosByMarkId(id, current, limit) {
    return request({
      url: `/travel_system/propagandist-photos/${id}/${current}/${limit}`,
      method: 'get'
    })
  }

}
