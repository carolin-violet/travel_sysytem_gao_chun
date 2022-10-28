const Router = require('koa-router')
const router = new Router()

const scenicSpotModel = require('../model/scenic_spot')


// 获取景点列表
router.get('/findAll', async (ctx) => {
  const scenicSpots = await scenicSpotModel.findAll({
    attributes: ['id', 'name', 'description', 'address', 'cover', 'recommend_rank', 'modify_time'],
    where: {
      is_deleted: 0
    },
    raw: true
  })
  ctx.response.body = {
    code: 20000,
    message: '查询成功',
    success: true,
    data: {
      scenicSpots
    }
  }
})

// 根据景点获取价钱
router.get('/:scenic_id/price', async (ctx) => {
  const data = await scenicSpotModel.findOne({
    attributes: ['id', 'name', 'price', 'discount_price', 'modify_time'],
    where: {
      id: ctx.request.params.scenic_id,
      is_deleted: 0
    },
    raw: true
  })
  ctx.response.body = {
    code: 20000,
    message: '查询成功',
    success: true,
    data
  }
})

module.exports = router
