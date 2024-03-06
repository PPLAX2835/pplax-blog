import request from '@/utils/request'

/**
 * 获取token
 * @param data
 * @returns {*}
 */
export function login(data) {
  return request({
    url: '/admin/auth/token',
    method: 'post',
    data
  })
}

/**
 * 登出
 * @returns {*}
 */
export function logout() {
  return request({
    url: '/admin/auth/token',
    method: 'delete'
  })
}

/**
 * 获得验证码
 * @returns {*}
 */
export function getCaptcha() {
  return request({
    url: '/admin/auth/captcha',
    method: 'get',
  })
}
