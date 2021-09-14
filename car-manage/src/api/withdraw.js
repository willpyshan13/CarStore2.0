import request from '@/utils/request'

export function queryWithdrawalList(data={}) {
    return request({
        url: '/account/withdrawal/queryWithdrawalList',
        method: 'post',
        data
    })
}

export function queryWithdrawalDetail(uuid) {
    return request({
        url: '/account/withdrawal/queryWithdrawalDetail/'+uuid,
        method: 'get'
    })
}


export function checkWithdrawal(data={}) {
    return request({
        url: '/account/withdrawal/checkWithdrawal',
        method: 'put',
        data
    })
}


export function exportWithdrawalList(data={}) {
    return request({
        url: '/account/withdrawal/exportWithdrawalList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}


export function deleteWithdrawal(id) {
    return request({
        url: '/account/withdrawal/deleteWithdrawal/'+id,
        method: 'delete',
    })
}
