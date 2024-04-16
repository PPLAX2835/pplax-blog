import service from '@/utils/request'

export function getMyUserInfo() {
  return service({
    url: '/web/user/userInfo',
    method: 'get',
  })
}

/**
 * 获得用户信息
 * @param userUid
 * @returns {*}
 */
export function getUserInfo(userUid) {
  return service({
    url: '/web/user/' + userUid + '/userInfo',
    method: 'get',
  })
}

/**
 * 更新用户信息
 * @param userUid
 * @param params
 * @returns {*}
 */
export function updateUserInfo(userUid, params) {
  return service({
    url: '/web/user/' + userUid + '/userInfo',
    method: 'put',
    data: params
  })
}
