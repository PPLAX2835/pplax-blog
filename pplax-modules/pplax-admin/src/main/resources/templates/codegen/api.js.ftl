import service from "../utils/request";


/**
* @description ${tableName}表 api
* @author ${author}
* @date ${date}
*/

/**
 * 列表
 * @param params
 * @returns {*}
 */
export function get${className}List(params) {
  return service({
    url: '/admin/${apiName}/list',
    method: 'get',
    params
  })
}

/**
 * 获得详情
 * @param uid
 * @returns {*}
 */
export function get${className}(uid) {
  return service({
    url: '/admin/${apiName}/' + uid,
    method: 'get'
  })
}

/**
 * 添加
 * @param data
 * @returns {*}
 */
export function add${className}(data) {
  return service({
    url: '/admin/${apiName}',
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
export function update${className}(uid, data) {
  return service({
    url: '/admin/${apiName}/' + uid,
    method: 'put',
    data: data
  })
}


/**
 * 删除博客
 * @param blogUid
 * @returns {*}
 */
export function delete${className}(uid) {
  return service({
    url: '/admin/${apiName}/' + uid,
    method: 'delete'
  })
}

/**
 * 批量删除博客
 * @param uids
 * @returns {*}
 */
export function delete${className}Batch(uids) {
  return service({
    url: '/admin/${apiName}',
    method: 'delete',
    data: uids
  })
}
