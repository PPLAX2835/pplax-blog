import service from '@/utils/request'

export function getFeedbackList(params) {
  return service({
    url: '/feedback',
    method: 'get',
    params
  })
}

export function addFeedback(data) {
  return service({
    url: '/feedback',
    method: 'post',
    data: data
  })
}

export function editFeedback(data) {
  return service({
    url: '/feedback/' + data.uid,
    method: 'put',
    data: data
  })
}

export function physicalDeleteFeedback(data) {
  return service({
    url: '/feedback/' + data.uid,
    method: 'delete'
  })
}
