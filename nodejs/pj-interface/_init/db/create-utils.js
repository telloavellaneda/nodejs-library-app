const dbUtils = require('./db-utils');
const logger = require('../../_common/logger');

module.exports.create = create;

async function create (connection, table) {
    const uuid = [
        { name: 'UUID', type: 'PK-VARCHAR', size: 36 }
    ];

    const tableName = dbUtils.setTableName(table.name);
    const keyFields = uuid.concat(table.fields[0]);
    const fields = keyFields.concat(table.fields[1]);

    var tablespace = '';
    if ( tableName == 'T_CIT' || tableName == 'T_GNDSALE' || tableName == 'T_GNDITEM' )
        tablespace = `TABLESPACE ${ process.env.DB_TS_EXT }`;

    const create = `CREATE TABLE ${ tableName } (${ dbUtils.getFieldsWithType(fields) }) ${ tablespace }`;
    result = await connection.execute(create);
    logger.info(`Table [${ tableName }] created.`);

    for (var i in keyFields)
        await connection.execute(`ALTER TABLE ${ tableName } MODIFY (${ keyFields[i].name } NOT NULL ENABLE)`);    

    const constraintName = dbUtils.getConstraintName();
    const tmp = dbUtils.joinKeyFields(keyFields);
    await connection.execute(`CREATE UNIQUE INDEX ${ constraintName } ON ${ tableName } (${ tmp })`);
    await connection.execute(`ALTER TABLE ${ tableName } ADD CONSTRAINT ${ constraintName } PRIMARY KEY (${ tmp }) ENABLE`);

    // On try-catch statement for those cases which not apply (Stores, Forecast/Budget, MAM)
    try {
        // Index on key fields without UUID
        await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON ${ tableName } (${ dbUtils.joinKeyFields(table.fields[0]) })`);
    } catch (error) {
        //
    }

    try {
        // Indexes on additional fields
        await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON ${ tableName } (${ table.indexes.join(', ') })`);
    } catch (error) {
        //
    }

    return tableName;
}