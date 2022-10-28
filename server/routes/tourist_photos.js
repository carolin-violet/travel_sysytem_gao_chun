const Router = require('koa-router')
const router = new Router()
const moment = require('moment')
const Sequelize = require('sequelize')
const Op = Sequelize.Op

const touristPhotosModel = require('../model/tourist_photos')
const GenId = require('../utils/GenId')
const aliOssUtil = require('../utils/aliOssUtil')
const { checkAuth } = require("../middleware/auth");

// 上传游记、反馈、头像的图片
router.post('/upload', checkAuth, async (ctx) => {
  const mark = ctx.request.body.mark
  const time = moment().format('YYYY-MM-DD')

  let url = await aliOssUtil.uploadPhoto(ctx.request.files.file, `travel_GaoChun/tourist_photos/${mark}/${ctx.tourist_id}/${time}`)
  if (url) {
    const genid = new GenId({ WorkerId: 1 });
    const is_insert = await touristPhotosModel.create({
      id: genid.NextId().toString(),
      url,
      mark,
      tourist_id: ctx.tourist_id,
      is_deleted: 0,
      create_time: new Date(),
      modify_time: new Date(),
    })
    if (is_insert) {
      ctx.body = url
    } else{
      ctx.body = {
        code: 20001,
        message: '图片保存失败',
        success: false
      }
    }
  } else {
    ctx.body = {
      code: 20001,
      message: '图片上传失败',
      success: false
    }
  }

})

// 查询游客上传的图片信息,根据mark查询,按天划分
router.get('/days', checkAuth, async (ctx) => {

  const photoList = await touristPhotosModel.findAll({
    where: {
      is_deleted: 0,
      tourist_id: ctx.tourist_id,
      mark: {
        [Op.like]: `%${ctx.request.query.mark}%`
      }
    },
    order: [
      ['create_time', 'DESC']
    ],
    raw: true
  })

  // 按天划分图片
  const photos = {}
  for(const photo of photoList) {
    const time = moment(photo.create_time).format('YYYY-MM-DD')
    if (!photos[time]) {
      photos[time] = []
    }
    photos[time].push(photo)
  }

  ctx.response.body = {
    code: 20000,
    message: '查询成功',
    success: true,
    data: {
      photos
    }
  }

})

// 根据mark分页查询游客上传的图片信息
router.get('/:current/:limit', checkAuth, async (ctx) => {

  const { rows, count } = await touristPhotosModel.findAndCountAll({
    where: {
      is_deleted: 0,
      tourist_id: ctx.tourist_id,
      mark: {
        [Op.like]: `%${ctx.request.query.mark}%`
      }
    },
    offset: (parseInt(ctx.params.current) - 1) * parseInt(ctx.params.limit),
    limit: parseInt(ctx.params.limit),
    order: [
      ['create_time', 'DESC']
    ],
    raw: true
  })

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

module.exports = router
