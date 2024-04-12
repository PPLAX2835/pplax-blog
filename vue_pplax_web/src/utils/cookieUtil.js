import Cookies from 'js-cookie'

export function setToken(value) {
    return Cookies.set("token", value, { expires: 7 });
}
export function getToken() {
    return Cookies.get("token");
}
export function removeToken() {
    return Cookies.remove("token");
}
export function setUserUid(userUid) {
    return Cookies.set('userUid', userUid)
}
export function getUserUid() {
    return Cookies.get('userUid')
}
export function removeUserUid() {
    return Cookies.remove('userUid')
}
export function setUrl(value) {
    return window.sessionStorage.setItem("baseUrl", value)
}
export function getUrl() {
    return window.sessionStorage.getItem("baseUrl")
}
export function setNotice(value) {
    return Cookies.set("notice", value, { expires: 90 });
}
export function getNotice() {
    return Cookies.get("notice");
}