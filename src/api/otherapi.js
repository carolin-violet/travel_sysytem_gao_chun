import request from '@/utils/request'

export default {
  /*
  * 获取短信验证码
  * @phone  用户手机号
  * */
  getMsmCode(phone) {
    return request({
      url: `/travel_system/msm/send/${phone}`,
      method: 'get'
    })
  }

}
