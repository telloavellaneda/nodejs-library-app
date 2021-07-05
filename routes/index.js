var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next){
    //res.send("Welcome to Library app, type localhost/books to see how it works :)")
    res.redirect('/books')
});

module.exports = router;