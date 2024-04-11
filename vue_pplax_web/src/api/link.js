import request from '@/utils/request'

export function featchLinks(param) {
    return request({
        url: '/web/link/list',
        method: 'get',
        params: param
    })
}
export function addLink(data) {
    return request({
        url: '/web/link',
        method: 'post',
        data
    })
}

/**
 * 友链logo上传
 * @param param
 * @returns {*}
 */
export function linkIconImageUpload(param) {

    return request({
        url: '/file/link/iconImage',
        method: 'POST',
        headers:{'Content-Type': 'multipart/form-data'},
        data: param
    })
}