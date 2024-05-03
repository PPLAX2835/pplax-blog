import service from '@/utils/request'

export function getSiteSettingList() {
  return service({
    url: '/admin/siteSetting/list',
    method: 'get'
  })
}

export function getSiteSettingMap() {
  return service({
    url: '/admin/siteSetting/map',
    method: 'get'
  })
}

export function addSiteSetting(params) {
  return service({
    url: '/admin/siteSetting',
    method: 'post',
    data: params
  })
}

export function updateSiteSetting(params) {
  return service({
    url: '/admin/siteSetting',
    method: 'put',
    data: params
  })
}

export function deleteSiteSetting(siteSettingUid) {
  return service({
    url: '/admin/siteSetting/' + siteSettingUid,
    method: 'delete'
  })
}
