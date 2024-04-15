import request from '@/utils/request'

export function fetchBlogList(params) {
    return request({
        url: '/web/blog/list',
        method: 'get',
        params: params
    })
}
