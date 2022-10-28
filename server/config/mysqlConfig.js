//配置sequelize：
//导入mysql模块
const mysql = require('mysql2')

//导入sequelize模块
const Sequelize = require('sequelize')

//创建sequelize对象，初始化连接(支持连接池)：四个参数：数据库名称，用户名，密码，配置
var MySequelize = new Sequelize('travel','root','zj20001125',{
    host: 'localhost', //数据库地址
    port: 3306,
    dialect: 'mysql',   //数据库类型
    timezone: "+08:00",
    pool:{
        max:30, //最大连接对象的个数
        min:1, //最少连接对象的个数
        idle:10000 //最长等待时间，单位是毫秒
    },
    define: {
      timestamps: false,  // 解决查询时sql语句自动添加createAt、updateAt字段的问题
      freezeTableName: true,   // 主要解决查询时将表名变复数的问题
  }
}) 

module.exports = MySequelize;
