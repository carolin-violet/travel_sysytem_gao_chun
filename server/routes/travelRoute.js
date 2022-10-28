const Router = require('koa-router')
const router = new Router()

const touristRouteModel = require('../model/tourist_route')
const routeSitesModel = require('../model/route_detail')


// 获取游玩路线列表
router.get('/findAll', async (ctx) => {
  const routes = await touristRouteModel.findAll({
    attributes: ['id', 'title', 'description', 'reason', 'is_deleted', 'create_time', 'modify_time'],
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
      routes
    }
  }
})

// 根据游玩路线获取价钱
router.get('/:route_id/price', async (ctx) => {
  const data = await touristRouteModel.findOne({
    attributes: ['id', 'title', 'price', 'discount_price', 'modify_time'],
    where: {
      id: ctx.request.params.route_id,
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

// 根据游玩线路获取站点详情
router.get('/:route_id/sites/findAll', async (ctx) => {
  const sites = await routeSitesModel.findAll({
    where: {
      is_deleted: 0,
      route_id: ctx.params.route_id
    },
    raw: true
  })
  ctx.response.body = {
    code: 20000,
    message: '查询成功',
    success: true,
    data: {
      sites
    }
  }
})
module.exports = router
