// routes/resultRoutes.js
const express = require('express');
const router = express.Router();
const resultController = require('../controllers/resultatsController');

// Define routes
router.post('/results', resultController.create);
router.get('/results', resultController.getAll);
router.get('/results/:id', resultController.getById);
router.put('/results/:id', resultController.update);
router.delete('/results/:id', resultController.deleteById);
router.get('/results/project/:projectId', resultController.getByProject);

module.exports = router;
