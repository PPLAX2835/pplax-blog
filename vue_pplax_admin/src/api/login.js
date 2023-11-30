import service from '@/utils/request'

export function login(param) {
  return service({
    url: '/admin/login',
    method: 'post',
    data: param
  })
}

export function getInfo(token) {
  return service({
    url: '/admin/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return service({
    url: '/admin/logout',
    method: 'post'
  })
}
