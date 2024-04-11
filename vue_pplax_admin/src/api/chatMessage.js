import service from '@/utils/request'

/**
 * 获得聊天记录列表
 * @param params
 * @returns {*}
 */
export function getChatMessageList(params) {
  return service({
    url: '/admin/chatMessage/list',
    method: 'get',
    params
  })
}

/**
 * 删除聊天记录
 * @param chatMessageUid
 * @returns {*}
 */
export function deleteChatMessage(chatMessageUid) {
  return service({
    url: '/admin/chatMessage/' + chatMessageUid,
    method: 'delete'
  })
}

/**
 * 批量删除聊天记录
 * @param chatMessageUids
 * @returns {*}
 */
export function deleteChatMessageBatch(chatMessageUids) {
  return service({
    url: '/admin/chatMessage',
    method: 'delete',
    data: chatMessageUids
  })
}

