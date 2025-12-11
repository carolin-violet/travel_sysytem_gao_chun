# 高淳旅游服务管理系统

高淳旅游一体化解决方案，包含后台管理、游客前台与前台后端。当前仓库为管理端 Vue 项目，已停止维护，仅供学习参考。

## 仓库与分支

- 管理端（本分支）：基于 Vue2 + ElementUI 的后台管理 UI
- 游客前台：Nuxt/Tailwind 前端（独立分支）
- 前台后端：Koa2 服务（独立分支）

## 核心功能（管理端）

- 管理员与权限：管理员、角色、菜单权限
- 业务数据：游客、景点、线路、美食、旅馆、游记、评论、反馈、订单等管理
- 内容运营：公告、照片、文章/游记富文本编辑
- 日志与统计：操作日志、看板图表、评论情感分析

## 技术栈概览

- 后端（独立服务）：Spring Boot、MyBatis-Plus、Spring Security、MySQL、Redis；文件存储用阿里云 OSS，短信用腾讯云，评论情感分析用百度 AI
- 管理端前端（本仓库）：Vue 2、ElementUI、Axios、ECharts、vue-puzzle-vcode
- 游客前台：Nuxt、TailwindCSS、Vue 生态
- 前台后端：Koa2

## 本地运行（管理端）

依赖：Node >= 8.9，npm >= 3。若需真实数据，请先启动后端服务并在接口配置中指向对应地址。

```bash
npm install
npm run dev          # 本地调试
npm run build:prod   # 生产构建
npm run lint         # 代码检查
npm run test:unit    # 单元测试
```

## 目录速览

- `src/api`：业务接口封装
- `src/views`：各业务页面（景点、美食、线路、日志、统计等）
- `src/router`：路由与权限控制
- `src/store`：Vuex 模块
- `src/styles`：全局样式与主题
- `src/components`：通用组件（面包屑、图标、上传等）

## 成品截图

![](https://gitee.com/carolin-violet/travel_system_-gao-chun/raw/management-platform/markdown_images/1.webp)
![](https://gitee.com/carolin-violet/travel_system_-gao-chun/raw/management-platform/markdown_images/2.webp)
![](https://gitee.com/carolin-violet/travel_system_-gao-chun/raw/management-platform/markdown_images/3.webp)
![](https://gitee.com/carolin-violet/travel_system_-gao-chun/raw/management-platform/markdown_images/4.webp)
![](https://gitee.com/carolin-violet/travel_system_-gao-chun/raw/management-platform/markdown_images/5.webp)
![](https://gitee.com/carolin-violet/travel_system_-gao-chun/raw/management-platform/markdown_images/6.webp)
![](https://gitee.com/carolin-violet/travel_system_-gao-chun/raw/management-platform/markdown_images/7.webp)
![](https://gitee.com/carolin-violet/travel_system_-gao-chun/raw/management-platform/markdown_images/8.webp)
