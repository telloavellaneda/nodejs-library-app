const logger = require('../../_common/logger');
const readCSV = require('./read');
const { v4: uuidv4 } = require('uuid');

module.exports.execute = async (args) => {

    const { connection, table, tiendas } = args;
    const fields = table.fields[1];
    const action = 'Channel';

    var values;
    var tmpCSV;
    var csv;
    var fileName;
    var isCR = false;
    var isPA = false;

    fileName = `_init/_tmp/${ action }_CR_PBR.csv`;
    try {
        tmpCSV = await readCSV.execute(fileName, fields);
        logger.info(`[${ action }] Reading file [${ fileName }, ${ tmpCSV.length } records]`);
        csv = tmpCSV;
    } catch (error) {
        isCR = false;
    }

    fileName = `_init/_tmp/${ action }_PA_PBR.csv`;
    try {
        tmpCSV = await readCSV.execute(fileName, fields);
        logger.info(`[${ action }] Reading file [${ fileName }, ${ tmpCSV.length } records]`);
        csv = csv.concat(tmpCSV);
    } catch (error) {
        isPA = false;
    }

    csv.map(element => {
        const tmp = tiendas.find( innerElement => (
            innerElement.COUNTRY.replace(/[àáâãäå]/g,"a") == element.COUNTRY && innerElement.CODIGOINTERNO == element.CODIGOINTERNO
        ));
        if (tmp)
            element.CODIGOINTERNO = tmp.ID_TIENDA;
        else
            element.CODIGOINTERNO = 0;
    })

    values = [];
    for (var i in csv) {
        values.push([
            uuidv4().toString(),
            csv[i].COUNTRY,
            csv[i].CODIGO,
            csv[i].ORDERMODE,
            csv[i].CHANNEL,
            csv[i].DESCRIPCION
        ]);
    }

    try {
        await connection.execute(`DROP TABLE T_${ action }`);
    } catch (error) {
        //
    }
    const dbUtils = require('../db/db-utils');
    const tableName = await require('../db/create-utils').create(connection, table);
    await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON ${ tableName } (F_COUNTRY)`);
    await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON ${ tableName } (F_CODIGO)`);

    logger.info(`[${ action }] Records: [${ values.length }]`)
    await connection.execute(`ALTER SESSION SET NLS_DATE_FORMAT = 'DD/MM/YYYY'`);
    result = await connection.executeMany(`INSERT INTO T_${ action } VALUES (:1, :2, :3, :4, :5, :6)`, values, { autoCommit: true });
    logger.info(`[${ action }] Records: [${ values.length }] inserted.`)
}