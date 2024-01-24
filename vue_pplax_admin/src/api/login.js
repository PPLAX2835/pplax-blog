import service from '@/utils/request'

export function login(param) {
  return service({
    url: '/oauth/token',
    method: 'post',
    data: param
  })
}

export function getInfo(token) {
  return service({
    url: '/oauth/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return service({
    url: '/oauth/logout',
    method: 'post'
  })
}
