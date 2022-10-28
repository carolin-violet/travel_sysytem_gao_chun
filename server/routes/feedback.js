const Router = require('koa-router')
const router = new Router()

const feedbackModel = require('../model/feedback')
const touristPhotoModel = require('../model/tourist_photos')
const touristModel = require('../model/tourist')
const GenId = require('../utils/GenId')
const { deletePhotos } = require('../utils/aliOssUtil')
const { checkAuth } = require("../middleware/auth");


// 分页获取反馈列表
router.get('/:current/:limit', async (ctx) => {
  const { rows, count } = await feedbackModel.findAndCountAll({
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
  // 反馈图片
  for(const feedback of rows){
    const photos = await touristPhotoModel.findAll({
      where: {
        mark_id: feedback.id,
        is_deleted: 0
      },
      raw: true
    })
    feedback.photos = photos
  }
  // 反馈用户信息
  for(const feedback of rows) {
    const tourist = await touristModel.findOne({
      where: {
        id: feedback.tourist_id
      },
      raw: true
    })
    feedback.tourist_nickName = tourist.nickName
    feedback.tourist_avatar = tourist.avatar
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

// 查看自己发布的反馈, 根据token查出id然后获取
router.get('/self/:current/:limit', checkAuth, async (ctx) => {
  const { rows, count } = await feedbackModel.findAndCountAll({
    where: {
      tourist_id: ctx.tourist_id,
      is_deleted: 0
    },
    offset: (parseInt(ctx.params.current) - 1) * parseInt(ctx.params.limit),
    limit: parseInt(ctx.params.limit),
    order: [
      ['create_time', 'DESC']
    ],
    raw: true
  })
  for(const feedback of rows){
    const photos = await touristPhotoModel.findAll({
      where: {
        mark_id: feedback.id,
        is_deleted: 0
      },
      raw: true
    })
    feedback.photos = photos
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


// 删除自己发的反馈
router.delete('/:id', checkAuth, async (ctx) => {
  // 根据反馈查询图片
  const feedback = await feedbackModel.findOne({
    where: {
      id: ctx.request.params.id,
      tourist_id: ctx.tourist_id,
      is_deleted: 0
    }
  })
  const photos = await touristPhotoModel.findAll({
    where: {
      mark_id: feedback.id,
      is_deleted: 0
    },
    raw: true
  })

  // 删除oss照片
  const urlList = photos.reduce((pre, next)=>{
    pre.push(next.url.replace("https://edu-19527.oss-cn-nanjing.aliyuncs.com/", ""))
  }, [])
  deletePhotos(urlList)

  // 删除表中照片
  touristPhotoModel.update({
    is_deleted: 1,
    modify_time: new Date()
  }, {
    where: {
      mark_id: feedback.id
    }
  })

  // 删除表中反馈
  const is_del = await feedbackModel.update({
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
      code: 20000,
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

// 编辑自己发布的游记的反馈
router.patch('/:id', checkAuth, async (ctx) => {

  const updated = await feedbackModel.update({
    content: ctx.request.body.content,
    modify_time: new Date()
  }, {
    where: {
      id: ctx.params.id,
      tourist_id: ctx.tourist_id
    }
  })
  if(updated) {
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

// 发布反馈
router.post('/', checkAuth, async (ctx) => {
  const feedback = ctx.request.body

  const genid = new GenId({ WorkerId: 1 }).NextId().toString();

  // 根据图片id为图片更改相应的mark_id
  if (feedback.photoUrlList.length) {
    feedback.photoUrlList.map((id) => {
      touristPhotoModel.update({
        mark_id: genid,
        modify_time: new Date()
      }, {
        where: {
          id
        }
      })
    })
  }

  const res = await feedbackModel.create({
    id: genid,
    tourist_id: feedback.tourist_id,
    content: feedback.content,
    is_deleted: 0,
    create_time: new Date(),
    modify_time: new Date()
  })
  if (res) {
    ctx.response.body = {
      code: 20000,
      message: '发布成功',
      success: true
    }
  }
})

module.exports = router
