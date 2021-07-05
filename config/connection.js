var mysql = require("mysql")
var maindbconnection = mysql.createConnection({
    host:'localhost',
    user: 'root',
    password: '',
    database:'libraryapp'
})

maindbconnection.connect(
    (error)=>{
        if(!error){
            console.log("Database connected")
        }
        else{
            console.log("!!! Database connection error !!!")
        }
    }
)

module.exports=maindbconnection;