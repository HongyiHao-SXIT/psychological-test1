import request from '@/utils/request'

export function fetchList(params) {
    return request({
        url: '/wxArticle/list/',
        method: 'get',
        params: params
    })
}

export function deleteById(data) {
    return request({
        url: '/wxArticle/deleteById',
        method: 'delete',
        data: data
    })
}

export function saveOrUpdate(data) {
    return request({
        url: '/wxArticle/saveOrUpdate',
        method: 'post',
        data: data
    })
}
