import request from '@/utils/request'


export function avatarUpload(userUid, param) {

  return request({
    url: '/file/user/' + userUid + '/avatar',
    method: 'POST',
    headers:{'Content-Type': 'multipart/form-data'},
    data: param
  })
}
