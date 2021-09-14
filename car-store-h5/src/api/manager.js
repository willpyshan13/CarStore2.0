import {get, post, put, deleteUrl, postUrl } from './index'

import base from './base'

const api = base + 'manager/'

export default {
    getAreaList() {
        return get(api + 'area/queryList')
    },
    // 查询省一级地区
    getOneJiArea() {
        return get(api + 'area/queryList/-1')
    },
    getShopType(params) {
        return get(api + 'dict/queryListByType/' + params)
    },
    zidian(params) {
        return get(api + 'dict/queryListByType/' + params)
    },
    queryByCode (code) {
    	return get(api + `/dict/queryByCode/${code}`)
    }
}