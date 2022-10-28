// 处理token校验的中间件
const jwtUtils = require('../utils/JwtUtil')


const checkAuth = async (ctx, next) => {
  try{
    const res = jwtUtils.checkToken(ctx.header.token)
    if (res && res.id) {
      ctx.tourist_id = res.id
      await next()
    } else {
      ctx.throw(401, 'token失效')
    }
  } catch(error) {
    ctx.throw(401, 'token失效')
  }
}

module.exports = { checkAuth }
