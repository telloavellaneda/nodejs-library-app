const dbUtils = require('../db/db-utils');
const logger = require('../../_common/logger');

module.exports.create = create;

async function create (connection) {
    var sql; 
    
    sql = `CREATE TABLE INT_TRANSACCIONES
        (	
            "ID_TX" VARCHAR2(50),
            "ID_TIENDA" NUMBER, 
            "FECHA" DATE,
            "INICIO" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            "FIN" TIMESTAMP,
            "NUM_ARCHIVOS" NUMBER,
            "COMENTARIOS" VARCHAR2(200),
            "STATUS" NUMBER DEFAULT 0
        )
    `;
    await connection.execute(sql);

    const tmpName = dbUtils.getConstraintName();
    await connection.execute(`CREATE UNIQUE INDEX ${ tmpName } ON INT_TRANSACCIONES (ID_TX)`);
    await connection.execute(`ALTER TABLE INT_TRANSACCIONES ADD CONSTRAINT ${ tmpName } PRIMARY KEY (ID_TX) ENABLE`);

    await connection.execute(`CREATE INDEX ${ dbUtils.getConstraintName() } ON INT_TRANSACCIONES (ID_TIENDA, FECHA)`);

    await connection.execute(`ALTER TABLE INT_TRANSACCIONES MODIFY (ID_TX NOT NULL ENABLE)`);
    await connection.execute(`ALTER TABLE INT_TRANSACCIONES MODIFY (ID_TIENDA NOT NULL ENABLE)`);
    await connection.execute(`ALTER TABLE INT_TRANSACCIONES MODIFY (FECHA NOT NULL ENABLE)`);

    logger.info(`Table [INT_TRANSACCIONES] created.`);
}