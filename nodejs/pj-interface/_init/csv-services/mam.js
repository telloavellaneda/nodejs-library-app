const logger = require('../../_common/logger');
const readCSV = require('./read');
const { v4: uuidv4 } = require('uuid');

module.exports.execute = async (connection, table) => {

    var result;
    var values;
    var tmpCSV;
    var csv;
    var fileName;

    // Forecast CSV Reading
    fileName = '_init/_tmp/MAM_CR_PBR.csv';
    try {
        tmpCSV = await readCSV.execute(fileName, table.fields[1]);
        logger.info(`[MAM] Reading file [${ fileName }, ${ tmpCSV.length } records]`);
        csv = tmpCSV;
    } catch (error) {
        //
    }

    fileName = '_init/_tmp/MAM_PA_PBR.csv';
    try {
        tmpCSV = await readCSV.execute(fileName, table.fields[1]);
        logger.info(`[MAM] Reading file [${ fileName }, ${ tmpCSV.length } records]`);
        csv = csv.concat(tmpCSV);
    } catch (error) {
        //
    }

    values = [];
    for (var i in csv) 
        values.push([
            uuidv4().toString(),
            csv[i].COUNTRY,
            csv[i].CODIGOMAM,
            csv[i].MAM,
        ]);


    try {
        await connection.execute(`DROP TABLE T_MAM`);
    } catch (error) {
        //
    }
    const dbUtils = require('../db/db-utils');
    const tableName = await require('../db/create-utils').create(connection, table);
    // await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON ${ tableName } (F_COUNTRY)`);

    logger.info(`[MAM] Records: [${ values.length }]`)
    result = await connection.executeMany(`INSERT INTO T_MAM VALUES (:1, :2, :3, :4)`, values, { autoCommit: true });
    logger.info(`[MAM] Records: [${ values.length }] inserted.`)
}