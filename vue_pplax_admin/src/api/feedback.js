import service from '@/utils/request'

/**
 * 获得反馈列表
 * @param params
 * @returns {*}
 */
export function getFeedbackList(params) {
  return service({
    url: '/admin/feedback/list',
    method: 'get',
    params
  })
}

/**
 * 更新反馈
 * @param blogUid
 * @param params
 * @returns {*}
 */
export function editFeedback(blogUid, params) {
  return service({
    url: '/admin/feedback/' + blogUid,
    method: 'put',
    data: params
  })
}


/**
 * 删除反馈
 * @param feedbackUid
 * @returns {*}
 */
export function deleteFeedback(feedbackUid) {
  return service({
    url: '/admin/feedback/' + feedbackUid,
    method: 'delete'
  })
}

/**
 * 批量删除反馈
 * @param feedbackUids
 * @returns {*}
 */
export function deleteFeedbackBatch(feedbackUids) {
  return service({
    url: '/admin/feedback',
    method: 'delete',
    data: feedbackUids
  })
}
