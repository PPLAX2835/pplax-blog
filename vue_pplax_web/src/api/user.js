import service from '@/utils/request'
import request from "@/utils/request";
import user from "@/view/user";

export function getMyUserInfo() {
  return service({
    url: '/web/user/userInfo',
    method: 'get',
  })
}
export function updateMyUserInfo(data) {
  return request({
    url: '/web/user/userInfo',
    method: 'put',
    data
  })
}
export function updateMyBackground(data) {
  return request({
    url: '/web/user/userInfo/background',
    method: 'put',
    data
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

export function isUsernameExist(username) {
  return service({
    url: '/web/user/exist',
    method: 'get',
    params: {
      username: username
    }
  })
}
/**
 * 更新用户信息
 * @param userUid
 * @param params
 * @returns {*}
 */
export function updateUserInfo(userUid, data) {
  return request({
    url: '/web/user/' + userUid + '/userInfo',
    method: 'put',
    data
  })
}
export function getBlogListByUserUid(userUid, params) {
  return request({
    url: '/web/user/' + userUid + '/space/blog/list',
    method: 'get',
    params: params
  })
}
export function getUserCount(userUid) {
  return request({
    url: '/web/user/' + userUid + '/space/count',
    method: 'get'
  })
}