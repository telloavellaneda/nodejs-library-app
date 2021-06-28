const createConnection = require('../db/create-connection');

execute();

async function execute() {

    require('dotenv').config({ path: './api/config/config.env' });

    const connection = await createConnection.open();
    await require('./create-mv-new').execute(connection);
    await createConnection.close(connection);

    await require('../db/copy-objects').execute(true);
}