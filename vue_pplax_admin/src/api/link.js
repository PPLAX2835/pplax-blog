import service from '@/utils/request'

export function getLinkList(params) {
  return service({
    url: '/link',
    method: 'get',
    params
  })
}

export function addLink(data) {
  return service({
    url: '/link',
    method: 'post',
    data: data
  })
}

export function editLink(data) {
  return service({
    url: '/link/' + data.uid,
    method: 'put',
    data: data
  })
}

export function physicalDeleteLink(data) {
  return service({
    url: '/link/' + data.uid,
    method: 'delete'
  })
}
