var databaseRequiredInController = require('../config/connection');
var bookModelRequiredInController = require('../model/bookModel');
var toDelete = require('fs');
const { EWOULDBLOCK } = require('constants');

module.exports={
    home:function (req, res) {
        bookModelRequiredInController.selectQuery(databaseRequiredInController, function(error, rows){   
            console.log(rows)
            res.render('books/index', { title: 'Library App', books:rows });
         })
    },

    createbook:function (req, res) {
        res.render('books/newbook');
    },

    savebook:function (req, res) {     
        console.log(req.body)
        console.log(req.file.filename);
        bookModelRequiredInController.insertQuery(databaseRequiredInController,req.body,req.file,function(error){
            res.redirect('/books')
         });
    },

    deleteBook:function(req, res) {
        console.log(req.params.id);

        bookModelRequiredInController.returnDataIDQuery(databaseRequiredInController,req.params.id,function(error, rows){
            var imageName = "public/images/"+(rows[0].image);
           
            if(toDelete.existsSync(imageName)){
                toDelete.unlinkSync(imageName);
            }
            bookModelRequiredInController.deleteQuery(databaseRequiredInController,req.params.id,function(error){
                res.redirect('/books');
            });
        });
    },

    editBook:function(req, res) {
        bookModelRequiredInController.returnDataIDQuery(databaseRequiredInController,req.params.id,function(error, rows){
            console.log(rows[0]);
            res.render('books/edit', {book:rows[0]});
        });
    },

    updateBook:function name(req, res) {
        console.log(req.body.name);
        //console.log(req.file.filename);
        if (req.file){
            if (req.file.filename){
                bookModelRequiredInController.returnDataIDQuery(databaseRequiredInController,req.body.id,function(error, rows){
                    var imageName = "public/images/"+(rows[0].image);
                   
                    if(toDelete.existsSync(imageName)){
                        toDelete.unlinkSync(imageName);
                    }
                    bookModelRequiredInController.updateIMGQuery(databaseRequiredInController,req.body,req.file,function(error){});
                });

            }
        }
        if (req.body.bookname){
            bookModelRequiredInController.updateQuery(databaseRequiredInController,req.body,function(error){});
        }
    res.redirect('/books');
    }
}