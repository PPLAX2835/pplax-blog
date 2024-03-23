import service from '@/utils/request'

export function getLinkList(params) {
  return service({
    url: '/admin/link/list',
    method: 'get',
    params
  })
}

export function addLink(data) {
  return service({
    url: '/admin/link',
    method: 'post',
    data: data
  })
}

export function editLink(linkUid, data) {
  return service({
    url: '/admin/link/' + linkUid,
    method: 'put',
    data: data
  })
}

export function deleteLink(linkUid) {
  return service({
    url: '/admin/link/' + linkUid,
    method: 'delete'
  })
}

export function deleteLinkBatch(linkUids) {
  return service({
    url: '/admin/link',
    method: 'delete',
    data: linkUids
  })
}
