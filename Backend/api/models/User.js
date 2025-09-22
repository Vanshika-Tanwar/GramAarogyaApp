const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');

const userSchema = new mongoose.Schema({
    email: { 
        type: String,
        required: true, 
        unique: true 
    },
    password: { 
        type: String, 
        required: function() { return !this.googleId && !this.appleId; } 
    },
    googleId: { 
        type: String, 
        unique: true, 
        sparse: true 
    },
    appleId: { 
        type: String, 
        unique: true, 
        sparse: true 
    },
    name: { 
        type: String, 
        required: true 
    },
    language: { 
        type: String, 
        enum: ['English', 'Hindi', 'Punjabi'], 
        default: 'English' 
    }
}, { timestamps: true });

userSchema.pre('save', async function(next) {
    if (!this.isModified('password')) { return next(); }
    const salt = await bcrypt.genSalt(10);
    this.password = await bcrypt.hash(this.password, salt);
    next();
});

module.exports = mongoose.model('User', userSchema);