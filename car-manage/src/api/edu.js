import request from '@/utils/request'

// 教育培训
// 分类管理
export function courseParentList(data = {}) {
    return request({
        url: '/order/courseParent/list',
        method: 'post',
        data
    })
}


export function courseParentDetail(uuid) {
    return request({
        url: '/order/courseParent/getById/'+uuid,
        method: 'get'
    })
}

export function courseParentAdd(data = {}) {
    return request({
        url: '/order/courseParent/add',
        method: 'post',
        data
    })
}


export function courseParentUpdate(data = {},uuid) {
    return request({
        url: '/order/courseParent/updateById/'+uuid,
        method: 'put',
        data
    })
}


export function courseParentDelete(uuid) {
    return request({
        url: '/order/courseParent/deleteById/'+uuid,
        method: 'delete',
    })
}


// 课程管理
export function courseList(data = {}) {
    return request({
        url: '/order/course/list',
        method: 'post',
        data
    })
}


export function coursetDetail(uuid) {
    return request({
        url: '/order/course/getById/'+uuid,
        method: 'get'
    })
}

export function courseAdd(data = {}) {
    return request({
        url: '/order/course/add',
        method: 'post',
        data
    })
}


export function courseUpdate(data = {},uuid) {
    return request({
        url: '/order/course/updateById/'+uuid,
        method: 'put',
        data
    })
}


export function courseDelete(uuid) {
    return request({
        url: '/order/course/deleteById/'+uuid,
        method: 'delete',
    })
}
