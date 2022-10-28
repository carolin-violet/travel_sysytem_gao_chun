const Router = require('koa-router')
const router = new Router()

const delicacyModel = require('../model/delicacy')

// 获取美食列表
router.get('/findAll', async (ctx) => {
  const delicacies = await delicacyModel.findAll({
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
      delicacies
    }
  }
})

module.exports = router