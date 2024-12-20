const { DataTypes } = require('sequelize');
const sequelize = require('../config/db');
const Result = require('./Result');  // Import Result model

// Define the Project (projet) model
const Project = sequelize.define('projet', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    titre: {
        type: DataTypes.STRING(255),
        allowNull: false,
    },
    date: {
        type: DataTypes.DATE,
        allowNull: false,
    },
    hum_max: {
        type: DataTypes.FLOAT,
        allowNull: false,
    },
    temp_max: {
        type: DataTypes.FLOAT,
        allowNull: false,
    },
    pompe_st: {
        type: DataTypes.INTEGER,
        allowNull: false,
    },
    biologist_id: {
        type: DataTypes.BIGINT,
        allowNull: false,
    },
    material_id: {
        type: DataTypes.INTEGER,
        allowNull: false,
    },
}, {
    tableName: 'projet',
    timestamps: false,  // If your table doesn't use `createdAt` and `updatedAt`
});


module.exports = Project;
