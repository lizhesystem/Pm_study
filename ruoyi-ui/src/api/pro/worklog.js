import request from '@/utils/request'

// 查询日报列表
export function listWorklog(query) {
  return request({
    url: '/pro/worklog/list',
    method: 'get',
    params: query
  })
}

// 查询日报详细
export function getWorklog(worklogId) {
  return request({
    url: '/pro/worklog/' + worklogId,
    method: 'get'
  })
}

// 新增日报
export function addWorklog(data) {
  return request({
    url: '/pro/worklog',
    method: 'post',
    data: data
  })
}

// 修改日报
export function updateWorklog(data) {
  return request({
    url: '/pro/worklog',
    method: 'put',
    data: data
  })
}

// 删除日报
export function delWorklog(worklogId) {
  return request({
    url: '/pro/worklog/' + worklogId,
    method: 'delete'
  })
}

// 导出日报
export function exportWorklog(query) {
  return request({
    url: '/pro/worklog/export',
    method: 'get',
    params: query
  })
}