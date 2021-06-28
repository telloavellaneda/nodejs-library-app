const logger = require('../../_common/logger');
const readCSV = require('./read');
const moment = require('moment');
const { v4: uuidv4 } = require('uuid');

module.exports.execute = async (args) => {

    const { connection, table, tiendas, action } = args;
    const fields = table.fields[1];

    var values;
    var wrongValues;
    var fileName;
    var tmpCSV;
    var csv;

    const formats = [
        moment.ISO_8601,
        'DD-MM-YYYY',
        'D-M-YYYY'
    ];

    fileName = `_init/_tmp/${ action }_CR_PBR.csv`;
    try {
        tmpCSV = await readCSV.execute(fileName, fields);
        logger.info(`[${ action }] Reading file [${ fileName }, ${ tmpCSV.length } records]`);
        csv = tmpCSV;
    } catch (error) {
        //
    }

    fileName = `_init/_tmp/${ action }_PA_PBR.csv`;
    try {
        tmpCSV = await readCSV.execute(fileName, fields);
        logger.info(`[${ action }] Reading file [${ fileName }, ${ tmpCSV.length } records]`);
        csv = csv.concat(tmpCSV);
    } catch (error) {
        //
    }

    values = [];
    wrongValues = [];
    for (var i in csv) {
        const element = csv[i];

        if (element.FORECAST)
            element.VALUE = element.FORECAST.replace(/,/g, ".");
        else if (element.BUDGET)
            element.VALUE = element.BUDGET.replace(/,/g, ".");

        if (!moment(element.DOB, formats, true).isValid() || 
                isNaN(element.CODIGOINTERNO) || 
                isNaN(element.VALUE) ||
                isNaN(element.CODE)) {
            wrongValues.push(element);
            continue;
        }

        const tmp = tiendas.find( innerElement => (innerElement.COUNTRY.replace(/[àáâãäå]/g,"a") == element.COUNTRY && innerElement.CODIGOINTERNO == element.CODIGOINTERNO));
        if (tmp && tmp.ID_TIENDA)
            element.ID_TIENDA = tmp.ID_TIENDA;
        else
            element.ID_TIENDA = 0;

        values.push([
            uuidv4().toString(),
            element.ID_TIENDA,
            element.COUNTRY,
            element.DOB,
            element.CODIGOINTERNO,
            element.VALUE,
            element.CODE
        ]);
    }

    logger.info(`[${ action }] Records [ Total ]: [${ csv.length }]`)
    logger.info(`[${ action }] Records [Correct]: [${ values.length }]`)
    logger.info(`[${ action }] Records [ Wrong ]: [${ wrongValues.length }]`)

    try {
        if ( wrongValues.length > 0) {
            const header = ['ID_TIENDA','COUNTRY, DOB, CODIGOINTERNO, VALUE, CODE'];
            const rows = wrongValues.map(row =>
                `"${ row.ID_TIENDA }","${ row.COUNTRY }","${ row.DOB }","${ row.CODIGOINTERNO }","${ row.VALUE }","${ row.CODE }"`
            );
            const data = header.concat(rows).join("\n");
    
            require('fs').writeFile(`./${ action }_error.csv`, data, err => {
                if (err) console.log('Error writing to csv file', err);
            });
        }    
    } catch (error) {
        console.log(error)
    }

    try {
        await connection.execute(`DROP TABLE T_${ action }`);
    } catch (error) {
        //
    }

    const dbUtils = require('../db/db-utils');
    const tableName = await require('../db/create-utils').create(connection, table);
    await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON ${ tableName } (F_STORE)`);
    await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON ${ tableName } (F_COUNTRY)`);
    await connection.execute(`ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY'`);

    logger.info(`[${ action }] Records: [${ values.length }]`)
    result = await connection.executeMany(`INSERT INTO T_${ action } VALUES (:1, :2, :3, :4, :5, :6, :7)`, values, { autoCommit: true });
    logger.info(`[${ action }] Records: [${ values.length }] inserted.`)

}