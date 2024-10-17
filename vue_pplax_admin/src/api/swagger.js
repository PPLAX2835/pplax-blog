import axios from "axios";

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

export function getSwaggerResources() {
  return axios({
    url: process.env.BASE_API + '/../swagger-resources',
    method: 'get'
  })
}
