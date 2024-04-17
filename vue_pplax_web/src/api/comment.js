import request from '@/utils/request'

export function featchComments(params) {
    return request({
        url: '/web/comment/list',
        method: 'get',
        params: params
    })
}

export function postComment(data) {
    return request({
        url: '/web/comment/',
        method: 'post',
        data
    })
}
