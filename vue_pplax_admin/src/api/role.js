import service from '@/utils/request'


export function getRoleList(params) {
  return service({
    url: '/admin/role/list',
    method: 'get',
    params
  })
}

export function addRole(params) {
  return service({
    url: '/admin/role',
    method: 'post',
    data: params
  })
}

export function updateRole(roleUid, params) {
  return service({
    url: '/admin/role/' + roleUid,
    method: 'put',
    data: params
  })
}

export function deleteRole(roleUid) {
  return service({
    url: '/admin/role/' + roleUid,
    method: 'delete'
  })
}

export function deleteRoleBatch(roleUids) {
  return service({
    url: '/admin/role',
    method: 'delete',
    data: roleUids
  })
}
