const Sequelize = require('sequelize')
const MySequelize = require('../config/mysqlConfig')

const travelNoteModel = MySequelize.define('travel_note', {
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
})

module.exports = travelNoteModel