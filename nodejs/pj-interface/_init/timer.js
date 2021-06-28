// ------------------ MAIN Logger Setup ---------------------
// MUST BE HERE BECAUSE I NEED TO SET UP WHAT THE FILENAME IS
process.env.WINSTON_FILENAME = 'load-data-%DATE%.log';
const logger = require('../_common/logger');
// ------------------ MAIN Logger Setup ---------------------

require('dotenv').config({ path: './api/config/config.env' });

var execInterval;
process.env.EXEC_SECONDS = 30;
process.env.NODE_ENV = 'prod';

startup();

async function startup() {
    logger.info(`[Load Data] DBF Loader is listening [env=${ process.env.NODE_ENV }]`);
    execInterval = setInterval(
        execute,
        parseInt(process.env.EXEC_SECONDS) * 1000);
} 

async function execute() {
    // console.log('Clear Interval');
    clearInterval(execInterval);

    // console.log('Load data');
    await require('./load-data').execute(true);

    // console.log('Interval Set');
    execInterval = setInterval(
        execute,
        parseInt(process.env.EXEC_SECONDS) * 1000
    );
}

module.exports.startup = startup;