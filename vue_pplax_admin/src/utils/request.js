import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import HttpStatus from '../base/HttpStatus'
import { getToken, removeToken, removeUserUid } from '@/utils/auth'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 10000 // 请求超时时间
})

// 创建简单axios实例
const simpleRequest = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 10000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    config.headers['Authorization'] = getToken()                                      // 让每个请求携带自定义token

    if (config.method === 'post' || config.method === 'put') {
      if (config.headers['Content-Type'] === undefined) {
        config.headers['Content-Type'] = 'application/json'                         // 给那些通过请求体传递参数的方法设置请求头
        config.data = JSON.stringify(config.data)
      }
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)
simpleRequest.interceptors.request.use(
  config => {
    config.headers['Authorization'] = getToken()                                      // 让每个请求携带自定义token
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {

    let res = response.data

    if (res.code === 401103 || res.code === 401104 || res.code === 401109) {
      // 认证有问题的情况
      MessageBox(res.code + ': ' + res.message + (res.data !== undefined ? ', ' + JSON.stringify(res.data): ''), '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        removeToken();
        removeUserUid();
        location.reload();
      })
      return Promise.reject(res.message)

    } else if (res.code !== HttpStatus.OK.code) {
      console.log(res)
      // code为非200是抛错
      Message({
        message: res.code + ': ' + res.message + (res.data !== undefined ? ', ' + JSON.stringify(res.data): ''),
        type: 'error',
        duration: 5 * 1000
      })

      return Promise.reject(res.message)
    } else {
      // 200 返回
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
export { simpleRequest }
