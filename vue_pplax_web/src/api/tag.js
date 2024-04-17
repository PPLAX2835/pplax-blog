import request from '@/utils/request'

export function fetchTagList(param) {
    return request({
        url: '/web/tag/list',
        method: 'get',
        params: param
    })
}

export function addTag(params) {
    return request({
        url: '/web/tag',
        method: 'post',
        data: params
    })
}
