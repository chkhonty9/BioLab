// config/db.js
const { Sequelize } = require('sequelize');

const sequelize = new Sequelize(
    process.env.MYSQL_DATABASE || 'my_db',
    process.env.MYSQL_USER || 'root',
    process.env.MYSQL_PASSWORD || '',
    {
      host: process.env.MYSQL_HOST || '178.16.129.132', // Change this if you're using a different host
      port: process.env.MYSQL_PORT || 3307,
      dialect: 'mysql'
});

module.exports = sequelize;
