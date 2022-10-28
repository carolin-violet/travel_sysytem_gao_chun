const Sequelize = require('sequelize')
const MySequelize = require('../config/mysqlConfig')

const articleModel = MySequelize.define('article', {
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
  content_html: {
    type: Sequelize.TEXT('long'),
    field:  'content_html',
    allowNull: false
  },
  content_text: {
    type: Sequelize.TEXT('long'),
    field:  'content_text',
    allowNull: false
  },
  mark: {
    type: Sequelize.STRING(10),
    field:  'mark',
    allowNull: false
  },
  mark_id: {
    type: Sequelize.STRING(19),
    field:  'mark_id',
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

module.exports = articleModel