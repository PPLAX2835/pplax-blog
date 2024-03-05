import service from '@/utils/request'

export function getList(params) {
  return service({
    url: '/user/list',
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
    url: '/user/' + userUid + '/userInfo',
    method: 'get',
  })
}

/**
 * 获取用户的角色，包含菜单
 * @param userUid
 * @returns {*}
 */
export function getRoleWithMenu(userUid) {
  return service({
    url: '/user/' + userUid + '/role',
    method: 'get',
  })
}
