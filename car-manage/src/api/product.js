import request from '@/utils/request'

export function queryGoodsList(data={}) {
    return request({
        url: '/account/goods/queryGoodsList',
        method: 'post',
        data
    })
}

export function goodsParentQueryList(params={}) {
    return request({
        url: '/account/goodsParent/queryList',
        method: 'get',
        params
    })
}

export function exportGoodsList(data={}) {
    return request({
        url: '/account/goods/exportGoodsList',
        method: 'post',
        data,
        responseType: 'blob'
    })
}


export function queryGoodsDetail(uuid) {
    return request({
        url: '/account/goods/queryGoodsDetail/'+uuid,
        method: 'get'
    })
}

export function updateGoods(data={}) {
    return request({
        url: '/account/goods/updateGoods',
        method: 'put',
        data
    })
}


export function addGoods(data={}) {
    return request({
        url: '/account/goods/addGoods',
        method: 'post',
        data
    })
}


export function deleteGoods(id) {
    return request({
        url: '/account/goods/deleteGoods/'+id,
        method: 'delete',
    })
}
