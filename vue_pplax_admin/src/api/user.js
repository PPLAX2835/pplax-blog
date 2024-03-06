import service from '@/utils/request'

export function getUserList(params) {
  return service({
    url: '/admin/user/list',
    method: 'get',
    params
  })
}

/**
 * 获得用户信息
 * @param userUid
 * @returns {*}
 */
export function getUserInfo(userUid) {
  return service({
    url: '/admin/user/' + userUid + '/userInfo',
    method: 'get',
  })
}

export function updateUserInfo(userUid, params) {
  return service({
    url: '/admin/user/' + userUid + '/userInfo',
    method: 'put',
    data: params
  })
}

/**
 * 获取用户的角色，包含菜单
 * @param userUid
 * @returns {*}
 */
export function getRoleWithMenu(userUid) {
  return service({
    url: '/admin/user/' + userUid + '/role',
    method: 'get',
  })
}
