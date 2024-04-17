import request from '@/utils/request'

export function collect(articleId) {
    return request({
        url: '/web/blog/' + articleId + '/collect',
        method: 'post'
    })
}
export function cancelCollect(articleId) {
    return request({
        url: '/web/blog/' + articleId + '/collect',
        method: 'post'
    })
}
