const logger = require('../../_common/logger');

module.exports.drop = drop;

async function drop (connection) {
    
    const plsql = `BEGIN
        FOR x IN (
            SELECT table_name tabla
            FROM all_tables
            WHERE owner='PJUSER'
        )
        LOOP
            BEGIN
                EXECUTE IMMEDIATE 'DROP TABLE ' || x.tabla;
            EXCEPTION
                WHEN OTHERS THEN
                    EXECUTE IMMEDIATE 'DROP MATERIALIZED VIEW ' || x.tabla;
                    DBMS_OUTPUT.PUT_LINE('NONE');
            END;
        END LOOP;
        commit;
        END;`;
    const result = await connection.execute(plsql);
    logger.warn('All tables were dropped!');
}