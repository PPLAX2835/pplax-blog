import request from '@/utils/request'

// 说说接口
export function getSayList(params) {
    return request({
        url: '/web/say/list',
        method: 'get',
        params: params
    })
}

export function sayLike(sayId) {
    return request({
        url: '/web/say/' + sayId + '/like',
        method: 'post'
    })
}

export function sayComment(data) {
    return request({
        url: '/v1/say/comment',
        method: 'post',
        data
    })
}

export function insertSay(data) {
    return request({
        url: '/v1/say/insertSay',
        method: 'post',
        data
    })
}