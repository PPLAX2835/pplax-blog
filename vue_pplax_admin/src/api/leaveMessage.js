import service from '@/utils/request'

/**
 * 获得留言列表
 * @param params
 * @returns {*}
 */
export function getLeaveMessageList(params) {
  return service({
    url: '/admin/leaveMessage/list',
    method: 'get',
    params
  })
}

/**
 * 删除评论
 * @param leaveMessageUid
 * @returns {*}
 */
export function deleteLeaveMessage(leaveMessageUid) {
  return service({
    url: '/admin/leaveMessage/' + leaveMessageUid,
    method: 'delete'
  })
}

/**
 * 批量删除评论
 * @param leaveMessageUids
 * @returns {*}
 */
export function deleteLeaveMessageBatch(leaveMessageUids) {
  return service({
    url: '/admin/leaveMessage',
    method: 'delete',
    data: leaveMessageUids
  })
}

