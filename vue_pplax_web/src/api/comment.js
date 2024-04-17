import request from '@/utils/request'

export function commentReply(commentUid, data) {
    return request({
        url: '/web/comment/' + commentUid + '/reply',
        method: 'post',
        data
    })
}
