// ------------------ MAIN Logger Setup ---------------------
// MUST BE HERE BECAUSE I NEED TO SET UP WHAT THE FILENAME IS
process.env.WINSTON_FILENAME = 'csv-loader-%DATE%.log';
const logger = require('../../_common/logger');
// ------------------ MAIN Logger Setup ---------------------
const dotenv = require('dotenv');
dotenv.config({ path: './api/config/config.env' });

const createConnection = require('../db/create-connection');

load();

module.exports.execute = load;

async function load(tmp) {

    var tables = [];

    tables.push({
        name: 'TIENDAS.',
        fields: [
            [
                { name: 'ID_TIENDA', type: 'PK-NUMBER' },
            ],
            [
                { name: 'CODIGOINTERNO', type: 'N' },
                { name: 'COUNTRY', type: 'C', size: 75, decimalPlaces: 0 },
                { name: 'UNIT', type: 'N' },
                { name: 'STORE', type: 'C', size: 75, decimalPlaces: 0 },
                { name: 'APERTURA', type: 'D' },
                { name: 'CIERRE', type: 'D' },
                { name: 'LATITUD', type: 'N' },
                { name: 'LONGITUD', type: 'N' },
                { name: 'ZONA', type: 'C', size: 75, decimalPlaces: 0 },
                { name: 'CODIGOMAM', type: 'N' },
            ]
        ]
    });

    tables.push({
        name: 'FORECAST.',
        fields: [
            [
                { name: 'ID_TIENDA', type: 'PK-NUMBER' },
            ],
            [
                { name: 'COUNTRY', type: 'C', size: 50, decimalPlaces: 0 },
                { name: 'DOB', type: 'D' },
                { name: 'STORE', type: 'N' },
                { name: 'FORECAST', type: 'N' },
                { name: 'CODE', type: 'N' }
            ]
        ]
    });

    tables.push({
        name: 'BUDGET.',
        fields: [
            [
                { name: 'ID_TIENDA', type: 'PK-NUMBER' },
            ],
            [
                { name: 'COUNTRY', type: 'C', size: 50, decimalPlaces: 0 },
                { name: 'DOB', type: 'D' },
                { name: 'STORE', type: 'N' },
                { name: 'FORECAST', type: 'N' },
                { name: 'CODE', type: 'N' }
            ]
        ]
    });

    tables.push({
        name: 'MAM.',
        fields: [
            [],
            [
                { name: 'COUNTRY', type: 'C', size: 75, decimalPlaces: 0 },
                { name: 'CODIGOMAM', type: 'N' },
                { name: 'MAM', type: 'C', size: 100, decimalPlaces: 0 },
            ]
        ]
    });

    tables.push({
        name: 'CHANNEL.',
        fields: [
            [],
            [
                { name: 'COUNTRY', type: 'C', size: 75, decimalPlaces: 0 },
                { name: 'CODIGO', type: 'N' },
                { name: 'ORDERMODE', type: 'N' },
                { name: 'CHANNEL', type: 'C', size: 100, decimalPlaces: 0 },
                { name: 'DESCRIPCION', type: 'C', size: 100, decimalPlaces: 0 },
            ]
        ]
    });

    var connection;

    if (tmp)
        connection = tmp;
    else
        connection = await createConnection.open();

    try {
        // Tiendas
        const tiendas = await require('./stores').execute(connection, tables[0], false);

        // MAM
        await require('./mam').execute(connection, tables[3]);

        // Forecast
        await require('./forecast').execute({
            connection: connection, 
            table: tables[1], 
            tiendas: tiendas,
            action: 'Forecast'
        });

        // Budget
        await require('./forecast').execute({
            connection: connection, 
            table: tables[2], 
            tiendas: tiendas,
            action: 'Budget'
        });

        // Channel
        await require('./channel').execute({
            connection: connection, 
            table: tables[4], 
            tiendas: tiendas
        });

    } catch (error) {
        console.log(error)
        logger.error(JSON.stringify(error));
    }

    if (!tmp)
        await createConnection.close(connection);
}