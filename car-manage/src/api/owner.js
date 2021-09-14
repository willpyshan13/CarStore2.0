import request from '@/utils/request'

export function queryList(data={}) {
    return request({
        url: '/account/vehicleUser/queryList',
        method: 'post',
        data
    })
}


export function exportVehicleList(data={}) {
    return request({
        url: '/account/vehicleUser/exportVehicleList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function queryOrderAmount(uuid) {
    return request({
        url: '/account/vehicleUser/queryOrderAmount/'+uuid,
        method: 'get'
    })
}


export function queryDetail(uuid) {
    return request({
        url: '/account/vehicleUser/queryDetail/'+uuid,
        method: 'get'
    })
}


export function queryVehicleUserCount() {
    return request({
        url: '/account/vehicleUser/queryVehicleUserCount',
        method: 'get'
    })
}


export function editVehicleUser(data={}) {
    return request({
        url: '/account/vehicleUser/edit',
        method: 'put',
        data
    })
}


export function queryAllList(params={}) {
    return request({
        url: '/account/vehicleConfig/queryAllList',
        method: 'get',
        params
    })
}

