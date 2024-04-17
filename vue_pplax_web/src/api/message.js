import request from '@/utils/request'


export function listLeaveMessage(param) {
    return request({
        url: '/web/message/leave/list',
        method: 'get',
        params: param
    })
}
export function addLeaveMessage(param) {
    return request({
        url: '/web/message/leave',
        method: 'post',
        data: param
    })
}
export function listChatMessage(chatRoomUid, param) {
    return request({
        url: '/web/message/room/' + chatRoomUid + '/chat/list',
        method: 'get',
        params: param
    })
}
export function addChatMessage(chatRoomUid, param) {
    return request({
        url: '/web/message/room/' + chatRoomUid + '/chat',
        method: 'post',
        data: param
    })
}
export function read(chatRoomUid) {
    return request({
        url: '/web/message/room/' + chatRoomUid + '/chat/read',
        method: 'get'
    })
}
export function getChatRoomList(userUid) {
    return request({
        url: '/web/message/room/list',
        method: 'get',
        params: {
            userUid: userUid
        }
    })
}
export function deleteChatRoom(roomId) {
    return request({
        url: '/web/message/room/' + roomId,
        method: 'delete'
    })
}