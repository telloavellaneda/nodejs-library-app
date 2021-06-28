const logger = require('../../_common/logger');
const readCSV = require('./read');
const { v4: uuidv4 } = require('uuid');

module.exports.execute = async (connection, table, create) => {
    var result;
    var tmpCSV;
    var fileName;
    var fields = table.fields[1];

    // Tiendas CSV Reading
    fileName = '_init/_tmp/Tiendas_CR_PBR.csv';
    try {
        tmpCSV = await readCSV.execute(fileName, fields);
        logger.info(`[Tiendas] Reading file [${ fileName }, ${ tmpCSV.length } records]`);
        result = tmpCSV;
    } catch (error) {
        //
    }

    fileName = '_init/_tmp/Tiendas_PA_PBR.csv';
    try {
        tmpCSV = await readCSV.execute(fileName, fields);
        logger.info(`[Tiendas] Reading file [${ fileName }, ${ tmpCSV.length } records]`);
        result = result.concat(tmpCSV);
    } catch (error) {
        //
    }

    values = [];
    for (var i in result) {

        var rowValue = [];
        // PK Values
        rowValue.push(uuidv4().toString());
        rowValue.push( parseInt(i)+1 );

        for ( var j in fields )
            rowValue.push( result[i][ fields[j].name ] );

        values.push(rowValue)
    }

    // console.log(values)

    if (create) {
        try {
            await connection.execute(`DROP TABLE T_TIENDAS`);
        } catch (error) {
            //
        }
        const dbUtils = require('../db/db-utils');
        const tableName = await require('../db/create-utils').create(connection, table);
        await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON ${ tableName } (ID_TIENDA, F_CODIGOINTERNO)`);
        await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON ${ tableName } (F_CODIGOINTERNO)`);
        await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON ${ tableName } (F_COUNTRY)`);
        await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON ${ tableName } (F_STORE)`);
    
        await connection.execute(`ALTER SESSION SET NLS_DATE_FORMAT = 'YYYYMMDD'`);
        await connection.executeMany(`INSERT INTO T_TIENDAS VALUES (:1, :2, :3, :4, :5, :6, :7, :8, :9, :10, :11, :12)`, values, { autoCommit: true });    
    }
    
    var tiendas = await connection.execute(`SELECT * FROM T_TIENDAS`);

    result.map(element => {
        const tmp = tiendas.rows.find(innerElement => (innerElement[3] == element.COUNTRY && innerElement[5] == element.STORE));
        if (tmp)
            element.ID_TIENDA = tmp[1];
    });

    logger.info(`[Tiendas] Records [  Total  ]: [${ result.length }]`);

    return result;
}