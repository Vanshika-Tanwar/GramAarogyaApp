const mongoose = require('mongoose');
const dotenv = require('dotenv');
const User = require('./api/models/User');
const Doctor = require('./api/models/Doctor');
const Clinic = require('./api/models/Clinic');
const Medicine = require('./api/models/Medicine');

dotenv.config();

const mongoURI = process.env.MONGO_URI;

mongoose.connect(mongoURI)
    .then(() => console.log('Database connection successful.'))
    .catch(err => console.error('Database connection error:', err));

const sampleDoctors = [
    {
        userId: new mongoose.Types.ObjectId(), // Replace with a real user ID if needed
        specialty: 'General Physician',
        fees: 500,
        bio: 'Dr. Sharma has over 10 years of experience in general medicine.'
    },
    {
        userId: new mongoose.Types.ObjectId(),
        specialty: 'Dermatologist',
        fees: 750,
        bio: 'Dr. Khan is a leading expert in skin care and cosmetic dermatology.'
    }
];

const sampleClinics = [
    {
        name: 'City Health Clinic',
        address: '123 Main Street',
        phone: '123-456-7890',
        location: {
            type: 'Point',
            coordinates: [77.2090, 28.6139]
        }
    },
    {
        name: 'Aarogya Medical Center',
        address: '456 Oak Avenue',
        phone: '098-765-4321',
        location: {
            type: 'Point',
            coordinates: [77.1025, 28.7041]
        }
    }
];

const sampleMedicines = [
    {
        name: 'Paracetamol',
        brand: 'Crocin',
        description: 'Pain reliever and fever reducer.',
        price: 25.50
    },
    {
        name: 'Amoxicillin',
        brand: 'Mox',
        description: 'Antibiotic used to treat bacterial infections.',
        price: 89.00
    },
    {
        name: 'Ibuprofen',
        brand: 'Brufen',
        description: 'NSAID used for pain, fever, and inflammation.',
        price: 45.75
    }
];

// Function to insert data
const seedDatabase = async () => {
    try {
        await Doctor.deleteMany({});
        await Clinic.deleteMany({});
        await Medicine.deleteMany({});

        await Doctor.insertMany(sampleDoctors);
        await Clinic.insertMany(sampleClinics);
        await Medicine.insertMany(sampleMedicines);

        console.log('Database seeded successfully!');
    } catch (err) {
        console.error('Error seeding database:', err);
    } finally {
        mongoose.disconnect();
    }
};

seedDatabase();