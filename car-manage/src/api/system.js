import request from '@/utils/request'

// 用户管理

export function getUserList(data = {}) {
    return request({
        url: '/manager/user/queryUserList',
        method: 'post',
        data
    })
}

export function addUser(data = {}) {
    return request({
        url: '/manager/user/addUser',
        method: 'post',
        data
    })
}

export function updateUser(data = {}) {
    return request({
        url: '/manager/user/updateUser',
        method: 'put',
        data
    })
}

export function deleteUser(id) {
    return request({
        url: '/manager/user/deleteUser/'+id,
        method: 'delete',
    })
}


export function updateUserStatus(data = {}) {
    return request({
        url: '/manager/user/updateUserStatus',
        method: 'put',
        data
    })
}


// 角色管理

export function getRoleList(data = {}) {
    return request({
        url: '/manager/role/queryRoleList',
        method: 'post',
        data
    })
}

export function addRole(data = {}) {
    return request({
        url: '/manager/role/addRole',
        method: 'post',
        data
    })
}


export function updateRole(data = {}) {
    return request({
        url: '/manager/role/updateRole',
        method: 'put',
        data
    })
}


export function deleteRole(id) {
    return request({
        url: '/manager/role/deleteRole/'+id,
        method: 'delete',
    })
}

export function queryRoleDetail(id) {
    return request({
        url: '/manager/role/queryRoleDetail/'+id,
        method: 'get',
    })
}


// 系统菜单

export function queryAllMenuList(params = {}) {
    return request({
        url: '/manager/menu/queryAllMenuList',
        method: 'get',
        params
    })
}

//日志

export function getLogList(data = {}) {
    return request({
        url: '/manager/log/queryList',
        method: 'post',
        data
    })
}
