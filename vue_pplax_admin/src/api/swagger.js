import axios from "axios";
import service from "../utils/request";

/**
 * 获得指定服务api文档
 * @param resource
 * @returns {*}
 */
export function getV2ApiDocs(resource) {
  return axios({
    url: process.env.BASE_HOST + resource.url,
    method: 'get'
  })
}

/**
 * 获得指定捷resource的阿皮文档
 * @returns {AxiosPromise}
 */
export function getSwaggerResources() {
  return axios({
    url: process.env.BASE_API + '/../swagger-resources',
    method: 'get'
  })
}

/**
 * api测试
 * @returns {AxiosPromise}
 */
export function apiTest(params) {
  params.url = process.env.BASE_API + params.url
  return service(params)
}
