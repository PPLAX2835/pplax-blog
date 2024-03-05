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

/**
 * 判断是否有该路径的权限
 * @param menuList
 * @param path
 * @returns {boolean|boolean|*}
 */
export function hasAuth(menuList, path) {
  console.log(menuList)

  for (let i = 0; i < menuList.length; i++) {
    let menu = menuList[i]

    if (menu.endpoint === path) {
      return true
    }

    if (menu.childMenuList.length > 0 && hasAuth(menu.childMenuList, path)) {
      return true
    }
  }

  return false
}

