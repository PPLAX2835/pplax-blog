import service from '@/utils/request'

export function getSayList(params) {
  return service({
    url: '/admin/say/list',
    method: 'get',
    params
  })
}


export function addSay(params) {
  return service({
    url: '/admin/say',
    method: 'post',
    data: params
  })
}

export function updateSay(sayUid, params) {
  return service({
    url: '/admin/say/' + sayUid,
    method: 'put',
    data: params
  })
}

export function deleteSay(sayUid) {
  return service({
    url: '/admin/say/' + sayUid,
    method: 'delete'
  })
}

export function deleteSayBatch(sayUids) {
  return service({
    url: '/admin/say',
    method: 'delete',
    data: sayUids
  })
}

export function getAddress() {
  return service({
    url: '/admin/say/address',
    method: 'get'
  })
}
