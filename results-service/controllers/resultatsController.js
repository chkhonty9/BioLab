// controllers/resultController.js
const resultService = require('../service/resultService');


async function create(req, res) {
    try {
        const result = await resultService.createResult(req.body);
        res.status(201).json(result);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
}

async function getAll(req, res) {
    try {
        const results = await resultService.getAllResults();
        res.json(results);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
}

async function getById(req, res) {
    try {
        const result = await resultService.getResultById(req.params.id);
        res.json(result);
    } catch (err) {
        res.status(404).json({ error: err.message });
    }
}

async function update(req, res) {
    try {
        const result = await resultService.updateResult(req.params.id, req.body);
        res.json(result);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
}

async function deleteById(req, res) {
    try {
        const message = await resultService.deleteResult(req.params.id);
        res.json(message);
    } catch (err) {
        res.status(404).json({ error: err.message });
    }
}

async function getByProject(req, res) {
    try {
        const projectId = req.params.projectId; // or req.query.projectId depending on the route
        const results = await resultService.getResultsByProject(projectId);

        if (results.length === 0) {
            return res.status(404).json({ error: 'No results found for this project' });
        }

        res.json(results);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
}


module.exports = {
    create,
    getAll,
    getById,
    update,
    deleteById,
    getByProject
};
