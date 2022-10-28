
// 手机号校验
export const validPhone = (str) => {
  return /^1[0-9]{10}$/.test(str)
}

// 密码校验,包含数字大小写，可以有特殊字符
export const validPassword = (str) => {
  return /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,16}$/.test(str)
}
