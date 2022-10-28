const Router = require('koa-router')
const router = new Router()

const bcrypt = require('bcryptjs')
const GenId = require('../utils/GenId')
const jwtUtils = require('../utils/JwtUtil')
const touristModel = require('../model/tourist')
const orderModel = require('../model/order_form')
const codeGenerator = require('../utils/codeUtil')
const { generateKeys, decrypt } = require('../utils/rsaUtil')
const { writeToRedis, readFromRedis } = require('../config/redisConfig')
const { sendSms } = require('../utils/tencentCloudUtil')
const { checkAuth } = require("../middleware/auth");
const { v4: uuidv4 } = require('uuid');

// 获取公钥并存入redis
router.get('/publicKey', async (ctx) => {
  // 获取客户端ip
  const ip = ctx.ip

  // 生成密钥对并存入redis
  const { publicKey, privateKey } = generateKeys()
  writeToRedis(`${ip}-publicKey:`, publicKey, 60)
  writeToRedis(`${ip}-privateKey:`, privateKey, 60)


  ctx.response.body = {
    code: 20000,
    message: '获取成功',
    success: true,
    data: {
      publicKey
    }
  }
})

// 获取手机验证码并存入redis
router.get('/code/:phone', async (ctx) => {
  const code = codeGenerator(6)
  try {
    const res = await sendSms(code, ctx.params.phone)
    writeToRedis(`${ctx.params.phone}-code`, code, 90)  // 多出30s，缓冲一下短信发送的延时
  } catch(err) {
    console.log(err);
  }
})

// 登录
router.post('/login', async (ctx) => {
  const { telephone, password } = ctx.request.body
  const tourist = await touristModel.findOne({
    where: { telephone: telephone },
    raw: true
  })
  if (tourist) {
    const ip = ctx.ip
    privateKey = await readFromRedis(`${ip}-privateKey:`)

    // 先对密码rsa解密
    const cPassword = decrypt(password, privateKey)
    // 校验密码
    const res = bcrypt.compareSync(cPassword, tourist.password)

    if (res) {
      // 先查询未支付订单数量
      const commodity_count = await orderModel.count({
        where: {
          is_deleted: 0,
          tourist_id: tourist.id,
          is_paid: 0
        },
        order: [
          ['create_time', 'DESC']
        ],
        raw: true
      })


      const token = jwtUtils.generateToken({ id: tourist.id }, '1 days')
      ctx.response.body = {
        code: 20000,
        message: '登录成功',
        success: true,
        data: {
          info: tourist,
          token,
          failure_time: new Date().getTime() + 1000*60*60*24,
          commodity_count
        }
      }
    } else {
      ctx.response.body = {
        code: 20001,
        message: '密码错误',
        success: false
      }
    }
  } else {
    ctx.response.body = {
      code: 20001,
      message: '游客不存在',
      success: false
    }
  }
})

// 游客注册
router.post('/register', async (ctx) => {
  const tourist = ctx.request.body

  const code = await readFromRedis(`${tourist.telephone}-code`)

  // 先判断验证码
  if (code === tourist.code) {
    // 判断手机号是否被注册
    const res = await touristModel.findOne({
      where: {
        telephone: tourist.telephone
      }
    })
    if (res) {
      ctx.response.body = {
        code: 20001,
        message: '该手机号已被注册',
        success: false
      }
    } else {
      const genid = new GenId({ WorkerId: 1 });

      const res = await touristModel.create({
        id: genid.NextId().toString(),
        nickName: tourist.nickName,
        telephone: tourist.telephone,
        password: bcrypt.hashSync(tourist.password),
        email: tourist.email,
        birthday: tourist.birthday,
        gender: tourist.gender,
        address: tourist.address,
        is_deleted: 0,
        create_time: new Date(),
        modify_time: new Date(),
      })
      if (res) {
        ctx.response.body = {
          code: 301,
          message: '注册成功,请前往登录',
          success: true
        }
      }
    }
  } else {
    ctx.response.body = {
      code: 20001,
      message: '验证码错误或失效',
      success: false
    }
  }
})


// 游客修改个人信息
router.put('/baseInfo', async (ctx) => {
  let tourist = ctx.request.body
  tourist.modify_time = new Date()
  const res = await touristModel.update(tourist, {
    where: {
      id: tourist.id
    }
  })
  if (res) {
    tourist = await touristModel.findOne({
      where: {
        id: tourist.id
      }
    })
    ctx.response.body = {
      code: 20000,
      message: '修改成功',
      success: true,
      data: {
        info: tourist
      }
    }
  } else {
    ctx.response.body = {
      code: 20001,
      message: '修改失败,请联系管理员',
      success: false
    }
  }
})


// 游客修改密码
router.patch('/password', checkAuth, async (ctx) => {

  const ip = ctx.ip
  privateKey = await readFromRedis(`${ip}-privateKey:`)
  // 先对密码rsa解密
  const cPassword = decrypt(ctx.request.body.password, privateKey)

  const updated = await touristModel.update({
    password: bcrypt.hashSync(cPassword),
    modify_time: new Date()
  }, {
    where: {
      id: ctx.tourist_id
    }
  })
  if (updated) {
    ctx.response.body = {
      code: 301,
      message: '密码修改成功,请重新登录',
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

// 游客修改头像
router.patch('/avatar', async (ctx) => {
  let tourist = ctx.request.body
  const res = await touristModel.update({
    avatar: tourist.avatar,
    modify_time: new Date()
    }, {
      where: {
        id: tourist.id
      }
    })
    if (res) {
      ctx.response.body = {
        code: 20000,
        message: '头像修改成功',
        success: true,
        avatar: tourist.avatar
      }
    } else {
      ctx.response.body = {
        code: 20001,
        message: '修改失败,请联系管理员',
        success: false
      }
    }
})

// 检查验证码,正确则返回一个校验字符串
router.post('/code/check', async (ctx) => {
  const { telephone, code } = ctx.request.body
  const init_code = await readFromRedis(`${telephone}-code`)
  if (code === init_code) {
    const checkStr = uuidv4().split('-').join("")
    writeToRedis(`${telephone}-checkStr`, checkStr, 300)  // 5分钟字符串重置密码凭证
    ctx.response.body = {
      code: 20000,
      message: '验证码正确',
      success: true,
      data: {
        checkStr,
        telephone
      }
    }
  } else {
    ctx.response.body = {
      code: 20001,
      message: '验证码错误',
      success: false
    }
  }
})

// 通过密码和校验字符串重置密码
router.patch('/password/reset', async (ctx) => {
  const { checkStr, telephone, password } = ctx.request.body
  init_str = await readFromRedis(`${telephone}-checkStr`)

  if (init_str === checkStr) {
    const ip = ctx.ip
    privateKey = await readFromRedis(`${ip}-privateKey:`)
    // 先对密码rsa解密
    const cPassword = decrypt(password, privateKey)

    const updated = await touristModel.update({
      password: bcrypt.hashSync(cPassword),
      modify_time: new Date()
    }, {
      where: {
        telephone: telephone
      }
    })
    if (updated) {
      ctx.response.body = {
        code: 301,
        message: '密码修改成功,请重新登录',
        success: true
      }
    } else {
      ctx.response.body = {
        code: 20001,
        message: '修改失败,请联系管理员',
        success: false
      }
    }
  } else {
    ctx.response.body = {
      code: 20001,
      message: '凭证错误，无法重置密码',
      success: false
    }
  }
})
module.exports = router
