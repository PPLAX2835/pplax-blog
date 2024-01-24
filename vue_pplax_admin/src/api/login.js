import service from '@/utils/request'

export function login(param) {
  return service({
    url: '/admin/oauth/token',
    method: 'post',
    data: param
  })
}

export function getInfo(token) {
  return service({
    url: '/admin/oauth/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return service({
    url: '/admin/oauth/logout',
    method: 'post'
  })
}
