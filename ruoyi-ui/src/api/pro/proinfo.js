import request from '@/utils/request'

// 查询项目列列表
export function listProinfo(query) {
  return request({
    url: '/pro/proinfo/list',
    method: 'get',
    params: query
  })
}

// 查询项目列详细
export function getProinfo(proId) {
  return request({
    url: '/pro/proinfo/' + proId,
    method: 'get'
  })
}

// 查询所有项目名称字典
export function getProdict(query) {
  return request({
    url: '/pro/proinfo/allList',
    method: 'get',
    params: query
  })
}


// 新增项目列
export function addProinfo(data) {
  return request({
    url: '/pro/proinfo',
    method: 'post',
    data: data
  })
}

// 修改项目列
export function updateProinfo(data) {
  return request({
    url: '/pro/proinfo',
    method: 'put',
    data: data
  })
}

// 删除项目列
export function delProinfo(proId) {
  return request({
    url: '/pro/proinfo/' + proId,
    method: 'delete'
  })
}

// 导出项目列
export function exportProinfo(query) {
  return request({
    url: '/pro/proinfo/export',
    method: 'get',
    params: query
  })
}
