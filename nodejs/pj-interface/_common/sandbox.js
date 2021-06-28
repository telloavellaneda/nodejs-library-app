const { v4: uuidv4 } = require('uuid');
const jwt = require('jsonwebtoken');
const dotenv = require('dotenv');
const fs = require('fs');
const dec = require('./encrypt-decrypt');

dotenv.config({ path: 'batch/config/config.env' });

var token = jwt.sign(
    {
        idPais: '1',
        idTienda: '109'
    }
    , dec.decrypt('b4e257b84bae65efd322cee096364e35') );

// console.log(`token`, token);

// for (var i =0; i < 30; i++)
//     console.log(uuidv4().toString());

// const timezoned = () => {
//     return new Date().toLocaleString('en-US', {
//         timeZone: 'America/Mexico_City'
//     });
// }

// console.log(fs.readFileSync('./test.txt', "utf8").toString().split("\n").filter(Boolean).map(element => element.replace('\r','')));
console.log(fs.readFileSync('./test.txt', "utf8").toString().replace(/[\n\r]/g, ','));
console.log(fs.readFileSync('./test.txt', "utf8").toString().split(/[\r\n]/g).filter(Boolean));

// console.log(new Date().toISOString().split('T')[0])

// var token = fs.readFileSync(process.env.JWT_FILE, process.env.CONFIG_ENCODING);
// console.log(`token`, token);
// var decoded = jwt.verify(token.trim(), dec.decrypt(process.env.JWT_SECRET));
// console.log(`decoded`, decoded);

// console.log(decoded.idPais);
// console.log(decoded.idTienda);