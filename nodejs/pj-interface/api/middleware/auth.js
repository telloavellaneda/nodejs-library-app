var jwt = require('jsonwebtoken');

function auth(req, res, next) {

    if (!req.headers || !req.headers.token)
        return res.send(`Invalid Token`);

    var decode;
    try {
        decode = jwt.verify(req.headers.token, process.env.JWT_SECRET);
    } catch (err) {
        return res.send(`Invalid Token`);
    }
    req.info = decode;

    next();
}

module.exports = auth;