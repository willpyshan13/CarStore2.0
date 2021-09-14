import {get, post, put, deleteUrl, postUrl } from './index'

import base from './base'

const api = base + 'account/'

export default {
    getOneType(params) {
        return get(api + 'goodsParent/queryListByParent/' + params)
    },
    shangjiaSure(params) {
        return post(api + 'goods/addGoods', params)
    }
}