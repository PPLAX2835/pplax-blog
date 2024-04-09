import service from '@/utils/request'

/**
 * 头像上传
 * @param param
 * @returns {*}
 */
export function avatarUpload(param) {

  return service({
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

  return service({
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

  return service({
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

  return service({
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

  return service({
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

  return service({
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

  return service({
    url: '/file/link/iconImage',
    method: 'POST',
    headers:{'Content-Type': 'multipart/form-data'},
    data: param
  })
}

/**
 * 获得文件列表
 * @param params
 * @returns {AxiosPromise}
 */
export function getFileStorageList(params) {
  return service({
    url: '/file/list',
    method: 'get',
    params
  })
}

export function deleteFileStorage(fileStorageUid) {
  return service({
    url: '/file/' + fileStorageUid,
    method: 'delete'
  })
}

export function deleteFileStorageBatch(fileStorageUids) {
  return service({
    url: '/file',
    method: 'delete',
    data: fileStorageUids
  })
}

/**
 * 站点logo上传
 * @param param
 * @returns {*}
 */
export function siteLogoImageUpload(param) {

  return service({
    url: '/file/site/logo',
    method: 'POST',
    headers:{'Content-Type': 'multipart/form-data'},
    data: param
  })
}

/**
 * 说说图片上传
 * @param param
 * @returns {*}
 */
export function sayImageUpload(param) {

  return service({
    url: '/file/say/image',
    method: 'POST',
    headers:{'Content-Type': 'multipart/form-data'},
    data: param
  })
}
