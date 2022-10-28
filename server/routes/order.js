const Router = require('koa-router')
const router = new Router()

const orderModel = require('../model/order_form')
const touristRouteModel = require('../model/tourist_route')
const scenicSpotModel = require('../model/scenic_spot')
const GenId = require('../utils/GenId')
const { checkAuth } = require("../middleware/auth");

// 分页获取历史订单
router.get('/:current/:limit', checkAuth, async (ctx) => {
  const { rows, count } = await orderModel.findAndCountAll({
    where: {
      is_deleted: 0,
      tourist_id: ctx.tourist_id
    },
    offset: (parseInt(ctx.params.current) - 1) * parseInt(ctx.params.limit),
    limit: parseInt(ctx.params.limit),
    order: [
      ['create_time', 'DESC']
    ],
    raw: true
  })
  for(const order of rows){
    if (order.mark === 'route') {
      const item = await touristRouteModel.findOne({
        where: {
          id: order.commodity_id,
          is_deleted: 0
        },
        raw: true
      })
      order.title = item.title
    } else if (order.mark === 'scenic') {
      const item = await scenicSpotModel.findOne({
        where: {
          id: order.commodity_id,
          is_deleted: 0
        },
        raw: true
      })
      order.title = item.name
    }
  }
  ctx.response.body = {
    code: 20000,
    message: '查询成功',
    success: true,
    data: {
      rows,
      count
    }
  }
})


// 获取未支付的订单(购物车)
router.get('/not_paid', checkAuth, async (ctx) => {
  const orders = await orderModel.findAll({
    where: {
      is_deleted: 0,
      tourist_id: ctx.tourist_id,
      is_paid: 0
    },
    order: [
      ['create_time', 'DESC']
    ],
    raw: true
  })
  for(const order of orders){
    if (order.mark === 'route') {
      const item = await touristRouteModel.findOne({
        where: {
          id: order.commodity_id,
          is_deleted: 0
        },
        raw: true
      })
      order.title = item.title
    } else if (order.mark === 'scenic') {
      const item = await scenicSpotModel.findOne({
        where: {
          id: order.commodity_id,
          is_deleted: 0
        },
        raw: true
      })
      order.title = item.name
    }
  }
  ctx.response.body = {
    code: 20000,
    message: '查询成功',
    success: true,
    data: {
      orders
    }
  }

})


// 提交订单(加入购物车)
router.post('/', checkAuth, async (ctx) => {
  const order = ctx.request.body

  const genid = new GenId({ WorkerId: 1 });
  order.id = genid.NextId().toString()
  order.is_deleted = 0
  order.create_time = new Date()
  order.modify_time = new Date()

  const res = await orderModel.create(order)

  if (res) {
      // 先查询未支付订单数量
    const commodity_count = await orderModel.count({
      where: {
        is_deleted: 0,
        tourist_id: ctx.tourist_id,
        is_paid: 0
      },
      order: [
        ['create_time', 'DESC']
      ],
      raw: true
    })
    ctx.response.body = {
      code: 20000,
      message: '发布成功',
      success: true,
      data: {
        commodity_count
      }
    }
  }
})

// 修改订单状态为已支付(完成支付)
router.patch('/', checkAuth, async (ctx) => {
  const order = ctx.request.body

  const res = await orderModel.update({
    is_paid: 1,
    modify_time: new Date()
  }, {
    where: {
      id: order.id
    }
  })
  if(res) {
    ctx.response.body = {
      code: 20000,
      message: '修改成功',
      success: true
    }
  } else {
    ctx.response.body = {
      code: 20001,
      message: '修改失败,请联系管理员',
      success: false
    }
  }
})

// 删除订单
router.delete('/:id', checkAuth, async (ctx) => {

  const is_del = await orderModel.update({
    is_deleted: 1,
    modify_time: new Date()
  },{
    where: {
      id: ctx.params.id,
      tourist_id: ctx.tourist_id,
    }
  })
  if (is_del) {
    ctx.response.body = {
      code: 204,
      message: '删除成功',
      success: true,
    }
  } else {
    ctx.response.body = {
      code: 20001,
      message: '删除失败,请联系管理员',
      success: false,
    }
  }
})
module.exports = router
