const express = require('express');
const ejs = require('ejs');
const path = require('path');

const app = express();
const axios = require('axios');

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Serve static files
app.use(express.static(path.join(__dirname, 'public')));

// Define routes
app.get('/home', async (req, res) => {
  try {
    res.render('home');
  } catch (error) {
    console.error('Error fetching books:', error);
    res.status(500).send('Error fetching books');
  }
});

app.get('/books', async (req, res) => {
  try {
    const response = await axios.get('http://localhost:8081/allBooks');
    const books = response.data;
    res.render('books', { books });
    console.log("Render Books: " + books[0]);
  } catch (error) {
    console.error('Error fetching books:', error);
    res.status(500).send('Error fetching books');
  }
});

app.get('/loan', async (req, res) => {
  try {

    res.render('loan');
  } catch (error) {
    console.error('Error fetching books:', error);
    res.status(500).send('Error fetching books');
  }
});

app.get('/loan/:action', async (req, res) => {
  try {
    const action = req.params.action;
    res.render('loan');
  } catch (error) {
    console.error('Error fetching books:', error);
    res.status(500).send('Error fetching books');
  }
});

app.get('/return', async (req, res) => {
  try {
    res.render('return');
  } catch (error) {
    console.error('Error fetching books:', error);
    res.status(500).send('Error fetching books');
  }
});

app.get('/analyst', async (req, res) => {
  try {
    res.render('analyst');
  } catch (error) {
    console.error('Error fetching books:', error);
    res.status(500).send('Error fetching books');
  }
});

app.listen(3000, () => {
  console.log('Server is listening on port 3000');
});