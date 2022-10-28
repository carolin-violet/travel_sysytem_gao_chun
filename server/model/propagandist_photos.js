const Sequelize = require('sequelize')
const MySequelize = require('../config/mysqlConfig')

const propagandistPhotosModel = MySequelize.define('propagandist_photos', {
  id: {
    type: Sequelize.STRING(19),
    field:  'id',
    primaryKey: true
  },
  url: {
    type: Sequelize.STRING(255),
    field:  'url',
    allowNull: false
  },
  mark_id: {
    type: Sequelize.STRING(19),
    field:  'mark_id',
    allowNull: false
  },
  mark: {
    type: Sequelize.STRING(10),
    field:  'mark',
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

module.exports = propagandistPhotosModel