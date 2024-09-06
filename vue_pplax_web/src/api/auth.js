import service from "../utils/request";
import request from "@/utils/request";

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
 * 注册
 * @param data
 * @returns {*}
 */
export function register(data) {
  return service({
    url: '/pOauth/register',
    method: 'post',
    data
  })
}

/**
 * 编辑密码
 * @param data
 * @returns {*}
 */
export function editPassword(data) {
  return request({
    url: '/pOauth/password',
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
