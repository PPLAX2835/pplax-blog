import service from '@/utils/request'

export function login(param) {
  return service({
    url: '/oauth/token',
    method: 'post',
    data: param
  })
}

export function getInfo() {
  return service({
    url: '/oauth/info',
    method: 'get'
  })
}

export function logout() {
  return service({
    url: '/oauth/logout',
    method: 'post'
  })
}
