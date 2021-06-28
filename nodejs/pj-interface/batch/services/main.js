const logger = require('../../_common/logger');
const validateToken = require('../../_common/validate-token');
const getFileList = require('./get-filelist');
const submitForm = require('./submit-form');
const fs = require('fs');

module.exports.execute = main;

async function main() {
    fs.writeFileSync('./exec-now.txt', 'NO');

    const tienda = validateToken.execute();

    const list2process = getFileList.execute(tienda);

    if (list2process.length == 0) {
        logger.info(`[idTienda=${ tienda.idTienda }] No files found.`);
        return;
    }

    const tag = `[idTienda=${ tienda.idTienda }] Files ${ list2process.length } files`;
    logger.info(tag + ' START');

    for (var i in list2process) {
        const { directory, original, replaced } = list2process[i];

        const count = (parseInt(i)+1).toString().padStart(4,'0');
        const total = list2process.length.toString().padStart(4,'0');
        const base = `${ count + '/' + total } (${ list2process[i].replaced })`;

        fs.copyFileSync(directory + '/' + original, './workdir/' + replaced);

        const { code, message, err } = await submitForm.execute({
            token: validateToken.getToken(),
            idTienda: tienda.idTienda,
            file: './workdir/' + replaced
        });

        fs.unlinkSync('./workdir/' + replaced);

        if (err)
            logger.error(`${ base } ${ JSON.stringify(err) }`);
        else if (code != 200)
            logger.error(`${ base } ${ code + "-" + message }`);
        else 
            logger.info(`${ base }`);    
    }

    logger.info(tag + ' END');
}