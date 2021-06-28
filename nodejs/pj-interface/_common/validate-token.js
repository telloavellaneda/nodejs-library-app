const fs = require('fs');
const jwt = require('jsonwebtoken');
const dec = require('./encrypt-decrypt');

var token = '';

function validateToken(abc) {

    if (!abc)
        try {
            token = fs.readFileSync('./token.key', 'utf8');
        } catch (err) {
            throw Error(`JWT was not found!`);
        }
    else
        token = abc;

    try {
        return jwt.verify(token.trim(), dec.decrypt('b4e257b84bae65efd322cee096364e35'));   
    } catch (err) {
        throw Error(`Please submit a valid JWT token`);
    }
}

function getToken() {
    return token;
}

module.exports.execute = validateToken;
module.exports.getToken = getToken;