import service from '@/utils/request'

export function getBlogSortList(params) {
  return service({
    url: '/blogSort/getList',
    method: 'get',
    params
  })
}

export function addBlogSort(data) {
  return service({
    url: '/blogSort/add',
    method: 'post',
    data: data
  })
}

export function editBlogSort(data) {
  return service({
    url: '/blogSort/edit',
    method: 'post',
    data: data
  })
}

export function deleteBlogSort(data) {
  return service({
    url: '/blogSort/delete',
    method: 'post',
    data: data
  })
}
