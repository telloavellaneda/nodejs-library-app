var express = require('express');
var router = express.Router();
const booksController = require("../controllers/booksController");

var multer = require('multer');
var imgDate = Date.now();

var routeStorage = multer.diskStorage({
    destination:function (request, file, callback) {
        callback(null, './public/images');
    },
    filename:function(request, file, callback){
        console.log(file);
        callback(null, imgDate+"_"+file.originalname);
    }
}
);
var load = multer({ storage:routeStorage });

/* GET home page. */
router.get('/',booksController.home);
router.get('/newbook',booksController.createbook);
router.post("/",load.single("bookimg"),booksController.savebook);
router.post('/delete/:id',booksController.deleteBook);
router.get('/edit/:id',booksController.editBook);
router.post('/update',load.single("bookimg"),booksController.updateBook);

module.exports = router;