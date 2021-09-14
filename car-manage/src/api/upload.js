import request from '@/utils/request'
import { getToken } from '@/utils/auth'
export function uploadPic(file,type = 'other') {
    const data = new FormData();
    data.append('file', file);
    return request({
        url: '/utility/file/uploadFile?type='+type,
        method: 'post',
        data,
        headers: {
            'Content-Type': 'multipart/form-data',
            'token':getToken()
        }
    })
}
