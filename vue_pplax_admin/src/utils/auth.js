import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

const UserUidKey = 'User-Uid'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function setUserUid(userUid) {
  return Cookies.set(UserUidKey, userUid)
}

export function getUserUid() {
  return Cookies.get(UserUidKey)
}

export function removeUserUid () {
  return Cookies.remove(UserUidKey)
}
