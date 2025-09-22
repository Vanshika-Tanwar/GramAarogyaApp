const express = require('express');
const router = express.Router();
const authMiddleware = require('../middleware/authMiddleware');
const HealthRecord = require('../models/HealthRecord');

// @route   POST /api/healthrecords
router.post('/', authMiddleware, async (req, res) => {
    try {
        const { doctor, symptoms, diagnosis, medicines, notes } = req.body;
        const newRecord = new HealthRecord({
            patient: req.user.id,
            doctor,
            symptoms,
            diagnosis,
            medicines,
            notes
        });
        const record = await newRecord.save();
        res.json(record);
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});

// @route   GET /api/healthrecords/my
router.get('/my', authMiddleware, async (req, res) => {
    try {
        const records = await HealthRecord.find({ patient: req.user.id }).sort({ date: -1 });
        res.json(records);
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});

module.exports = router;