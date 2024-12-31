import request from '@/utils/request'

export function fetchList(params) {
    return request({
        url: '/wxCounselor/list/',
        method: 'get',
        params: params
    })
}

export function deleteById(data) {
    return request({
        url: '/wxCounselor/deleteById',
        method: 'delete',
        data: data
    })
}

export function saveOrUpdate(data) {
    return request({
        url: '/wxCounselor/saveOrUpdate',
        method: 'post',
        data: data
    })
}
