const db = require('../db/db');
const Result = require('../models/result.model');

// Create a new result
exports.createResult = async (req, res) => {
    const { val_temp, val_hum, val_sol, date_resultat, serie, project_id } = req.body;
    try {
        const [result] = await db.execute(
            "INSERT INTO Resultat (val_temp, val_hum, val_sol, date_resultat, serie, project_id) VALUES (?, ?, ?, ?, ?, ?)",
            [val_temp, val_hum, val_sol, date_resultat, serie, project_id]
        );
        const newResult = new Result(result.insertId, val_temp, val_hum, val_sol, date_resultat, serie, project_id);
        res.status(201).json({ message: "Result created successfully", result: newResult });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

// Update an existing result
exports.updateResult = async (req, res) => {
    const { id } = req.params;
    const { val_temp, val_hum, val_sol, date_resultat, serie, project_id } = req.body;
    try {
        const [update] = await db.execute(
            "UPDATE Resultat SET val_temp = ?, val_hum = ?, val_sol = ?, date_resultat = ?, serie = ?, project_id = ? WHERE id = ?",
            [val_temp, val_hum, val_sol, date_resultat, serie, project_id, id]
        );

        if (update.affectedRows === 0) {
            return res.status(404).json({ message: `Result with id ${id} not found` });
        }

        res.status(200).json({ message: "Result updated successfully" });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

// Retrieve all results
exports.getAllResults = async (req, res) => {
    try {
        const [results] = await db.execute("SELECT * FROM Resultat");
        const formattedResults = results.map(
            (row) => new Result(row.id, row.val_temp, row.val_hum, row.val_sol, row.date_resultat, row.serie, row.project_id)
        );
        res.status(200).json(formattedResults);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

// Retrieve a specific result by id
exports.getResultById = async (req, res) => {
    const { id } = req.params;
    try {
        const [result] = await db.execute("SELECT * FROM Resultat WHERE id = ?", [id]);

        if (result.length === 0) {
            return res.status(404).json({ message: `Result with id ${id} not found` });
        }

        const foundResult = new Result(
            result[0].id,
            result[0].val_temp,
            result[0].val_hum,
            result[0].val_sol,
            result[0].date_resultat,
            result[0].serie,
            result[0].project_id
        );

        res.status(200).json(foundResult);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

// Retrieve results by project_id
exports.getResultsByProjectId = async (req, res) => {
    const { project_id } = req.params;
    try {
        const [results] = await db.execute("SELECT * FROM Resultat WHERE project_id = ?", [project_id]);
        if (results.length === 0) {
            return res.status(404).json({ message: "No results found for this project ID" });
        }
        const formattedResults = results.map(
            (row) => new Result(row.id, row.val_temp, row.val_hum, row.val_sol, row.date_resultat, row.serie, row.project_id)
        );
        res.status(200).json(formattedResults);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};
