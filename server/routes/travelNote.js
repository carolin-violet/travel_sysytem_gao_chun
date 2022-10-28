const Router = require('koa-router')
const router = new Router()

const travelNoteModel = require('../model/travel_note')
const touristPhotoModel = require('../model/tourist_photos')
const touristModel = require('../model/tourist')
const { checkAuth } = require("../middleware/auth");
const GenId = require('../utils/GenId')
const { deletePhotos } = require('../utils/aliOssUtil')


// 分页获取游记列表
router.get('/:current/:limit', async (ctx) => {
  const { rows, count } = await travelNoteModel.findAndCountAll({
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
  // 查图片
  for(const travelNote of rows){
    const photos = await touristPhotoModel.findAll({
      where: {
        mark_id: travelNote.id,
        is_deleted: 0
      },
      raw: true
    })
    travelNote.photos = photos
  }
  // 反馈用户信息
  for(const travelNote of rows) {
    const tourist = await touristModel.findOne({
      where: {
        id: travelNote.tourist_id
      },
      raw: true
    })
    travelNote.tourist_nickName = tourist.nickName
    travelNote.tourist_avatar = tourist.avatar
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

// 查看自己发布的游记, 根据token查出id然后获取
router.get('/self/:current/:limit', checkAuth, async (ctx) => {

  const { rows, count } = await travelNoteModel.findAndCountAll({
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
  for(const travelNote of rows){
    const photos = await touristPhotoModel.findAll({
      where: {
        mark_id: travelNote.id,
        is_deleted: 0
      },
      raw: true
    })
    travelNote.photos = photos
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


// 删除自己发的游记
router.delete('/:id', checkAuth, async (ctx) => {

  // 根据游记查询图片
  const travelNote = await travelNoteModel.findOne({
    where: {
      id: ctx.request.params.id,
      tourist_id: ctx.tourist_id,
      is_deleted: 0
    }
  })
  const photos = await touristPhotoModel.findAll({
    where: {
      mark_id: travelNote.id,
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
      mark_id: travelNote.id
    }
  })

  // 删除表中游记
  const is_del = await travelNoteModel.update({
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

// 编辑自己发布的游记的内容(一般不能跟换图片信息)
router.patch('/:id', checkAuth, async (ctx) => {

  const updated = await travelNoteModel.update({
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

// 发布游记
router.post('/', checkAuth, async (ctx) => {
  const travel_note = ctx.request.body

  const genid = new GenId({ WorkerId: 1 }).NextId().toString();

  // 根据图片id为图片更改相应的mark_id
  if (travel_note.photoUrlList.length) {
    travel_note.photoUrlList.map((id) => {
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

  const res = await travelNoteModel.create({
    id: genid,
    tourist_id: travel_note.tourist_id,
    content: travel_note.content,
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
