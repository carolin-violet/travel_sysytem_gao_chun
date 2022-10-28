const Router = require('koa-router')
const router = new Router()
const moment = require('moment')

const noticeModel = require('../model/notice')


// 获取资讯列表
router.get('/findAll/:current/:limit', async (ctx) => {
  const { rows, count } = await noticeModel.findAndCountAll({
    where: {
      is_deleted: 0
    },
    offset: (parseInt(ctx.params.current) - 1) * parseInt(ctx.params.limit),
    limit: parseInt(ctx.params.limit),
    order: [
      ['create_time', 'DESC']
    ],
    raw: true
  })
  const noticeList = rows.map(notice => {
    notice.modify_time = moment(notice.modify_time).utcOffset(480).format('yyyy-MM-DD HH:mm:ss')
    return notice
  })
  ctx.response.body = {
    code: 20000,
    message: '查询成功',
    success: true,
    data: {
      noticeList,
      count
    }
  }
})

module.exports = router
