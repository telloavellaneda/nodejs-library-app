const validateToken = require('../../_common/validate-token');
const logger = require('../../_common/logger');

module.exports.execute = upload;

function upload(req, res, err) {
    var result;

    const tmp = ( req.body.idTienda ) ? req.body.idTienda : 'undefined';
    const base = `[idTienda=${ tmp.toString().padStart(4,'0') }]`;

    try {
        if (!req.file) 
            throw new Error('No file received');
        else if (req.fileValidationError)
            throw new Error('Only DBF files are allowed');
    } catch (error) {
        logger.error(`${ base } ${ error.message }`);
        return res.status(400).send({ status: 400, message: error.message });
    }

    try {
        if (!req.body.token)
            throw new Error('Invalid Token');
        result = validateToken.execute(req.body.token);
    } catch (error) {
        logger.error(`${ base } ${ error.message } (${ req.file.filename })`);
        return res.status(401).send({ status: 401, message: error.message });
    }
    
    logger.info(`${ base } ${ req.file.filename }`);

    res.send({ 
        idTienda: result.idTienda,
        message: 'File updloaded successfully',
        filename: req.file.filename
    });
}