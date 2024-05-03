import request from '@/utils/request'

// 说说接口

export function addSay(params) {
    return request({
        url: '/web/say',
        method: 'post',
        data: params
    })
}

export function getSayList(params) {
    return request({
        url: '/web/say/list',
        method: 'get',
        params: params
    })
}

export function getSayCommentList(uid, params) {
    return request({
        url: '/web/say/' + uid + '/comment/list',
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

export function deleteSay(sayUid) {
    return request({
        url: '/web/say/' + sayUid,
        method: 'delete'
    })
}

export function postSayComment(sayUid, data) {
    return request({
        url: '/web/say/' + sayUid + '/comment',
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

export function getAddress() {
    return request({
        url: '/web/say/address',
        method: 'get'
    })
}
