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
    url: '/tag/add',
    method: 'post',
    params
  })
}

export function editTag(params) {
  return service({
    url: '/tag/edit',
    method: 'post',
    params
  })
}

export function deleteTag(params) {
  return service({
    url: '/tag/delete',
    method: 'post',
    params
  })
}
