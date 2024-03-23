import request from '@/utils/request'

/**
 * 头像上传
 * @param param
 * @returns {*}
 */
export function avatarUpload(param) {

  return request({
    url: '/file/user/avatar',
    method: 'POST',
    headers:{'Content-Type': 'multipart/form-data'},
    data: param
  })
}

/**
 * 个人空间背景图上传
 * @param param
 * @returns {*}
 */
export function spaceBackgroundPictureUpload(param) {

  return request({
    url: '/file/user/spaceBackgroundPicture',
    method: 'POST',
    headers:{'Content-Type': 'multipart/form-data'},
    data: param
  })
}

/**
 * 博客封面上传
 * @param blogUid
 * @param param
 * @returns {*}
 */
export function blogCoverImageUpload(param) {

  return request({
    url: '/file/blog/coverImage',
    method: 'POST',
    headers:{'Content-Type': 'multipart/form-data'},
    data: param
  })
}

/**
 * 上传博客附件文件
 * @param param
 * @returns {*}
 */
export function blogAttachUpload(param) {

  return request({
    url: '/file/blog/attach/file',
    method: 'POST',
    headers:{'Content-Type': 'multipart/form-data'},
    data: param
  })
}

/**
 * 博客内容上传图片
 * @param param
 * @returns {*}
 */
export function blogImageAttachUpload(param) {

  return request({
    url: '/file/blog/attach/image',
    method: 'POST',
    headers:{'Content-Type': 'multipart/form-data'},
    data: param
  })
}

/**
 * 博客内容上传视频
 * @param param
 * @returns {*}
 */
export function blogVideoAttachUpload(param) {

  return request({
    url: '/file/blog/attach/video',
    method: 'POST',
    headers:{'Content-Type': 'multipart/form-data'},
    data: param
  })
}

/**
 * 友链logo上传
 * @param param
 * @returns {*}
 */
export function linkIconImageUpload(param) {

  return request({
    url: '/file/link/iconImage',
    method: 'POST',
    headers:{'Content-Type': 'multipart/form-data'},
    data: param
  })
}
