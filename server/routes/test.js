const Router = require('koa-router')
const router = new Router()

const touristModel = require('../model/tourist')
const aliOssUtil = require('../utils/aliOssUtil')
const jwtUtils = require('../utils/JwtUtil')
const fs = require('fs')

const { generateKeys, encrypt, decrypt } = require('../utils/rsaUtil')

router.get('/hello', async(ctx) => {
  ctx.response.body = ctx.request.url
})

router.get('/rsa', async (ctx) => {
  const { publicKey, privateKey } = generateKeys()
  const cipher = encrypt('$2a$10$r6sqHeBHP52A/9wQsEkms.l3UdWxtdlk4VjfzF355JO2.AXFEOnpy', publicKey)
  console.log('密文:', cipher);
  const plain = decrypt(cipher, privateKey)
  console.log('明文:', plain);
})

router.get('/token', async (ctx) => {
  const token = jwtUtils.generateToken({ id: '1534064312866316290' }, '1 days')
  ctx.body = token
})


router.delete('/deletePhoto', async (ctx) => {
  let res = await aliOssUtil.deletePhoto('travel_GaoChun/dog.jpg')
  if (res.res.statusCode === 204) {
    ctx.body = 'success'
  }
})

router.delete('/deletePhotos', async (ctx) => {
  let res = await aliOssUtil.deletePhotos(['travel_GaoChun/favicon.png', 'travel_GaoChun/0bfe4155db634987a3d79cdb1a36073f.webp'])
  if (res.res.statusCode === 200) {
    ctx.body = 'success'
  }
})

module.exports = router
