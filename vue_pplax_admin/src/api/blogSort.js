import service from '@/utils/request'

export function getBlogSortList(params) {
  return service({
    url: '/blogSort',
    method: 'get',
    params
  })
}

export function checkSortNameExists(params) {
  return service({
    url: '/blogSort/' + params.sortName + '/exists',
    method: 'get'
  })
}

export function addBlogSort(data) {
  return service({
    url: '/blogSort',
    method: 'post',
    data: data
  })
}

export function editBlogSort(data) {
  return service({
    url: '/blogSort/' + data.uid,
    method: 'put',
    data: data
  })
}

export function logicalDeleteBatchBlogSort(data) {
  return service({
    url: '/blogSort/logic-batch',
    method: 'delete',
    data: data
  })
}

export function physicalDeleteBlogSort(data) {
  return service({
    url: '/blogSort/' + data.uid,
    method: 'delete'
  })
}

export function physicalDeleteBatchBlogSort(data) {
  return service({
    url: '/blogSort/batch',
    method: 'delete',
    data: data
  })
}

