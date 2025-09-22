const mongoose = require('mongoose');

const medicineSchema = new mongoose.Schema({
    name: { type: String, required: true },
    brand: { type: String, required: true },
    description: { type: String },
    price: { type: Number, required: true },
    imageUrl: { type: String }
});

module.exports = mongoose.model('Medicine', medicineSchema);