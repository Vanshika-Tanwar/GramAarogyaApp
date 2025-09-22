const express = require('express');
const mongoose = require('mongoose');
const dotenv = require('dotenv');
const cors = require('cors');

dotenv.config();

const app = express();

app.use(express.json()); 
app.use(cors());

app.get('/', (req, res) => {
    res.send('Gram Aarogya Backend API is running!');
});

// Database Connection
const mongoURI = process.env.MONGO_URI || 'mongodb://localhost:27017/gramaarogya';

mongoose.connect(mongoURI)
.then(() => console.log('MongoDB connected successfully'))
.catch(err => console.error('MongoDB connection error:', err));

const authRoutes = require('./api/routes/auth');
const clinicRoutes = require('./api/routes/clinics');
const medicineRoutes = require('./api/routes/medicines');
const healthRecordRoutes = require('./api/routes/healthrecords');
const doctorRoutes = require('./api/routes/doctors');

app.use('/api/auth', authRoutes);
app.use('/api/clinics', clinicRoutes);
app.use('/api/medicines', medicineRoutes);
app.use('/api/healthrecords', healthRecordRoutes);
app.use('/api/doctors', doctorRoutes);

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
