const mongoose = require('mongoose');

const doctorSchema = new mongoose.Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    specialty: { type: String, required: true },
    fees: { type: Number, required: true },
    bio: { type: String },
    contact: { type: String }
});

module.exports = mongoose.model('Doctor', doctorSchema);