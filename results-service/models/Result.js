const { DataTypes } = require('sequelize');
const sequelize = require('../config/db');
const Project = require("./Projet");  // Import Project model

// Define the Result model
const Result = sequelize.define('results', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    val_temp: {
        type: DataTypes.FLOAT,
        allowNull: false,
    },
    val_hum: {
        type: DataTypes.FLOAT,
        allowNull: false,
    },
    val_sol: {
        type: DataTypes.FLOAT,
        allowNull: false,
    },
    date_resultat: {
        type: DataTypes.DATE,
        allowNull: false,
    },
    serie: {
        type: DataTypes.STRING(255),
        allowNull: false,
    },
    project_id: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'projet',  // Table name of project
            key: 'id',
        },
        onDelete: 'CASCADE',  // Optional: define what happens when the referenced project is deleted
    },
    createdAt: {
        type: DataTypes.DATE,
        allowNull: false,
        defaultValue: DataTypes.NOW,
    },
    updatedAt: {
        type: DataTypes.DATE,
        allowNull: false,
        defaultValue: DataTypes.NOW,
    },
}, {
    timestamps: true,  // Enable `createdAt` and `updatedAt` timestamps
});

// Define the `belongsTo` relationship in Result for Project
Result.belongsTo(Project, {
    foreignKey: 'project_id',
    targetKey: 'id', // Reference the id of Project as the target key
    as: 'projet'
});

module.exports = Result;
