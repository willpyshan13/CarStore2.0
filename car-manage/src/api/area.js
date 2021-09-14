import request from '@/utils/request'

export function queryAreaList(type) {
    return request({
        url: '/manager/area/queryList/'+type,
        method: 'get'
    })
}

