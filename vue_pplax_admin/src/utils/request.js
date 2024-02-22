import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '../store'
import HttpStatus from '../base/HttpStatus'
import { getToken, removeToken, removeUserUid } from '@/utils/auth'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 5000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    config.headers['Authorization'] = getToken()                                      // 让每个请求携带自定义token

    if (config.method === 'post' || config.method === 'put') {
      config.headers['Content-Type'] = 'application/json'                         // 给那些通过请求体传递参数的方法设置请求头
      config.data = JSON.stringify(config.data)
    }
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

    if (res.code === HttpStatus.UNAUTHORIZED.code || (res.code >= 401000 && res.code <= 401999)) {
      // 认证有问题的情况
      Message({
        message: res.code + ': ' + res.message + (res.data !== '' ? ', ' + res.data : ''),
        type: 'error',
        duration: 5 * 1000
      })

      setTimeout(function () {
        removeToken();
        removeUserUid();
        location.reload();
      }, 2000)
    } else if (res.code !== HttpStatus.OK.code) {
      // code为非200是抛错
      Message({
        message: res.code + ': ' + res.message + (res.data !== '' ? ', ' + res.data : ''),
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
