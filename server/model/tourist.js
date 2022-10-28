const Sequelize = require('sequelize')
const MySequelize = require('../config/mysqlConfig')

const touristModel = MySequelize.define('tourist', {
  id: {
    type: Sequelize.STRING(19),
    field:  'id',
    primaryKey: true
  },
  nickName: {
    type: Sequelize.STRING(25),
    field:  'nickName',
    allowNull: false
  },
  telephone: {
    type: Sequelize.STRING(11),
    field:  'telephone',
    allowNull: false
  },
  password: {
    type: Sequelize.STRING(255),
    field:  'password',
    allowNull: false
  },
  email: {
    type: Sequelize.STRING(30),
    field:  'email',
    allowNull: false
  },
  birthday: {
    type: Sequelize.DATE,
    field:  'birthday',
    allowNull: false
  },
  gender: {
    type: Sequelize.STRING(1),
    field:  'gender',
    allowNull: false
  },
  address: {
    type: Sequelize.STRING(255),
    field:  'address',
    allowNull: false
  },
  avatar: {
    type: Sequelize.STRING(255),
    field:  'avatar',
    allowNull: true
  },
  is_deleted: {
    type: Sequelize.TINYINT,
    field:  'is_deleted',
    allowNull: false
  },
  create_time: {
    type: Sequelize.DATE,
    field:  'create_time',
    allowNull: false
  },
  modify_time: {
    type: Sequelize.DATE,
    field:  'modify_time',
    allowNull: false
  },
},
// {
//   tableName: 'tourist'   // 主要解决查询时将表名变复数的问题
// }
)

module.exports = touristModel