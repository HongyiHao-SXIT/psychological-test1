import request from '@/utils/request'

export function fetchList(params) {
    return request({
        url: '/wxMindItem/list/',
        method: 'get',
        params: params
    })
}

export function deleteById(data) {
    return request({
        url: '/wxMindItem/deleteById',
        method: 'delete',
        data: data
    })
}

export function saveOrUpdate(data) {
    return request({
        url: '/wxMindItem/saveOrUpdate',
        method: 'post',
        data: data
    })
}
