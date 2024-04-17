import request from "@/utils/request";


export function addFeedback(data) {
    return request({
        url: '/web/feedback',
        method: 'post',
        data
    })
}