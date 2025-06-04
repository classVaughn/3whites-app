const express = require("express");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const users = require("../utils/users");

const router = express.Router();
const JWT_SECRET = process.env.JWT_SECRET || "defaultsecret";

// Helper function for email validation
function isValidEmail(email) {
  // Simple regex for demonstration; you can use a stricter one if needed
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

// Register
router.post("/register", async (req, res) => {
  let { email, password } = req.body;

  // Trim whitespace
  email = email ? email.trim() : "";
  password = password ? password.trim() : "";

  if (!email || !password) {
    return res.status(400).json({ message: "Email and password are required" });
  }

  if (!isValidEmail(email)) {
    return res.status(400).json({ message: "Invalid email format" });
  }

  if (password.length < 8) {
    return res.status(400).json({ message: "Password must be at least 8 characters" });
  }

  // Check if user already exists
  const existingUser = users.find((user) => user.email === email);
  if (existingUser) {
    return res.status(400).json({ message: "Email already in use" });
  }

  // No password strength check
  const hashedPassword = await bcrypt.hash(password, 10);
  users.push({ email, password: hashedPassword });

  res.status(201).json({ message: "Registration successful" });
});

// Login
router.post("/login", async (req, res) => {
  let { email, password } = req.body;

  // Trim whitespace
  email = email ? email.trim() : "";
  password = password ? password.trim() : "";

  if (!email || !password) {
    return res.status(400).json({ message: "Email and password are required" });
  }

  if (!isValidEmail(email)) {
    return res.status(400).json({ message: "Invalid email format" });
  }

  const user = users.find((u) => u.email === email);
  if (!user) {
    return res.status(400).json({ message: "Email not registered" });
  }

  const isMatch = await bcrypt.compare(password, user.password);
  if (!isMatch) {
    return res.status(400).json({ message: "Incorrect password" });
  }

  const token = jwt.sign({ email: user.email }, JWT_SECRET, { expiresIn: "1h" });
  res.json({ token, message: "Login successful" });
});

module.exports = router;
