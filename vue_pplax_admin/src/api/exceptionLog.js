import service from "../utils/request";


/**
* @description t_exception_log表 api
* @author PPLAX
* @date Thu Aug 29 11:06:16 CST 2024
*/

/**
 * 列表
 * @param params
 * @returns {*}
 */
export function getExceptionLogList(params) {
  return service({
    url: '/admin/exceptionLog/list',
    method: 'get',
    params
  })
}

/**
 * 获得详情
 * @param uid
 * @returns {*}
 */
export function getExceptionLog(uid) {
  return service({
    url: '/admin/exceptionLog/' + uid,
    method: 'get'
  })
}

/**
 * 添加
 * @param data
 * @returns {*}
 */
export function addExceptionLog(data) {
  return service({
    url: '/admin/exceptionLog',
    method: 'post',
    data: data
  })
}

/**
 * 修改
 * @param uid
 * @param data
 * @returns {*}
 */
export function updateExceptionLog(uid, data) {
  return service({
    url: '/admin/exceptionLog/' + uid,
    method: 'put',
    data: data
  })
}


/**
 * 删除博客
 * @param blogUid
 * @returns {*}
 */
export function deleteExceptionLog(uid) {
  return service({
    url: '/admin/exceptionLog/' + uid,
    method: 'delete'
  })
}

/**
 * 批量删除博客
 * @param uids
 * @returns {*}
 */
export function deleteExceptionLogBatch(uids) {
  return service({
    url: '/admin/exceptionLog',
    method: 'delete',
    data: uids
  })
}
