import request from '@/utils/request'

export function fetchBlogList(params) {
    return request({
        url: '/web/blog/list',
        method: 'get',
        params: params
    })
}

export function fetchBlogCommentList(blogUid, params) {
    return request({
        url: '/web/blog/' + blogUid + '/comment/list',
        method: 'get',
        params: params
    })
}

export function postBlogComment(blogUid, data) {
    return request({
        url: '/web/blog/' + blogUid + '/comment/',
        method: 'post',
        data
    })
}

export function getBlog(blogUid) {
    return request({
        url: '/web/blog/' + blogUid,
        method: 'get'
    })
}

export function blogLike(blogUid) {
    return request({
        url: '/web/blog/' + blogUid + '/like',
        method: 'post'
    })
}