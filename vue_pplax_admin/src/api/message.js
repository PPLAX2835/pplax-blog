import service from '@/utils/request'

/**
 * 获得聊天记录列表
 * @param params
 * @returns {*}
 */
export function getMessageList(params) {
  return service({
    url: '/admin/message/list',
    method: 'get',
    params
  })
}

/**
 * 删除聊天记录
 * @param chatMessageUid
 * @returns {*}
 */
export function deleteMessage(chatMessageUid) {
  return service({
    url: '/admin/message/' + chatMessageUid,
    method: 'delete'
  })
}

/**
 * 批量删除聊天记录
 * @param chatMessageUids
 * @returns {*}
 */
export function deleteMessageBatch(chatMessageUids) {
  return service({
    url: '/admin/message',
    method: 'delete',
    data: chatMessageUids
  })
}

