import axios from 'axios';
import { Notify } from 'vant';
import router from '../router/index'

// 请求超时时间
axios.defaults.timeout = 60000;
// post请求头
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
// 请求拦截器
axios.interceptors.request.use(
    config => {
        // console.log(localStorage.getItem('token'), 'request-token')
        if (localStorage.getItem('token')) {
            const token = localStorage.getItem('token');
            config.headers.token = token;
        }
        // 每次发送请求之前判断是否存在token，如果存在，则统一在http请求的header都加上token，不用每次请求都手动添加了
        // 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断
        // if (store.state.token) {
        //     const token = store.state.token
        //     // const token = localStorage.getItem('token');
        //     config.headers.token = token;
        // }
        return config;
    },
    error => {
        return Promise.error(error);
    }
);
// 响应拦截器
axios.interceptors.response.use(
    response => {
        console.log(response)
        if (response.data.code === '0000') {
            return Promise.resolve(response);
        } else {
            Notify(response.data.msg)
            return Promise.resolve(response);
        }
    },
    error => {
        console.log(error, 'error')
        if (error && error.response) {
            if (error.response.status == '401') {
                window.js_android.anewLogin()
                Notify('token失效请重新登录')
            }
        }
    }
);
/**
 * get方法，对应get请求
 */
const get = (url, params) => {
    return new Promise((resolve, reject) => {
        axios.get(url, {
            params: params
        }).then(res => {
            resolve(res.data);
        }).catch(err => {
            reject(err)
        })
    });
}

/**
 * post方法，对应post请求
 */
const post = (url, params) => {
        return new Promise((resolve, reject) => {
            axios.post(url, params)
                .then(res => {
                    console.log(res, 'ressss')
                    resolve(res.data);
                })
                .catch(err => {
                    console.log(err, 'errsss')
                    reject(err)
                })
        });
    }
    /**
     * delete方法，对应delete请求
     */
const deleteUrl = (url, params) => {
        return new Promise((resolve, reject) => {
            axios.delete(url, {
                    params: params
                })
                .then(res => {
                    resolve(res.data);
                })
                .catch(err => {
                    reject(err)
                })
        });
    }
    /**
     * put方法，对应put请求
     */
const put = (url, params) => {
    return new Promise((resolve, reject) => {
        axios.put(url, params)
            .then(res => {
                resolve(res.data);
            })
            .catch(err => {
                reject(err)
            })
    });
}

const postUrl = (url, params) => {
    return new Promise((resolve, reject) => {
        axios.post(url, params, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(res => {
                resolve(res.data);
            })
            .catch(err => {
                reject(err)
            })
    });
}

export {
    get,
    post,
    put,
    deleteUrl,
    postUrl,
}