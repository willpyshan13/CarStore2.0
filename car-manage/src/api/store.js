import request from '@/utils/request'

export function queryDictList(data={}) {
    return request({
        url: '/account/store/queryStoreList',
        method: 'post',
        data
    })
}

export function queryStoreDetail(uuid) {
    return request({
        url: '/account/store/queryStoreDetail/'+uuid,
        method: 'get'
    })
}


export function updateStore(data={}) {
    return request({
        url: '/account/store/updateStore',
        method: 'put',
        data
    })
}


export function exportStoreList(data={}) {
    return request({
        url: '/account/store/exportStoreList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}


export function deleteStore(id) {
    return request({
        url: '/account/store/deleteStore/'+id,
        method: 'delete',
    })
}
