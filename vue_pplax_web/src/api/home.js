import request from '@/utils/request'

export function fetchHomeData() {
    return request({
        url: '/web/site/homeData',
        method: 'get',
    })
}
