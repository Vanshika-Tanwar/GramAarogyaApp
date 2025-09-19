const express = require('express');
const router = express.Router();
const Clinic = require('../models/Clinic');

router.get('/', async (req, res) => {
    try {
        const clinics = await Clinic.find();
        res.json(clinics);
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});

router.post('/', async (req, res) => {
    const { name, address, phone, location } = req.body;
    try {
        const newClinic = new Clinic({
            name,
            address,
            phone,
            location: { type: 'Point', coordinates: [location.longitude, location.latitude] }
        });
        const clinic = await newClinic.save();
        res.status(201).json(clinic);
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});

router.get('/nearby', async (req, res) => {
    const { longitude, latitude, maxDistance } = req.query;
    try {
        const clinics = await Clinic.find({
            location: {
                $near: {
                    $geometry: {
                        type: 'Point',
                        coordinates: [parseFloat(longitude), parseFloat(latitude)]
                    },
                    $maxDistance: parseInt(maxDistance) || 5000 // default 5km
                }
            }
        });
        res.json(clinics);
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});

module.exports = router;