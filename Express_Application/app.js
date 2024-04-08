const express = require('express');
const ejs = require('ejs');
const path = require('path');

const app = express();

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Serve static files
app.use(express.static(path.join(__dirname, 'public')));

// Define routes
app.get('/home', (req, res) => {
  res.render('home');
});

app.get('/books', (req, res) => {
  res.render('books');
});

app.listen(3000, () => {
  console.log('Server is listening on port 3000');
});