import service from "../utils/request";

/**
 * 获取token
 * @param data
 * @returns {*}
 */
export function login(data) {
  return service({
    url: '/pOauth/token',
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
    url: '/pOauth/token',
    method: 'delete'
  })
}

/**
 * 获得验证码
 * @returns {*}
 */
export function getCaptcha() {
  return service({
    url: '/pOauth/imageCaptcha',
    method: 'get',
  })
}

/**
 * 获得邮箱验证码
 * @returns {*}
 */
export function getEmailCaptcha(email) {
  return service({
    url: '/pOauth/emailCaptcha',
    method: 'get',
    params: {
      email: email
    }
  })
}

/**
 * 编辑密码
 * @param data
 * @returns {*}
 */
export function editPassword(data) {
  return service({
    url: '/pOauth/password',
    method: 'put',
    data
  })
}
