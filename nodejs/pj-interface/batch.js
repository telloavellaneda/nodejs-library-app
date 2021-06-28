// ------------------ MAIN Logger Setup ---------------------
// MUST BE HERE BECAUSE I NEED TO SET UP WHAT THE FILENAME IS
const { idTienda } = require('./_common/validate-token').execute();
process.env.WINSTON_FILENAME = `batch_${ idTienda }-%DATE%.log`;
require('./_common/logger');
// ------------------ MAIN Logger Setup ---------------------

process.env.EXEC_SECONDS = 3;

require('./batch/services/timer').startup(process.argv);