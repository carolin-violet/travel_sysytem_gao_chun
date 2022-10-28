const Sequelize = require('sequelize')
const MySequelize = require('../config/mysqlConfig') 

const routeDetailModel = MySequelize.define('route_detail', {
  id: {
    type: Sequelize.STRING(19),
    field:  'id',
    primaryKey: true
  },
  route_id: {
    type: Sequelize.STRING(20),
    field:  'route_id',
    allowNull: false
  },
  site: {
    type: Sequelize.STRING(25),
    field:  'site',
    allowNull: false
  },
  sort: {
    type: Sequelize.TINYINT,
    field:  'sort',
    allowNull: false
  },
  description: {
    type: Sequelize.STRING(255),
    field:  'description',
    allowNull: false
  },
  start_time: {
    type: Sequelize.TIME,
    field:  'start_time',
    allowNull: false
  },
  end_time: {
    type: Sequelize.TIME,
    field:  'end_time',
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

module.exports = routeDetailModel