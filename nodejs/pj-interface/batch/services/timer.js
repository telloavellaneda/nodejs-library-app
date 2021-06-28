const logger = require('../../_common/logger');
const timerUtils = require('../../_common/timer-utils');
const readEnvironment = require('./read-environment');
const main = require('./main');

var i = 1;
var execInterval;
var batchLabel;

async function startup(options) {

    if (!process.env.NODE_ENV)
        process.env.NODE_ENV = 'dev';

    var dbfDir = readEnvironment.searchDbfDir();
    batchLabel = `[env=${ process.env.NODE_ENV }] [dir=${ dbfDir }]`;

    if (options.length == 2) {
        logger.info(`Batch started as daemon ${ batchLabel }`);
        execInterval = setInterval(execute, parseInt(process.env.EXEC_SECONDS) * 1000);
        
    } else {
        logger.info(`Batch started now ${ batchLabel }`);
        await main.execute();
        logger.info(`Batch finished now ${ batchLabel }`);
    }
} 

async function execute() {
    readEnvironment.get();
    const { execNow, initLoad, time } = global.env;
    const flagDate = timerUtils.getFlagDate(time);

    if ( execNow || initLoad || flagDate ) {
        logger.info(`Batch started now [times=${ i++ }] ${ batchLabel }`);

        // console.log('Clear Interval');
        clearInterval(execInterval);

        // Decouple function for unit-testing purposes
        await main.execute();

        // Wait a while to avoid triggering this process twice
        await timerUtils.wait(process.env.EXEC_SECONDS);

        // console.log('Interval Set');
        execInterval = setInterval(
            execute,
            parseInt(process.env.EXEC_SECONDS) * 1000
        );

        logger.info(`Batch started as daemon ${ batchLabel }`);
    }
}

module.exports.startup = startup;