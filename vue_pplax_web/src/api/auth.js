import service from "../utils/request";

/**
 * 获取token
 * @param data
 * @returns {*}
 */
export function login(data) {
  return service({
    url: '/web/auth/token',
    method: 'post',
    data
  })
}

/**
 * 登出
 * @returns {*}
 */
export function logout() {
  return service({
    url: '/web/auth/token',
    method: 'delete'
  })
}

/**
 * 获得验证码
 * @returns {*}
 */
export function getCaptcha() {
  return service({
    url: '/web/auth/captcha',
    method: 'get',
  })
}

export function editPassword(data) {
  return service({
    url: '/web/auth/password',
    method: 'put',
    data
  })
}
