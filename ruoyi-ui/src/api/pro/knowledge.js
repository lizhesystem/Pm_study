import request from '@/utils/request'

// 查询知识库列表
export function listKnowledge(query) {
  return request({
    url: '/pro/knowledge/list',
    method: 'get',
    params: query
  })
}

// 查询知识库详细
export function getKnowledge(knowledgeId) {
  return request({
    url: '/pro/knowledge/' + knowledgeId,
    method: 'get'
  })
}

// 新增知识库
export function addKnowledge(data) {
  return request({
    url: '/pro/knowledge',
    method: 'post',
    data: data
  })
}

// 修改知识库
export function updateKnowledge(data) {
  return request({
    url: '/pro/knowledge',
    method: 'put',
    data: data
  })
}

// 删除知识库
export function delKnowledge(knowledgeId) {
  return request({
    url: '/pro/knowledge/' + knowledgeId,
    method: 'delete'
  })
}

// 导出知识库
export function exportKnowledge(query) {
  return request({
    url: '/pro/knowledge/export',
    method: 'get',
    params: query
  })
}