
// 随机生成6位短信验证码
module.exports = function(size) {
  let code = ''
  for(let i = 0; i < size; i++) {
    code += Math.floor(Math.random()*10)
  }
  return code
}