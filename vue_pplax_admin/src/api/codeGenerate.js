import service from '@/utils/request'


export function getTableList(params) {
  return service({
    url: '/admin/codeGenerate/table/list',
    method: 'get',
    params
  })
}

export function getTable(tableName) {
  return service({
    url: '/admin/codeGenerate/table/' + tableName + '/columns',
    method: 'get'
  })
}

