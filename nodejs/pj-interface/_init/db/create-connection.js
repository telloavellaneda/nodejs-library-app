const oracledb = require('oracledb');
const logger = require('../../_common/logger');

async function open() {

    try {
        await oracledb.createPool({
            poolAlias: process.env.DB_POOL_ALIAS,
            user: process.env.DB_USER,
            password: process.env.DB_PASSWORD,
            connectString: process.env.DB_URL
        });
    } catch (error) {

    }

    var connection = await oracledb.getConnection({ poolAlias: process.env.DB_POOL_ALIAS });
    logger.info(`Connected to Oracle ${ process.env.DB_URL }`);
    return connection;
}

async function close(connection) {
    await connection.close();
    logger.info(`Connection Closed ${ process.env.DB_URL }`);
}

module.exports.open = open;
module.exports.close = close;