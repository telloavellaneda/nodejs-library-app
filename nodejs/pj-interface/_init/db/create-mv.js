const logger = require('../../_common/logger');

module.exports.execute = async (connection) => {

    const mvArray = [{ 
            name : 'MV_SALES_X_CH', 
            query: `
                SELECT A.F_ID, A.F_NAME, A.ID_TIENDA, B.FECHA, B.F_DISCPRIC, B.F_INCLTAX, B.F_CHECK
                FROM T_ODR A, T_GNDITEM B
                WHERE A.F_ID = B.F_MODE
                AND A.ID_TIENDA = B.ID_TIENDA
        `}, {
            name : 'MV_1_PRODUCT_PRICE_SALES', 
            query: `
                SELECT I.F_ID, I.F_SHORTNAME, I.ID_TIENDA, Z.F_DISCPRIC, Z.F_INCLTAX, Z.F_CHECK, Z.FECHA
                FROM T_ITM I, T_GNDITEM Z
                WHERE Z.F_ITEM = I.F_ID
                AND Z.ID_TIENDA = I.ID_TIENDA
        `}, {
            name : 'MV_1_SALES_CATEGORY', 
            query: `
                SELECT Z.F_DISCPRIC, Z.F_INCLTAX, Z.F_CHECK, Z.FECHA, C.ID_TIENDA, C.F_NAME
                FROM T_GNDITEM Z, T_CAT C
                WHERE Z.F_CATEGORY = C.F_ID
                AND Z.ID_TIENDA = C.ID_TIENDA
        `}, {
            name : 'T_CHECK', 
            query: `SELECT DISTINCT ID_TIENDA, FECHA, F_HOUR, F_CHECK FROM T_GNDITEM`
        }, {
            name : 'T_DOB', 
            query: `SELECT DISTINCT FECHA FROM T_CHECK`
        }, {
            name : 'T_HOUR', 
            query: `SELECT DISTINCT FECHA, F_HOUR FROM T_CHECK`
        }
    ];

    for (var i in mvArray) {

        logger.info(`[${ mvArray[i].name }] Creating...`);
        try {
            await connection.execute(`DROP MATERIALIZED VIEW ${ mvArray[i].name }`);
        } catch (error) {
            //
        }
        await connection.execute(`CREATE MATERIALIZED VIEW ${ mvArray[i].name } TABLESPACE ${ process.env.DB_TS_EXT }
            NOLOGGING CACHE BUILD IMMEDIATE REFRESH ON DEMAND AS 
            ${ mvArray[i].query }`
        );	
        logger.info(`[${ mvArray[i].name }] Created.`);

    }
}