const booksController = require("../controllers/booksController")

module.exports = {
    selectQuery:function (modelDatabaseParameter, myOwnFunction){
        
        modelDatabaseParameter.query("SELECT * FROM books", myOwnFunction);
    },

    insertQuery:function (modelDatabaseParameter, myData, myFile, funcion){
        modelDatabaseParameter.query("INSERT INTO books (name, image) VALUES (?, ?)",[myData.bookname, myFile.filename], funcion);
    },

    returnDataIDQuery:function (modelDatabaseParameter, id, myOwnFunction){
        modelDatabaseParameter.query("SELECT * FROM books WHERE id=?", [id], myOwnFunction);
    },

    deleteQuery:function (modelDatabaseParameter, id, myOwnFunction){
        modelDatabaseParameter.query("DELETE FROM books WHERE id=?", [id], myOwnFunction);
    },

    updateQuery:function (modelDatabaseParameter, myData, funcion){
        modelDatabaseParameter.query("UPDATE books SET name=? WHERE id=?",[myData.bookname, myData.id], funcion);
    },

    updateIMGQuery:function (modelDatabaseParameter, myData, myFile, funcion){
        modelDatabaseParameter.query("UPDATE books SET image=? WHERE id=?",[myFile.filename, myData.id], funcion);
    }
}