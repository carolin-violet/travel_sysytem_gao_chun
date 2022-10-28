const Sequelize = require('sequelize')
const MySequelize = require('../config/mysqlConfig')

const delicacyModel = MySequelize.define('delicacy', {
  id: {
    type: Sequelize.STRING(19),
    field:  'id',
    primaryKey: true
  },
  name: {
    type: Sequelize.STRING(20),
    field:  'name',
    allowNull: false
  },
  description: {
    type: Sequelize.STRING(500),
    field:  'description',
    allowNull: false
  },
  cover: {
    type: Sequelize.STRING(255),
    field:  'cover',
    allowNull: false
  },
  recommend_rank: {
    type: Sequelize.TINYINT,
    field:  'recommend_rank',
    allowNull: false
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
})

module.exports = delicacyModel
