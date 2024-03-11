import service from "../utils/request";

/**
 * 获得博客列表
 * @param params
 * @returns {*}
 */
export function getBlogList(params) {
  return service({
    url: '/admin/blog/list',
    method: 'get',
    params
  })
}

export function addBlog(params) {
  return service({
    url: '/blog/add',
    method: 'post',
    params
  })
}

export function editBlog(params) {
  return service({
    url: '/blog/edit',
    method: 'post',
    params
  })
}

export function deleteBlog(params) {
  return service({
    url: '/blog/delete',
    method: 'post',
    params
  })
}
