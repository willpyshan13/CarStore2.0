import request from '@/utils/request'

export function queryContentList(data={}) {
    return request({
        url: '/order/store/queryContentList',
        method: 'post',
        data
    })
}

export function checkContent(data={}) {
    return request({
        url: '/order/store/checkContent',
        method: 'put',
        data
    })
}


export function deleteContent(id) {
    return request({
        url: '/order/store/deleteContent/'+id,
        method: 'delete',
    })
}
