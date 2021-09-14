import request from '@/utils/request'

export function queryDictList(type) {
    return request({
        url: '/manager/dict/queryListByType/'+type,
        method: 'get'
    })
}
