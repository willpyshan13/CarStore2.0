import {get, post, put, deleteUrl, postUrl } from './index'

import base from './base'

const api = base + 'utility/'

export default {
    upload(params) {
        return postUrl(api + 'file/uploadFile?type=other', params)
    },
    uploadBase64Image(params) {
        return post(api + 'file/uploadBase64Image', params)
    }
}