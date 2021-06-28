const createConnection = require('./db/create-connection');
const logger = require('../_common/logger');

const fs = require('fs');

async function execute(flag) {

    const uploadsDir = './uploads';
    if (fs.readdirSync(uploadsDir).length == 0)
        return;
    
    const connection = await createConnection.open();
    try {
        // Upsert Data
        await require('./services/upsert-data').execute(connection, flag);

    } catch (error) {
        console.log(error)
        logger.error(JSON.stringify(error));
    }
    await createConnection.close(connection);


    // Move Data
    await require('./db/copy-objects').execute(flag);
    
    logger.info(`[Load Data] DBF Loader is listening [env=${ process.env.NODE_ENV }]`);
}

module.exports.execute = execute;
