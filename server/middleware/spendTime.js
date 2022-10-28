// 计算服务器消耗时长的中间件

module.exports = async(ctx, next) => {
  const start = new Date()
  await next()
  const end = new Date()

  const duration = end - start
  ctx.set('Response-Time', duration + 'ms')
}