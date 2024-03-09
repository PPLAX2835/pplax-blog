import service from '@/utils/request'


export function getRoleList() {
  return service({
    url: '/admin/role/list',
    method: 'get'
  })
}
