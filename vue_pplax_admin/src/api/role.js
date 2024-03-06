import service from '@/utils/request'


export function getRoleList(params) {
  return service({
    url: '/admin/role/list',
    method: 'get',
    params
  })
}
