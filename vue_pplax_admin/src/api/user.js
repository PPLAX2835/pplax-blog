import service from '@/utils/request'

export function getUserList(params) {
  return service({
    url: '/user',
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
