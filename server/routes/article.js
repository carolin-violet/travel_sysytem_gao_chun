const Router = require('koa-router')
const router = new Router()

const articleModel = require('../model/article')

// 分页获取特定物品的评论
router.get('/:mark_id', async (ctx) => {
  const article = await articleModel.findOne({
    where: {
      mark_id: ctx.params.mark_id
    },
    raw: true
  })
  ctx.response.body = {
    code: 20000,
    message: '查询成功',
    success: true,
    data: {
      article
    }
  }
})

module.exports = router
