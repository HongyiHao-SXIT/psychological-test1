import request from '@/utils/request'

export function fetchList(params) {
    return request({
        url: '/wxBanner/list/',
        method: 'get',
        params: params
    })
}

export function deleteById(data) {
    return request({
        url: '/wxBanner/deleteById',
        method: 'delete',
        data: data
    })
}

export function saveOrUpdate(data) {
    return request({
        url: '/wxBanner/saveOrUpdate',
        method: 'post',
        data: data
    })
}
