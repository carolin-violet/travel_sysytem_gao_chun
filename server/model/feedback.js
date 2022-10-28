const Sequelize = require('sequelize')
const MySequelize = require('../config/mysqlConfig')

const feedbackModel = MySequelize.define('feedback', {
  id: {
    type: Sequelize.STRING(19),
    field:  'id',
    primaryKey: true
  },
  tourist_id: {
    type: Sequelize.STRING(19),
    field:  'tourist_id',
    allowNull: false
  },
  content: {
    type: Sequelize.TEXT,
    field:  'content',
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
  replay: {
    type: Sequelize.STRING(255),
    field:  'replay',
    allowNull: true
  },
  replay_time: {
    type: Sequelize.DATE,
    field:  'replay_time',
    allowNull: true
  },
})

module.exports = feedbackModel
