import service from '@/utils/request'

export function getUserList(params) {
  return service({
    url: '/admin/user/list',
    method: 'get',
    params
  })
}

/**
 * 获得用户信息
 * @param userUid
 * @returns {*}
 */
export function getUserInfo(userUid) {
  return service({
    url: '/admin/user/' + userUid + '/userInfo',
    method: 'get',
  })
}

/**
 * 更新用户信息
 * @param userUid
 * @param params
 * @returns {*}
 */
export function updateUserInfo(userUid, params) {
  return service({
    url: '/admin/user/' + userUid + '/userInfo',
    method: 'put',
    data: params
  })
}

/**
 * 添加用户
 * @param params
 * @returns {*}
 */
export function addUser(params) {
  return service({
    url: '/admin/user',
    method: 'post',
    data: params
  })
}

/**
 * 删除用户
 * @param userUid
 * @returns {*}
 */
export function deleteUser(userUid) {
  return service({
    url: '/admin/user/' + userUid,
    method: 'delete'
  })
}

/**
 * 踢用户下线
 * @param userUid
 * @returns {*}
 */
export function kickUser(userUid) {
  return service({
    url: '/admin/user/' + userUid + '/kick',
    method: 'delete'
  })
}

export function deleteUserBatch(userUids) {
  return service({
    url: '/admin/user',
    method: 'delete',
    data: userUids
  })
}

export function isUsernameExist(username) {
  return service({
    url: '/admin/user/exist',
    method: 'get',
    params: {
      username: username
    }
  })
}

/**
 * 获取用户的角色，包含菜单
 * @param userUid
 * @returns {*}
 */
export function getRoleWithMenu(userUid) {
  return service({
    url: '/admin/user/' + userUid + '/role',
    method: 'get',
  })
}
