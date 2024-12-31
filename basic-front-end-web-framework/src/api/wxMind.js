import request from '@/utils/request'

export function fetchList(params) {
    return request({
        url: '/wxMind/list/',
        method: 'get',
        params: params
    })
}

export function deleteById(data) {
    return request({
        url: '/wxMind/deleteById',
        method: 'delete',
        data: data
    })
}

export function saveOrUpdate(data) {
    return request({
        url: '/wxMind/saveOrUpdate',
        method: 'post',
        data: data
    })
}
