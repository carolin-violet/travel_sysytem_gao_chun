const Sequelize = require('sequelize')
const MySequelize = require('../config/mysqlConfig')

const commentModel = MySequelize.define('comment', {
  id: {
    type: Sequelize.STRING(19),
    field:  'id',
    primaryKey: true
  },
  mark_id: {
    type: Sequelize.STRING(19),
    field:  'mark_id',
    allowNull: false
  },
  tourist_id: {
    type: Sequelize.STRING(19),
    field:  'tourist_id',
    allowNull: false
  },
  comment: {
    type: Sequelize.STRING(255),
    field:  'comment',
    allowNull: false
  },
  sentiment: {
    type: Sequelize.TINYINT,
    field:  'sentiment',
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
})

module.exports = commentModel