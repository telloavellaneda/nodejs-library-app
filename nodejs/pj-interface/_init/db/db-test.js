const dotenv = require('dotenv');
dotenv.config({ path: './api/config/config.env' });

const createConnection = require('./create-connection');

test();

async function test() {
    const connection = await createConnection.open();

    createConnection.close(connection);
}