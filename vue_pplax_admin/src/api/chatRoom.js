import service from '@/utils/request'

/**
 * 获得聊天室列表
 * @param params
 * @returns {*}
 */
export function getChatRoomList(params) {
  return service({
    url: '/admin/chatRoom/list',
    method: 'get',
    params
  })
}

/**
 * 删除聊天室
 * @param chatRoomUid
 * @returns {*}
 */
export function deleteChatRoom(chatRoomUid) {
  return service({
    url: '/admin/chatRoom/' + chatRoomUid,
    method: 'delete'
  })
}

/**
 * 批量删除聊天室
 * @param chatRoomUids
 * @returns {*}
 */
export function deleteChatRoomBatch(chatRoomUids) {
  return service({
    url: '/admin/chatRoom',
    method: 'delete',
    data: chatRoomUids
  })
}

