import service from "../utils/request";

/**
 * 获得菜单树
 * @returns {*}
 */
export function getMenuTree() {
  return service({
    url: '/admin/menu/tree',
    method: 'get'
  })
}

/**
 * 添加菜单
 * @param params
 * @returns {*}
 */
export function addMenu(params) {
  return service({
    url: '/admin/menu',
    method: 'post',
    data: params
  })
}

/**
 * 更新菜单
 * @param menuUid
 * @param params
 * @returns {*}
 */
export function updateMenu(menuUid, params) {
  return service({
    url: '/admin/menu/' + menuUid,
    method: 'put',
    data: params
  })
}

/**
 * 删除菜单
 * @param menuUid
 * @returns {*}
 */
export function deleteMenu(menuUid) {
  return service({
    url: '/admin/menu/' + menuUid,
    method: 'delete'
  })
}
