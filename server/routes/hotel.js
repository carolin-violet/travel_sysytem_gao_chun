const Router = require('koa-router')
const router = new Router()

const hotelModel = require('../model/hotel')


// 获取旅馆列表
router.get('/findAll', async (ctx) => {
  const hotels = await hotelModel.findAll({
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
      hotels
    }
  }
})

module.exports = router