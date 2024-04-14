import request from '@/utils/request'

export function getCollect(params) {
    return request({
        url: '/v1/collect/',
        method: 'get',
        params: params
    })
}
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
