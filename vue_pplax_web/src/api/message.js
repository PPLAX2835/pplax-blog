
import request from '@/utils/request'
export function listMessage(param) {
    return request({
        url: '/web/leaveMessage/list',
        method: 'get',
        params: param
    })
}
export function addMessage(content) {
    return request({
        url: '/web/leaveMessage/',
        method: 'post',
        data: {
            content: content
        }
    })
}