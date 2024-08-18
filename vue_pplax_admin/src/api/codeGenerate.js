import service from '@/utils/request'


export function getTableList(params) {
  return service({
    url: '/admin/codeGenerate/table/list',
    method: 'get',
    params
  })
}
