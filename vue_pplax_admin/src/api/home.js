import service from "../utils/request";

/**
 * init
 * @returns {*}
 */
export function init() {
  return service({
    url: '/admin/home/init',
    method: 'get'
  })
}

