
import request from '@/utils/request'
export function listMessage(param) {
    return request({
        url: '/web/message/list',
        method: 'get',
        params: param
    })
}
export function addMessage(content, type) {
    return request({
        url: '/web/message/',
        method: 'post',
        data: {
            type: type,
            content: content
        }
    })
}