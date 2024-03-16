import service from '@/utils/request'

/**
 * 获得评论列表
 * @param params
 * @returns {*}
 */
export function getCommentList(params) {
  return service({
    url: '/admin/comment/list',
    method: 'get',
    params
  })
}

/**
 * 删除评论
 * @param commentUid
 * @returns {*}
 */
export function deleteComment(commentUid) {
  return service({
    url: '/admin/comment/' + commentUid,
    method: 'delete'
  })
}

/**
 * 批量删除评论
 * @param commentUids
 * @returns {*}
 */
export function deleteCommentBatch(commentUids) {
  return service({
    url: '/admin/comment',
    method: 'delete',
    data: commentUids
  })
}

