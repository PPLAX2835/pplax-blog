import service from '@/utils/request'

/**
 * 获得分类列表
 * @param params
 * @returns {*}
 */
export function getBlogSortList(params) {
  return service({
    url: '/admin/blogSort/list',
    method: 'get',
    params
  })
}

/**
 * 对指定分类进行置顶
 * @param blogSortUid
 * @returns {*}
 */
export function promote(blogSortUid) {
  return service({
    url: '/admin/blogSort/' + blogSortUid + '/promote',
    method: 'put'
  })
}

/**
 * 取消置顶
 * @param blogSortUid
 * @returns {*}
 */
export function promoteCancel(blogSortUid) {
  return service({
    url: '/admin/blogSort/' + blogSortUid + '/promote',
    method: 'delete'
  })
}

export function addBlogSort(data) {
  return service({
    url: '/admin/blogSort',
    method: 'post',
    data: data
  })
}

export function updateBlogSort(blogSortUid, data) {
  return service({
    url: '/admin/blogSort/' + blogSortUid,
    method: 'put',
    data: data
  })
}

export function deleteBlogSort(data) {
  return service({
    url: '/admin/blogSort/' + data.uid,
    method: 'delete'
  })
}

export function deleteBatchBlogSort(data) {
  return service({
    url: '/admin/blogSort/batch',
    method: 'delete',
    data: data
  })
}

