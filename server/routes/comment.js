const Router = require('koa-router')
const router = new Router()
const moment = require('moment')

const commentModel = require('../model/comment')
const touristModel = require('../model/tourist')
const GenId = require('../utils/GenId')
const { getSentiment } = require('../utils/baiduApiUtil')
const { checkAuth } = require("../middleware/auth");

// 分页获取特定物品的评论,并获取对应游客的头像姓名信息
router.get('/findAll/:current/:limit', async (ctx) => {
  // 获取特定评论
  const { rows, count } = await commentModel.findAndCountAll({
    where: {
      is_deleted: 0,
      mark_id: ctx.request.query.mark_id
    },
    offset: (parseInt(ctx.params.current) - 1) * parseInt(ctx.params.limit),
    limit: parseInt(ctx.params.limit),
    order: [
      ['create_time', 'DESC']
    ],
    raw: true
  })

  const commentList = []
  for(const comment of rows) {
    // 将评论的创建时间转换为东八区时间
    comment.create_time = moment(comment.create_time).utcOffset(480).format('yyyy-MM-DD HH:mm:ss')

    // 获取评论相关游客信息
    const tourist = await touristModel.findOne({
      where: {
        id: comment.tourist_id
      },
      raw: true
    })
    comment.tourist_nickName = tourist.nickName
    comment.tourist_avatar = tourist.avatar
    commentList.push(comment)
  }

  ctx.response.body = {
    code: 20000,
    message: '查询成功',
    success: true,
    data: {
      count,
      commentList
    }
  }
})


// 添加评论
router.post('/', checkAuth, async (ctx) => {
  const comment = ctx.request.body

  const genid = new GenId({ WorkerId: 1 });
  comment.id = genid.NextId().toString()
  comment.is_deleted = 0
  comment.create_time = new Date()
  comment.modify_time = new Date()
  // 情感分析有时候会导致接口出错
  // const sentimentObj = await getSentiment(comment.comment)
  // comment.sentiment = sentimentObj.items[0].sentiment

  const res = await commentModel.create(comment)
  if (res) {
    ctx.response.body = {
      code: 20000,
      message: '评论成功',
      success: true
    }
  } else {
    ctx.response.body = {
      code: 20001,
      message: '评论失败',
      success: false
    }
  }

})

// 修改评论
router.patch('/:id', checkAuth, async (ctx) => {
  const sentimentObj = await getSentiment(ctx.body.comment)
  const sentiment = sentimentObj.items[0].sentiment

  const res = await commentModel.update({
    comment: ctx.body.comment,
    sentiment: sentiment,
    modify_time: new Date()
  }, {
    where: {
      id: ctx.params.id,
      tourist_id: res.id
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

// 删除评论
router.delete('/:id', checkAuth, async (ctx) => {
  const is_del = await commentModel.update({
    is_deleted: 1,
    modify_time: new Date()
  },{
    where: {
      id: ctx.params.id,
      tourist_id: ctx.tourist_id
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
