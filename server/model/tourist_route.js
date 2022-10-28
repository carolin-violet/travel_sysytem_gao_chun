const Sequelize = require('sequelize')
const MySequelize = require('../config/mysqlConfig')

const touristRouteModel = MySequelize.define('tourist_route', {
  id: {
    type: Sequelize.STRING(19),
    field:  'id',
    primaryKey: true
  },
  title: {
    type: Sequelize.STRING(25),
    field:  'title',
    allowNull: false
  },
  description: {
    type: Sequelize.STRING(255),
    field:  'description',
    allowNull: false
  },
  reason: {
    type: Sequelize.STRING(255),
    field:  'reason',
    allowNull: false
  },
  price: {
    type: Sequelize.DECIMAL(10,2),
    field:  'price',
    allowNull: false
  },
  discount_price: {
    type: Sequelize.DECIMAL(10,2),
    field:  'discount_price',
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

module.exports = touristRouteModel