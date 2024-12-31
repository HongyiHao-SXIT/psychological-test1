import request from '@/utils/request'
export function getEnum(params) {
  return request({
    url:'/enum/options',
    method:'get',
    params:params
  })
}
