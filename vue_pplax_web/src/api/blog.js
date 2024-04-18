import request from '@/utils/request'

export function searchBlog(params) {
    return request({
        url: '/web/blog/search',
        method: 'get',
        params: params
    })
}

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

export function deleteBlog(id) {
    return request({
        url: '/web/blog/' + id,
        method: 'delete'
    })
}

/**
 * 添加博客
 * @param params
 * @returns {*}
 */
export function addBlog(params) {
    return request({
        url: '/web/blog',
        method: 'post',
        data: params
    })
}

/**
 * 修改博客
 * @param blogUid
 * @param params
 * @returns {*}
 */
export function updateBlog(blogUid, params) {
    return request({
        url: '/web/blog/' + blogUid,
        method: 'put',
        data: params
    })
}
