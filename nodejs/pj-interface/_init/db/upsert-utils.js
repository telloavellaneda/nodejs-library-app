const dbUtils = require('./db-utils');
const logger = require('../../_common/logger');

module.exports.upsert = upsert;

async function upsert(connection, table) {
    var result;

    const tableName = dbUtils.setTableName(table.name);    
    const fields = ([ 'UUID' ]).concat(table.fields[0].concat(table.fields[1]));
    const values = dbUtils.setValues(table.fields, table.values, tableName);


    // DELETE Conditions
    const conditions = table.fields[0].map( ( element ) => {
        if (element.name == 'FECHA')
            return `TO_CHAR(${ element.name },'YYYY-MM-DD')='${ element.value }'`;
        else
            return element.name + '=' + element.value;
    }).join(' AND ');

    const deleteStatement = `DELETE ${ tableName } WHERE ${ conditions }`;
    const insertStatement = `INSERT INTO ${ tableName } values (${ dbUtils.getBinds(fields) })`;

    try {
        result = {
            rowsAffected: 0
        };
        result = await connection.execute(deleteStatement);
        await connection.executeMany(insertStatement, values, { autoCommit: true });

        return {
            flag: true,
            message: `Inserted=${ pad(values.length) }, Deleted=${ pad(result.rowsAffected) }`
        };

    } catch (error) {
        return {
            flag: false,
            message: `Records=${ pad(0) }, Message=${ error.message }, Error=${ error }`
        };
    }
}

function pad(num) {
    const spaces = 5
    try {
        return parseInt(num).toString().padStart(spaces,'0');
    } catch (error) {
        return parseInt(0).toString().padStart(spaces,'0');
    }
}