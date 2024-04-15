import request from '@/utils/request'

export function fetchBlogSortList() {
    return request({
        url: '/web/blogSort/list',
        method: 'get'
    })
}