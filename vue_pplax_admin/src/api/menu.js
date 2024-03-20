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
