const Sequelize = require('sequelize')
const MySequelize = require('../config/mysqlConfig')

const orderFormModel = MySequelize.define('order_form', {
  id: {
    type: Sequelize.STRING(19),
    field:  'id',
    primaryKey: true
  },
  commodity_id: {
    type: Sequelize.STRING(19),
    field:  'commodity_id',
    allowNull: false
  },
  tourist_id: {
    type: Sequelize.STRING(19),
    field:  'tourist_id',
    allowNull: false
  },
  appointment_time: {
    type: Sequelize.DATE,
    field:  'appointment_time',
    allowNull: false
  },
  phone: {
    type: Sequelize.STRING(11),
    field:  'phone',
    allowNull: false
  },
  name: {
    type: Sequelize.STRING(30),
    field:  'name',
    allowNull: false
  },
  adult: {
    type: Sequelize.INTEGER,
    field:  'adult',
    allowNull: true
  },
  child: {
    type: Sequelize.INTEGER,
    field:  'child',
    allowNull: true
  },
  mark: {
    type: Sequelize.STRING(10),
    field:  'mark',
    allowNull: false
  },
  amount: {
    type: Sequelize.DECIMAL(10,2),
    field:  'amount',
    allowNull: false
  },
  is_paid: {
    type: Sequelize.TINYINT,
    field:  'is_paid',
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
},
)

module.exports = orderFormModel