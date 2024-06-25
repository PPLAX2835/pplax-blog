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
export function getChatRoomList(isOwner) {
    return request({
        url: '/web/message/room/list?isOwner=' + isOwner,
        method: 'get'
    })
}
export function searchChatRoomList(param) {
    return request({
        url: '/web/message/room/search',
        method: 'get',
        params: param
    })
}
export function joinChatRoom(chatRoomUid) {
    return request({
        url: '/web/message/room/' + chatRoomUid + '/join',
        method: 'put'
    })
}
export function deleteChatRoom(roomId) {
    return request({
        url: '/web/message/room/' + roomId,
        method: 'delete'
    })
}
export function createChatRoom(param) {
    return request({
        url: '/web/message/room',
        method: 'post',
        data: param
    })
}
export function updateChatRoom(chatRoomUid, param) {
    return request({
        url: '/web/message/room/' + chatRoomUid,
        method: 'put',
        data: param
    })
}
export function getChatRoomMemberList(chatRoomUid) {
    return request({
        url: '/web/message/room/' + chatRoomUid + '/member/list',
        method: 'get'
    })
}
export function kickChatRoomMember(chatRoomUid, memberUid) {
    return request({
        url: '/web/message/room/' + chatRoomUid + '/member/' + memberUid,
        method: 'delete'
    })
}
export function withdraw(chatRoomUid, chatMessageUid) {
    return request({
        url: '/web/message/room/' + chatRoomUid + '/chat/' + chatMessageUid,
        method: 'delete'
    })
}