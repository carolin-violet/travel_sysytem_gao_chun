const Koa = require('koa')
const koaBody = require('koa-body')
const cors = require('koa2-cors')
const helmet = require('koa-helmet')
const Router = require('koa-router')
const fs = require('fs')
const consola = require('consola')

const app = new Koa()
const router = new Router()

const spendTimeMiddleWare = require('./middleware/spendTime')


const { Nuxt, Builder } = require('nuxt')
const config = require('../nuxt.config')
config.dev = app.env !== 'production'


function useMiddleware() {
  app.use(spendTimeMiddleWare)
  // 下面这个中间件导致前端vue失效，先暂时注销了
  // app.use(helmet())
  app.use(koaBody({
    multipart: true
  }))
  app.use(cors({
    origin: function(ctx) {
      return '*'
    },
    exposeHeaders: ['WWW-Authenticate', 'Server-Authorization'],
    maxAge: 86400,
    credentials: true,  // 允许携带头部验证信息
    allowMethods: ['GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'HEAD', 'OPTIONS'],
    allowHeaders: ['Content-Type', 'Authorization', 'Accept', 'X-Token', 'X-Device-Id', 'X-Uid'],
  }))
}

function useRouter(path){
  path = path || __dirname + '/routes'
  //注册路由
  let urls = fs.readdirSync(path)
  urls.forEach((element) => {
    const elementPath = path + '/' + element
    const stat = fs.lstatSync(elementPath);
    const isDir = stat.isDirectory();
    if (isDir) { // 文件夹递归注册路由
      useRouter(elementPath)
    } else {
      let module = require(elementPath)
      //routes里的文件名作为 路由名
      router.use('/travel_system/' + element.replace('.js', ''), module.routes())
    }
  })
  //使用路由
  app.use(router.routes()).use(router.allowedMethods())
}


async function start () {
  // Instantiate nuxt.js
  const nuxt = new Nuxt(config)

  const {
    host = process.env.HOST || '127.0.0.1',
    port = process.env.PORT || 7778
  } = nuxt.options.server

  await nuxt.ready()
  // Build in development
  if (config.dev) {
    const builder = new Builder(nuxt)
    await builder.build()
  }
  useMiddleware()
  useRouter()
  app.use((ctx) => {
    ctx.status = 200
    ctx.respond = false // Bypass Koa's built-in response handling
    ctx.req.ctx = ctx // This might be useful later on, e.g. in nuxtServerInit or with nuxt-stash
    nuxt.render(ctx.req, ctx.res)
  })
  app.listen(port, host)
  consola.ready({
    message: `Server listening on http://${host}:${port}`,
    badge: true
  })
}

start()
