const Router = require('koa-router')
const router = new Router()

const coordinateModel = require('../model/coordinate')


// 获取旅馆列表
router.get('/:mark_id', async (ctx) => {
  const coordinate = await coordinateModel.findOne({
    where: {
      mark_id: ctx.request.params.mark_id,
      is_deleted: 0
    },
    raw: true
  })
  ctx.response.body = {
    code: 20000,
    message: '查询成功',
    success: true,
    data: {
      coordinate
    }
  }
})

module.exports = router
