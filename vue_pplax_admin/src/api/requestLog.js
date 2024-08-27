import service from "../utils/request";


/**
* @description t_request_log表 api
* @author PPLAX
* @date Tue Aug 27 14:29:54 CST 2024
*/

/**
 * 列表
 * @param params
 * @returns {*}
 */
export function getRequestLogList(params) {
  return service({
    url: '/admin/requestLog/list',
    method: 'get',
    params
  })
}

/**
 * 获得详情
 * @param uid
 * @returns {*}
 */
export function getRequestLog(uid) {
  return service({
    url: '/admin/requestLog/' + uid,
    method: 'get'
  })
}

/**
 * 添加
 * @param data
 * @returns {*}
 */
export function addRequestLog(data) {
  return service({
    url: '/admin/requestLog',
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
export function updateRequestLog(uid, data) {
  return service({
    url: '/admin/requestLog/' + uid,
    method: 'put',
    data: data
  })
}


/**
 * 删除博客
 * @param blogUid
 * @returns {*}
 */
export function deleteRequestLog(uid) {
  return service({
    url: '/admin/requestLog/' + uid,
    method: 'delete'
  })
}

/**
 * 批量删除博客
 * @param uids
 * @returns {*}
 */
export function deleteRequestLogBatch(uids) {
  return service({
    url: '/admin/requestLog',
    method: 'delete',
    data: uids
  })
}
