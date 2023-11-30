import service from '@/utils/request'

export function getUserList(params) {
  return service({
    url: '/user',
    method: 'get',
    params
  })
}
