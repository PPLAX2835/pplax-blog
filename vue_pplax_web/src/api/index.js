import request from '@/utils/request'

export function fetchBlogList(params) {
    return request({
        url: '/web/blog/list',
        method: 'get',
        params: params
    })
}
export function getArticleByUserId(params) {
    return request({
        url: '/v1/article/selectArticleByUserId',
        method: 'get',
        params: params
    })
}
export function deleteMyArticle(id) {
    return request({
        url: '/v1/article/',
        method: 'delete',
        params: {
            id: id
        }
    })
}
export function getMyArticleInfo(id) {
    return request({
        url: '/v1/article/selectMyArticleInfo',
        method: 'get',
        params: {
            id: id
        }
    })
}
export function readMarkdownFile(data) {
    return request({
        url: '/v1/article/readMarkdownFile',
        method: 'post',
        data
    })
}
export function insertArticle(data) {
    return request({
        url: '/v1/article/',
        method: 'post',
        data
    })
}
export function updateArticle(data) {
    return request({
        url: '/v1/article/',
        method: 'put',
        data
    })
}
export function searchArticle(params) {
    return request({
        url: '/v1/article/search',
        method: 'get',
        params: params
    })
}

export function getBlog(uid) {
    return request({
        url: '/web/blog/' + uid,
        method: 'get'
    })
}

export function checkCode(code) {
    return request({
        url: '/v1/article/checkCode',
        method: 'get',
        params: {
            code: code
        }
    })
}
export function articleLike(id) {
    return request({
        url: '/web/blog/' + id + '/like',
        method: 'post'
    })
}
export function archive() {
    return request({
        url: '/web/archive',
        method: 'get',
    })
}

export function fetchTagList(param) {
    return request({
        url: '/web/tag/list',
        method: 'get',
        params: param
    })
}

export function featchHomeData() {
    return request({
        url: '/web/site/homeData',
        method: 'get',
    })
}
export function getHot(type) {
    return request({
        url: '/v1/hot',
        method: 'get',
        params: {
            type: type
        }
    })
}
export function report() {
    return request({
        url: '/web/site/report',
        method: 'get',
    })
}
export function getWebSiteInfo() {
    return request({
        url: '/web/site/info',
        method: 'get',
    })
}

export function emailLogin(data) {
    return request({
        url: '/oauth/emailLogin',
        method: 'post',
        data
    })
}
export function forgetPassword(data) {
    return request({
        url: '/oauth/forgetPassword',
        method: 'put',
        data
    })
}
export function logout() {
    return request({
        url: '/logout',
        method: 'get'
    })
}
export function openAuthUrl(source) {
    return request({
        url: '/oauth/render/' + source,
        method: 'get'
    })
}
export function wxIsLogin(loginCode) {
    return request({
        url: '/oauth/wechat/is_login',
        method: 'get',
        params: {
            loginCode: loginCode
        }
    })
}
export function getWechatLoginCode() {
    return request({
        url: '/oauth/wechatLoginCode',
        method: 'get',

    })
}
export function sendEmailCode(email) {
    return request({
        url: '/oauth/sendEmailCode',
        method: 'get',
        params: {
            email: email
        }
    })
}
export function emailRegister(data) {
    return request({
        url: '/oauth/emailRegister',
        method: 'post',
        data
    })
}
export function updateUserInfo(data) {
    return request({
        url: '/v1/user/',
        method: 'put',
        data
    })
}
export function getUserInfo(userId) {
    return request({
        url: '/v1/user/info',
        method: 'get',
        params: {
            userId: userId
        }
    })
}
export function selectUserInfoByToken(token) {
    return request({
        url: '/v1/user/selectUserInfoByToken',
        method: 'get',
        params: {
            token: token
        }
    })
}
export function getUserCount(id) {
    return request({
        url: '/v1/user/getUserCount',
        method: 'get',
        params: {
            id: id
        }
    })
}
export function upload(data) {
    return request({
        url: '/file/upload',
        method: 'POST',
        headers: { 'Content-Type': 'multipart/articles-data' },
        data
    })
}
export function featchBlogSortList() {
    return request({
        url: '/web/blogSort/list',
        method: 'get'
    })
}

export function addFeedback(data) {
    return request({
        url: '/v1/feedback/',
        method: 'post',
        data
    })
}
export function getCollect() {
    return request({
        url: '/v1/collect/',
        method: 'get'
    })
}
export function collect(id) {
    return request({
        url: '/v1/collect/collect',
        method: 'get',
        params: {
            articleId: id
        }
    })
}
export function cancelCollect(id) {
    return request({
        url: '/v1/collect/',
        method: 'delete',
        params: {
            articleId: id
        }
    })
}

export function followedUser(userId) {
    return request({
        url: '/v1/followed/insertFollowed',
        method: 'post',
        params: {
            userId: userId
        }
    })
}
export function deleteFollowedUser(userId) {
    return request({
        url: '/v1/followed/deleteFollowed',
        method: 'delete',
        params: {
            userId: userId
        }
    })
}

