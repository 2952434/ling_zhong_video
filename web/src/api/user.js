import request from "../utils/request"

/**
 * 用户相关接口
 */
// 获取邮箱验证码
export const getCode = (mail) => {
  const data = new URLSearchParams()
  data.append('mail', mail)
  return request.post('/user/sentRegisterAuthCode', data)
}
// 邮箱密码登录
export const loginByPassword = () => {
  return request.post('/login/password', {})
}
// 