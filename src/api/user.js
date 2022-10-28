import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/travel_system/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/travel_system/manager/getInfo',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/travel_system/logout',
    method: 'post'
  })
}

// 获取手机验证码
export function getCode(phone) {
  return request({
    url: `/travel_system/msm/send/${phone}`,
    method: 'get'
  })
}

// 获取RSA公钥,后端根据ip判断唯一
export function getPublickey() {
  return request({
    url: `/travel_system/security/publicKey`,
    method: 'get'
  })
}
