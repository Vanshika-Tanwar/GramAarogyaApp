const { OAuth2Client } = require('google-auth-library');

const client = new OAuth2Client(process.env.GOOGLE_CLIENT_ID);

const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const User = require('../models/User');

// @route   POST /api/auth/register
router.post('/register', async (req, res) => {
    const { name, email, password } = req.body;
    try {
        let user = await User.findOne({ email });
        if (user) { return res.status(400).json({ msg: 'User already exists' }); }
        user = new User({ name, email, password });
        await user.save();
        const payload = { user: { id: user.id } };
        jwt.sign(payload, process.env.JWT_SECRET, { expiresIn: '1h' }, (err, token) => {
            if (err) throw err;
            res.json({ token, name: user.name });
        });
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});

// @route   POST /api/auth/login
router.post('/login', async (req, res) => {
    const { email, password } = req.body;
    try {
        let user = await User.findOne({ email });
        if (!user) { return res.status(400).json({ msg: 'Invalid Credentials' }); }
        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) { return res.status(400).json({ msg: 'Invalid Credentials' }); }
        const payload = { user: { id: user.id } };
        jwt.sign(payload, process.env.JWT_SECRET, { expiresIn: '1h' }, (err, token) => {
            if (err) throw err;
            res.json({ token, name: user.name });
        });
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});

// @route   GET /api/auth/me
router.get('/me', require('../middleware/authMiddleware'), async (req, res) => {
    try {
        const user = await User.findById(req.user.id).select('-password');
        res.json(user);
    } catch (err) {
        console.error(err.message);
        res.status(500).send('Server Error');
    }
});

router.post('/google', async (req, res) => {
    const { token } = req.body;
    try {
        const ticket = await client.verifyIdToken({
            idToken: token,
            audience: process.env.GOOGLE_CLIENT_ID,
        });
        const payload = ticket.getPayload();
        const { email, name, sub: googleId } = payload;

        let user = await User.findOne({ googleId });
        
        if (user) {
            // User exists, log them in
            const payload = { user: { id: user.id } };
            const jwtToken = jwt.sign(payload, process.env.JWT_SECRET, { expiresIn: '1h' });
            return res.json({ token: jwtToken, name: user.name });
        }

        // User is new, create an account
        user = new User({ email, name, googleId });
        await user.save();
        
        const payloadForNewUser = { user: { id: user.id } };
        const jwtToken = jwt.sign(payloadForNewUser, process.env.JWT_SECRET, { expiresIn: '1h' });
        res.json({ token: jwtToken, name: user.name });

    } catch (err) {
        console.error(err.message);
        res.status(500).send('Google authentication failed');
    }
});

module.exports = router;