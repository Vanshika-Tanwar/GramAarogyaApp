const express = require('express');
const router = express.Router();
const Medicine = require('../models/Medicine');

router.get('/', async (req, res) => {
    try {
        const medicines = await Medicine.find();
        res.json(medicines);
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});

// @route   GET /api/medicines/:id
// @desc    Get a single medicine by ID
router.get('/:id', async (req, res) => {
    try {
        const medicine = await Medicine.findById(req.params.id);
        if (!medicine) { return res.status(404).json({ msg: 'Medicine not found' }); }
        res.json(medicine);
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});

module.exports = router;