import request from '@/utils/request'

export default {
  // 获取所有管理员信息
  getPageManager(current, limit, params) {
    return request({
      url: `/travel_system/manager/${current}/${limit}`,
      method: 'get',
      params
    })
  },

  // 添加管理员
  addManager(data) {
    return request({
      url: '/travel_system/manager/addManager',
      method: 'post',
      data
    })
  },

  // 修改管理员信息
  updateManager(data) {
    return request({
      url: '/travel_system/manager/updateManager',
      method: 'put',
      data
    })
  },

  // 修改管理员密码
  changePassword(data) {
    return request({
      url: '/travel_system/manager/changePassword',
      method: 'patch',
      data
    })
  },

  // 根据id删除用户
  removeManager(id) {
    return request({
      url: `/travel_system/manager/${id}`,
      method: 'delete'
    })
  },

  // 查询所有角色
  getAllRole() {
    return request({
      url: '/travel_system/role/findAll',
      method: 'get'
    })
  },

  // 根据用户id查询用户权限
  getPermissionById(id) {
    return request({
      url: `/travel_system/permission/findAll/${id}`,
      method: 'get'
    })
  },

  // 根据用户id给用户添加权限
  addPermission(data) {
    return request({
      url: 'travel_system/permission/addPermission',
      method: 'post',
      data
    })
  },

  // 根据权限id删除权限信息
  delPermission(id) {
    return request({
      url: `/travel_system/permission/${id}`,
      method: 'delete'
    })
  },

}
