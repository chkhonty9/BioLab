const Result = require('../models/Result');
const Project = require('../models/Projet');  // Ensure you have the Project model

async function createResult(data) {
    try {
        const result = await Result.create(data);
        return result;
    } catch (err) {
        throw new Error('Error creating result: ' + err.message);
    }
}

async function getAllResults() {
    try {
        const results = await Result.findAll({
            include: {
                model: Project,  // Include the project model
                as: 'projet',   // Alias as defined in Result model's belongsTo relationship
            }
        });
        return results;
    } catch (err) {
        throw new Error('Error fetching results: ' + err.message);
    }
}

async function getResultById(id) {
    try {
        const result = await Result.findByPk(id, {
            include: {
                model: Project,  // Include the project model
                as: 'projet',   // Alias as defined in Result model's belongsTo relationship
            }
        });
        if (!result) {
            throw new Error('Result not found');
        }
        return result;
    } catch (err) {
        throw new Error('Error fetching result: ' + err.message);
    }
}

async function updateResult(id, data) {
    try {
        const result = await Result.findByPk(id);
        if (!result) {
            throw new Error('Result not found');
        }
        await result.update(data);
        return result;
    } catch (err) {
        throw new Error('Error updating result: ' + err.message);
    }
}

async function deleteResult(id) {
    try {
        const result = await Result.findByPk(id);
        if (!result) {
            throw new Error('Result not found');
        }
        await result.destroy();
        return { message: 'Result deleted' };
    } catch (err) {
        throw new Error('Error deleting result: ' + err.message);
    }
}

// New function to get results by project ID, including project details
async function getResultsByProject(projectId) {
    try {
        const results = await Result.findAll({
            where: {
                project_id: projectId,  // Ensure the key matches the one in your table
            },
            include: {
                model: Project,  // Include the project model
                as: 'projet',   // Alias to access the project's attributes
            }
        });

        if (!results || results.length === 0) {
            throw new Error('No results found for this project');
        }

        return results;
    } catch (err) {
        throw new Error('Error fetching results for project: ' + err.message);
    }
}

module.exports = {
    createResult,
    getAllResults,
    getResultById,
    updateResult,
    deleteResult,
    getResultsByProject
};
