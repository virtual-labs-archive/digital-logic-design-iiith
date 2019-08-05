
const express = require('express')
const app = express()
const port = 3000
var router = express.Router();
var path = require('path');
app.set('view engine', 'ejs');

app.get('/', function (req, res) {
    res.render(__dirname + "/index");
    app.use(express.static(__dirname));
    app.use(express.static(__dirname));
})
