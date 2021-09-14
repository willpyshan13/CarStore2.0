import request from '@/utils/request'

export function queryTechnicianList(data={}) {
    return request({
        url: '/account/technician/queryTechnicianList',
        method: 'post',
        data
    })
}


export function exportTechnicianList(data={}) {
    return request({
        url: '/account/technician/exportTechnicianList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function queryLastStoreHistory(uuid) {
    return request({
        url: '/account/storeHistory/queryLastStoreHistory/'+uuid,
        method: 'get'
    })
}


export function queryTechnicianDetail(uuid) {
    return request({
        url: '/account/technician/queryTechnicianDetail/'+uuid,
        method: 'get'
    })
}


export function queryTechnicianCount(data = {}) {
    return request({
        url: '/account/technician/queryTechnicianCount',
        method: 'post',
        data
    })
}


export function updateTechnician(data={}) {
    return request({
        url: '/account/technician/updateTechnician',
        method: 'put',
        data
    })
}



export function deleteTechnician(id) {
    return request({
        url: '/account/technician/deleteTechnician/'+id,
        method: 'delete',
    })
}

/**
 * 获取品牌列表
 * @param uuid
 * @returns {AxiosPromise}
 */
export function queryBrandList(uuid) {
    return request({
        url: '/account/vehicleConfig/queryList/0001',
        method: 'get'
    })
}
export function queryBrandListCx(uuid) {
    return request({
        url: '/account/vehicleConfig/queryList/'+uuid,
        method: 'get'
    })
}