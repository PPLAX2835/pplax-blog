import request from '@/utils/request'
import user from "@/view/user";

// 聊天室接口
export function addRoom(userId) {
    return request({
        url: '/v1/im/addRoom',
        method: 'post',
        params: {
            userId: userId
        }
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
