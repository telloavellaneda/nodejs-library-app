const logger = require('../../_common/logger');
const FormData = require('form-data');
const fs = require('fs');

module.exports.execute = submitForm; 

function submitForm(carga) {
    return new Promise( (resolve) => {
        const { token, idTienda, file } = carga;

        var form = new FormData();
        form.append('idTienda', idTienda);
        form.append('token', token);
        form.append('file', fs.createReadStream(file));

        form.submit(process.env.API_UPLOAD, (err, res) => {

            const code = (res) ? res.statusCode : '-1';
            const message = (res) ? res.statusMessage : 'API is not available' ;

            resolve({
                code: code,
                message: message,
                err: err
            });
        });

        // Testing Flow
        // DO NOT DELETE IT
        // console.log(process.env.API_UPLOAD)
        // resolve({
        //     code: '400',
        //     message: 'Testing',
        //     //err: null
        // });

    });
}