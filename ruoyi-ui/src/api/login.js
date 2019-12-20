import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  // 封装成json对象数据
  const data = {
    username,
    password,
    code,
    uuid
  }

  return request({
    url: '/login',
    method: 'post',
    // 使用拼接到地址后面&的方式提交数据
    params: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    method: 'get'
  })
}
