import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '../store'
import ResponseCode from '../base/ResponseCode'
import { getToken } from '@/utils/auth'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 5000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      console.log(getToken())
      config.headers['Authorization'] = getToken()                                      // 让每个请求携带自定义token
    } 

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
    /**
     * code为非200是抛错
     */
    if (res.code != ResponseCode.SUCCESS.code) {
      Message({
        message: res.code + ': ' + res.message + (res.data !== '' ? ', ' + res.data : ''),
        type: 'error',
        duration: 5 * 1000
      })

      // // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
      // if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
      //   MessageBox.confirm(
      //     '你已被登出，可以取消继续留在该页面，或者重新登录',
      //     '确定登出',
      //     {
      //       confirmButtonText: '重新登录',
      //       cancelButtonText: '取消',
      //       type: 'warning'
      //     }
      //   ).then(() => {
      //     store.dispatch('FedLogOut').then(() => {
      //       location.reload() // 为了重新实例化vue-router对象 避免bug
      //     })
      //   })
      // }
      return Promise.reject(res.message)
    } else {
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
