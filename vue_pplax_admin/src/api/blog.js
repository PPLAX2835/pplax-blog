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

/**
 * 获得博客内容
 * @param blogUid
 * @returns {*}
 */
export function getBlogContent(blogUid) {
  return service({
    url: '/admin/blog/' + blogUid + '/content',
    method: 'get'
  })
}

/**
 * 添加博客
 * @param params
 * @returns {*}
 */
export function addBlog(params) {
  return service({
    url: '/admin/blog',
    method: 'post',
    data: params
  })
}

/**
 * 修改博客
 * @param blogUid
 * @param params
 * @returns {*}
 */
export function updateBlog(blogUid, params) {
  return service({
    url: '/admin/blog/' + blogUid,
    method: 'put',
    data: params
  })
}

/**
 * 删除博客
 * @param blogUid
 * @returns {*}
 */
export function deleteBlog(blogUid) {
  return service({
    url: '/admin/blog/' + blogUid,
    method: 'delete'
  })
}

/**
 * 批量删除博客
 * @param blogUids
 * @returns {*}
 */
export function deleteBlogBatch(blogUids) {
  return service({
    url: '/admin/blog',
    method: 'delete',
    data: blogUids
  })
}
