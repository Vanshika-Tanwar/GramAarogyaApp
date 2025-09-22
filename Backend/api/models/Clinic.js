const mongoose = require('mongoose');

const clinicSchema = new mongoose.Schema({
    name: { type: String, required: true },
    address: { type: String, required: true },
    phone: { type: String },
    // GeoJSON object for geospatial queries
    location: {
        type: {
            type: String,
            enum: ['Point'],
            required: true
        },
        coordinates: {
            type: [Number], // [longitude, latitude]
            required: true
        }
    }
});

// Create a 2dsphere index for fast geospatial queries
clinicSchema.index({ location: '2dsphere' });

module.exports = mongoose.model('Clinic', clinicSchema);