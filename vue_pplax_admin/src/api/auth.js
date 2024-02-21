import request from '@/utils/request'

/**
 * 获取token
 * @param data
 * @returns {*}
 */
export function login(data) {
  return request({
    url: '/auth/token',
    method: 'post',
    data
  })
}

/**
 * 获得当前用户信息
 * @returns {*}
 */
export function getCurrentUserInfo() {
  return request({
    url: '/auth/info',
    method: 'get'
  })
}

/**
 * 登出
 * @returns {*}
 */
export function logout() {
  return request({
    url: '/auth/token',
    method: 'delete'
  })
}

/**
 * 获得验证码
 * @returns {*}
 */
export function getCaptcha() {
  return request({
    url: '/auth/captcha',
    method: 'get',
  })
}
