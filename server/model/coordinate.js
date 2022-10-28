const Sequelize = require('sequelize')
const MySequelize = require('../config/mysqlConfig')

const coordinateModel = MySequelize.define('coordinate', {
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
  longitude: {
    type: Sequelize.DECIMAL(10,6),
    field:  'longitude',
    allowNull: false
  },
  latitude: {
    type: Sequelize.DECIMAL(10,6),
    field:  'latitude',
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

module.exports = coordinateModel