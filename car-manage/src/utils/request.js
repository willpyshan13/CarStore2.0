import axios from 'axios'
import {MessageBox, Message} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'
import router from '@/router/index'

// create an axios instance
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
    // withCredentials: true, // send cookies when cross-domain requests
    // timeout: 5000 // request timeout
    timeout: 35000 // request timeout
})

// request interceptor
service.interceptors.request.use(
    config => {
        // do something before request is sent

        if (store.getters.token) {
            // let each request carry token
            // ['X-Token'] is a custom headers key
            // please modify it according to the actual situation
            config.headers['token'] = getToken()
        }
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)

// response interceptor
service.interceptors.response.use(
    /**
     * If you want to get http information such as headers or status
     * Please return  response => response
     */

    /**
     * Determine the request status by custom code
     * Here is just an example
     * You can also judge the status by HTTP Status Code
     */
    response => {
        const res = response.data;

        if(response.headers['content-type'].indexOf('application/vnd.ms-excel') > -1){
            return res
        }else{
            switch (res.code){
                case '0000':
                    console.log(router.currentRoute);
                    return res
                    break
                default:
                    Message({
                        message: res.msg || 'Error',
                        type: 'error',
                        duration: 5 * 1000
                    })
                    return Promise.reject(new Error(res.msg || 'Error'))
                    break
            }
        }
    },
    error => {
        if (error.response){
            switch (error.response.status) {
                case 401:
                    // 只有在当前路由不是登录页面才跳转
                    router.currentRoute.path !== 'login' &&
                    router.replace({
                        name: 'login',
                        query: { redirect: router.currentRoute.fullPath},
                    })
                    return Promise.reject(error)
                    break;
                default:
                    Message({
                        message: error.msg || '请求失败',
                        type: 'error',
                        duration: 5 * 1000
                    })
                    return Promise.reject(error)
                    break
            }
        }else{
            // Message({
            //     message: error.msg || '请求失败',
            //     type: 'error',
            //     duration: 5 * 1000
            // })
            return Promise.reject(error)
        }
    }
)

export default service
