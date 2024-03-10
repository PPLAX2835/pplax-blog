import service from '@/utils/request'

export function getTagList(params) {
  return service({
    url: '/admin/tag/list',
    method: 'get',
    params
  })
}

export function addTag(params) {
  return service({
    url: '/admin/tag',
    method: 'post',
    data: params
  })
}

export function updateTag(tagUid, params) {
  return service({
    url: '/admin/tag/' + tagUid,
    method: 'put',
    data: params
  })
}

export function deleteTag(tagUid) {
  return service({
    url: '/admin/tag/' + tagUid,
    method: 'delete'
  })
}

export function deleteTagBatch(tagUids) {
  return service({
    url: '/admin/tag',
    method: 'delete',
    data: tagUids
  })
}
