import service from "../utils/request";
import request from "@/utils/request";

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
 * 注册
 * @param data
 * @returns {*}
 */
export function register(data) {
  return service({
    url: '/web/auth/register',
    method: 'post',
    data
  })
}
export function forgetPassword(data) {
  return request({
    url: '/web/auth/password',
    method: 'put',
    data
  })
}
/**
 * 获得图片验证码
 * @returns {*}
 */
export function getImageCaptcha() {
  return service({
    url: '/web/auth/imageCaptcha',
    method: 'get',
  })
}

/**
 * 获得邮箱验证码
 * @returns {*}
 */
export function getEmailCaptcha(email) {
  return service({
    url: '/web/auth/emailCaptcha',
    method: 'get',
    params: {
      email: email
    }
  })
}

export function editPassword(data) {
  return service({
    url: '/web/auth/password',
    method: 'put',
    data
  })
}
