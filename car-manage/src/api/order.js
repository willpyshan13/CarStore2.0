import request from '@/utils/request'

// 商品订单
export function queryOrderGoodsList(data={}) {
    return request({
        url: '/order/orderGoods/queryOrderGoodsList',
        method: 'post',
        data
    })
}

export function queryOrderGoodsDetail(uuid) {
    return request({
        url: '/order/orderGoods/queryOrderGoodsDetail/'+uuid,
        method: 'get'
    })
}

export function exportOrderGoodsList(data={}) {
    return request({
        url: '/order/orderGoods/exportOrderGoodsList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}


// 代驾订单
export function queryOrderDrivingList(data={}) {
    return request({
        url: '/order/orderDriving/queryOrderDrivingList',
        method: 'post',
        data
    })
}

export function queryOrderDrivingDetail(uuid) {
    return request({
        url: '/order/orderDriving/queryOrderDrivingDetail/'+uuid,
        method: 'get'
    })
}

export function exportOrderDrivingList(data={}) {
    return request({
        url: '/order/orderDriving/exportOrderDrivingList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}


// 案例订单
export function queryOrderCaseList(data={}) {
    return request({
        url: '/order/orderCase/queryOrderCaseList',
        method: 'post',
        data
    })
}

export function queryOrderCaseDetail(uuid) {
    return request({
        url: '/order/orderCase/queryOrderCaseDetail/'+uuid,
        method: 'get'
    })
}

export function exportOrderCaseList(data={}) {
    return request({
        url: '/order/orderCase/exportOrderCaseList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

// 问答订单
export function queryOrderConsultList(data={}) {
    return request({
        url: '/order/orderConsult/queryOrderConsultList',
        method: 'post',
        data
    })
}

export function queryOrderConsultDetail(uuid) {
    return request({
        url: '/order/orderConsult/queryOrderConsultDetail/'+uuid,
        method: 'get'
    })
}

export function exportOrderConsultList(data={}) {
    return request({
        url: '/order/orderConsult/exportOrderConsultList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}



// dtc订单
export function dtcOrderList(data={}) {
    return request({
        url: '/order/dtcOrder/list',
        method: 'post',
        data
    })
}

export function dtcOrderDetail(uuid) {
    return request({
        url: '/order/dtcOrder/getById/'+uuid,
        method: 'get'
    })
}

export function exportOrderDtcList(data={}) {
    return request({
        url: '/order/dtcOrder/exportOrderDtcList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}



// 现场订单
export function sceneOrderList(data={}) {
    return request({
        url: '/order/sceneOrder/querySceneOrderList',
        method: 'post',
        data
    })
}


export function sceneOrderDetail(uuid) {
    return request({
        url: '/order/sceneOrder/querySceneOrderInfo/'+uuid,
        method: 'get'
    })
}


export function sceneOrderComplete(uuid) {
    return request({
        url: '/order/sceneOrder/completeOrder/'+uuid,
        method: 'get'
    })
}



// 课程订单
export function courseOrderList(data={}) {
    return request({
        url: '/order/courseOrder/list',
        method: 'post',
        data
    })
}


export function courseOrderDetail(uuid) {
    return request({
        url: '/order/courseOrder/getById/'+uuid,
        method: 'get'
    })
}


export function exportOrderCourseList(data={}) {
    return request({
        url: '/order/courseOrder/exportCourseOrderList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}




// 技师预约订单
export function makeOrderList(data={}) {
    return request({
        url: '/order/shareTechnicianOrder/queryShareTechnicianOrderList',
        method: 'post',
        data
    })
}

export function makeOrderDetail(uuid) {
    return request({
        url: '/order/shareTechnicianOrder/queryShareTechnicianOrder/'+uuid,
        method: 'get'
    })
}
