// ------------------ MAIN Logger Setup ---------------------
// MUST BE HERE BECAUSE I NEED TO SET UP WHAT THE FILENAME IS
process.env.WINSTON_FILENAME = 'main-setup-%DATE%.log';
const logger = require('../../_common/logger');
// ------------------ MAIN Logger Setup ---------------------
const dotenv = require('dotenv');
dotenv.config({ path: './api/config/config.env' });

const createConnection = require('../db/create-connection');

const files = [{
    keys: [
        { name: 'ID_TIENDA', type: 'PK-NUMBER' }
    ],
    list: [
        { name: 'CAT.DBF', indexes: [ 'ID_TIENDA', 'F_ID' ] },
        { name: 'ODR.DBF', indexes: [ 'ID_TIENDA', 'F_ID' ] },
        { name: 'PRO.DBF' },
        { name: 'CIT.DBF', indexes: [ 'ID_TIENDA', 'F_CATEGORY' ] },
        { name: 'EMP.DBF' },
        { name: 'CMP.DBF' },
        { name: 'RSN.DBF' },
        { name: 'ITM.DBF', indexes: [ 'ID_TIENDA', 'F_ID' ] },
        { name: 'TDR.DBF' },
    ]}, {
    keys:[
        { name: 'ID_TIENDA', type: 'PK-NUMBER' },
        { name: 'FECHA', type: 'PK-DATE' }
    ],
    list: [
        { name: 'GNDSALE.Dbf', indexes: [ 'ID_TIENDA', 'F_CHECK' ] },
        { name: 'GNDITEM.Dbf', indexes: [ 'ID_TIENDA', 'F_ITEM' ] },
        { name: 'GNDLINE.Dbf', },
        { name: 'GNDTndr.dbf', },
        { name: 'GNDTurn.dbf', },
        { name: 'GNDVoid.dbf', },
    ]
}];

execute(files);

async function execute(files) {
    const connection = await createConnection.open();

    try {
        // // 0. Delete Objects
        // await require('../db/delete-utils').drop(connection);

        // // 1. Create file tables
        await require('../services/create-tables').execute(connection, files);

        // // 2. Create+Load Additional Data
        await require('../csv-services/load').execute(connection);

        // // 3. Create Internal tables
        // // await require('../services/create-tx').create(connection);

    } catch (error) {
        console.log(error)
        // logger.error(JSON.stringify(error));
    }

    createConnection.close(connection);
}