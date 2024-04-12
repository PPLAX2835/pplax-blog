import request from '@/utils/request'
import user from "@/view/user";

// 聊天室接口
export function getImHistory(params) {
    return request({
        url: '/v1/im/',
        method: 'get',
        params: params
    })
}
export function getRoomList(userUid) {
    return request({
        url: '/web/chat/room/list',
        method: 'get',
        params: {
            userUid: userUid
        }
    })
}
export function addRoom(userId) {
    return request({
        url: '/v1/im/addRoom',
        method: 'post',
        params: {
            userId: userId
        }
    })
}
export function send(data) {
    return request({
        url: '/v1/im/chat',
        method: 'post',
        data
    })
}
export function withdraw(data) {
    return request({
        url: '/v1/im/withdraw',
        method: 'post',
        data
    })
}
export function getUserImHistoryList(params) {
    return request({
        url: '/v1/im/selectUserImHistory',
        method: 'get',
        params: params
    })
}
export function read(userId) {
    return request({
        url: '/v1/im/read',
        method: 'get',
        params: {
            userId: userId
        }
    })
}
export function deleteRoom(roomId) {
    return request({
        url: '/v1/im/deleteRoom',
        method: 'delete',
        params: {
            roomId: roomId
        }
    })
}
export function getMessageNotice(params) {
    return request({
        url: '/v1/im/getMessageNotice',
        method: 'get',
        params: params
    })
}
export function getNewSystemNotice() {
    return request({
        url: '/v1/im/getNewSystemNotice',
        method: 'get',
    })
}
export function deleteMessage(params) {
    return request({
        url: '/v1/im/deleteMessage',
        method: 'delete',
        params: params
    })
}
