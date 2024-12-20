const express = require('express');
const router = express.Router();
const resultsController = require('../controllers/resultatsController');

// Routes for managing results
router.post('/', resultsController.createResult); // Create a result
router.put('/:id', resultsController.updateResult); // Update a result by ID
router.get('/', resultsController.getAllResults); // Get all results
router.get('/:id', resultsController.getResultById); // Get result by ID
router.get('/project/:project_id', resultsController.getResultsByProjectId); // Get results by project ID

module.exports = router;
