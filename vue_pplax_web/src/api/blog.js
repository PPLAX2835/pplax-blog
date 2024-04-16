import request from '@/utils/request'

export function fetchBlogList(params) {
    return request({
        url: '/web/blog/list',
        method: 'get',
        params: params
    })
}

export function getBlog(uid) {
    return request({
        url: '/web/blog/' + uid,
        method: 'get'
    })
}

export function blogLike(id) {
    return request({
        url: '/web/blog/' + id + '/like',
        method: 'post'
    })
}