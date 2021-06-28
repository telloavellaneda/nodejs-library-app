// ------------------ MAIN Logger Setup ---------------------
// MUST BE HERE BECAUSE I NEED TO SET UP WHAT THE FILENAME IS
process.env.WINSTON_FILENAME = 'test-load-data-%DATE%.log';
require('../../_common/logger');
// ------------------ MAIN Logger Setup ---------------------

require('dotenv').config({ path: './api/config/config.env' });

startup();

async function startup() {
    await require('../load-data').execute(false);
}