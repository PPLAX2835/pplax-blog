import service from '@/utils/request'
import {simpleRequest} from "../utils/request";

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

export function generateCode(tableName, params) {
  return simpleRequest({
    url: '/admin/codeGenerate/table/' + tableName + '/generate',
    method: 'get',
    params: params,
    responseType: 'blob', // 关键设置，确保Axios将响应数据处理为Blob
  })
}
