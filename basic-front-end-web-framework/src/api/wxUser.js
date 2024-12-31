import request from '@/utils/request'

export function fetchList(params) {
    return request({
        url: '/wxUser/list/',
        method: 'get',
        params: params
    })
}

export function deleteById(data) {
    return request({
        url: '/wxUser/deleteById',
        method: 'delete',
        data: data
    })
}

export function saveOrUpdate(data) {
    return request({
        url: '/wxUser/saveOrUpdate',
        method: 'post',
        data: data
    })
}
