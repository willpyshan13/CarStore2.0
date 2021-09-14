import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/manager/oauth/userLogin',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/manager/oauth/getTokenUser',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/manager/oauth/exitLogin',
    method: 'get'
  })
}

export function updateUserPwd(data = {}){
    return request({
        url: '/manager/user/updateUserPwd',
        method: 'put',
        data
    })
}
