import request from '@/utils/request'
import {getToken} from "@/utils/auth";

// 故障码管理
export function dtcList(data = {}) {
    return request({
        url: '/order/dtc/list',
        method: 'post',
        data
    })
}


export function dtcDetail(uuid) {
    return request({
        url: '/order/dtc/getById/'+uuid,
        method: 'get'
    })
}

export function dtcAdd(data = {}) {
    return request({
        url: '/order/dtc/add',
        method: 'post',
        data
    })
}


export function dtcUpdate(data = {},uuid) {
    return request({
        url: '/order/dtc/updateById/'+uuid,
        method: 'put',
        data
    })
}


export function dtcDelete(uuid) {
    return request({
        url: '/order/dtc/deleteById/'+uuid,
        method: 'delete',
    })
}


export function batchImport(query,data = {}) {
    return request({
        url: '/order/dtc/batchImport'+query,
        method: 'post',
        data,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}


