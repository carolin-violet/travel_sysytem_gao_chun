const Router = require('koa-router')
const router = new Router()


// 获取首屏数据信息
router.get('/info', async (ctx) => {
  console.log('qaq:');
  ctx.response.body = {
    code: 20000,
    message: '成功',
    success: true,
    data: {
      items: 'wa'
    }

  }
})

module.exports = router