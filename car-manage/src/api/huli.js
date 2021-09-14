import request from '@/utils/request'

// 教育培训
// 分类管理
export function huliList(data = {}) {
    return request({
        url: '/order/maintain/list',
        method: 'post',
        data
    })
}


export function huliDetail(uuid) {
    return request({
        url: '/order/maintain/getById/'+uuid,
        method: 'get'
    })
}

export function huliAdd(data = {}) {
    return request({
        url: '/order/maintain/add',
        method: 'post',
        data
    })
}


export function huliUpdate(data = {},uuid) {
    return request({
        url: '/order/maintain/updateById/'+uuid,
        method: 'put',
        data
    })
}


export function huliDelete(uuid) {
    return request({
        url: '/order/maintain/deleteById/'+uuid,
        method: 'delete',
    })
}


