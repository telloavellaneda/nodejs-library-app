const logger = require('../../_common/logger');

module.exports.execute = async (connection) => {

    const mvArray = [{ 
            name : 'MV_GNDITEM', 
            query: `
                SELECT  
                Z.ID_TIENDA,
                Z.FECHA,
                TO_CHAR(z.FECHA,'DD-MM-YYYY') ||'-'|| z.F_HOUR as codhrs,
                Z.F_CHECK,
                Z.F_DISCPRIC, Z.F_INCLTAX, z.F_MODE, z.F_CATEGORY, z.F_ITEM, z.F_PRICE, Z.F_ENTRYID,
                C.F_NAME CAT_NAME, 
                A.F_ID ID_ODR, A.F_NAME ODR_NAME,
                I.F_ID ID_ITM, I.F_LONGNAME ITM_LONGNAME,
                E.F_ID EMP_ID
                FROM 
                T_GNDITEM Z,
                T_CAT C,
                T_ODR A,
                T_ITM I
                ,T_EMP E
                WHERE 1=1
                and Z.F_CATEGORY = C.F_ID
                AND Z.ID_TIENDA = C.ID_TIENDA
                and A.F_ID = Z.F_MODE
                AND A.ID_TIENDA = Z.ID_TIENDA
                and Z.F_ITEM = I.F_ID
                AND Z.ID_TIENDA = I.ID_TIENDA
                AND Z.ID_TIENDA = E.ID_TIENDA(+)
                AND Z.F_EMPLOYEE = E.F_ID(+)
            `}, {
            name : 'MV_GNDITEM_SUM', 
            query: `
                select
                t.F_STORE,
                t.f_unit,
                z.ID_TIENDA, 
                z.FECHA , 
                TO_CHAR(z.FECHA,'DD-MM-YYYY') || '&' || z.ID_TIENDA as codbud,
                sum(z.F_DISCPRIC), 
                sum(z.F_INCLTAX)
                from T_TIENDAS t, T_GNDITEM z 
                where z.ID_TIENDA = t.ID_TIENDA 
                group by 
                t.F_STORE, t.f_unit, z.ID_TIENDA, z.FECHA, TO_CHAR(z.FECHA,'DD-MM-YYYY') || '&' || z.ID_TIENDA
            `}, {
            name : 'MV_GNDITEM_GNDLINE', 
            query: `
                select
                    mv.ID_TIENDA, mv.FECHA, 
                    mv.F_DISCPRIC, mv.F_INCLTAX, 
                    mv.ODR_NAME CANAL, mv.CAT_NAME Categoria,
                    mv.ITM_LONGNAME item, 
                    mv.CODHRS, mv.F_CHECK, 
                    l.F_TYPE, l.F_TYPEID, 
                    mv.ID_TIENDA || '-' || mv.F_CHECK || '-' || mv.CODHRS codcheck
                from MV_GNDITEM mv, 
                    T_GNDLINE l
                where mv.ID_TIENDA = l.ID_TIENDA 
                    and mv.FECHA = l.FECHA 
                    and mv.F_CHECK = l.F_CHECKID 
                    and mv.F_ITEM = l.F_ITEMID 
                    and mv.F_CATEGORY = l.F_CATID
                    and mv.f_entryid = l.f_entryid
            `}, {
            name : 'MV_FORECAST_BUDGET', 
            query: `
                SELECT A.*,
                    B.F_FORECAST
                    ,C.F_FORECAST F_BUDGET
                FROM (
                    SELECT ID_TIENDA, F_COUNTRY, F_DOB, F_STORE, F_CODE FROM T_FORECAST
                    UNION
                    SELECT ID_TIENDA, F_COUNTRY, F_DOB, F_STORE, F_CODE FROM T_BUDGET
                ) A,
                T_FORECAST B
                ,T_BUDGET C
                WHERE 1=1
                    AND A.F_COUNTRY = B.F_COUNTRY(+)
                    AND A.F_DOB = B.F_DOB(+)
                    AND A.F_STORE = B.F_STORE(+)
                    AND A.F_CODE = B.F_CODE(+)
                    AND A.F_COUNTRY = C.F_COUNTRY(+)
                    AND A.F_DOB = C.F_DOB(+)
                    AND A.F_STORE = C.F_STORE(+)
                    AND A.F_CODE = C.F_CODE(+)
            `}, {
            name : 'MV_GNDVOID', 
            query: `
                select 
                v.FECHA, v.F_CHECK, v.ID_TIENDA, 
                e.F_FIRSTNAME||' '|| e.F_LASTNAME as name, 
                v.ID_TIENDA ||'-'|| v.F_EMPLOYEE as codemp, 
                v.ID_TIENDA ||'-'|| v.F_MANAGER as manager, 
                TO_CHAR(v.FECHA,'DD-MM-YYYY') ||'-'|| v.ID_TIENDA ||'-'|| v.F_CHECK as codvoid,
                TO_CHAR(v.FECHA,'DD-MM-YYYY') ||'-'|| v.F_HOUR as codHRS, 
                v.F_ITEM,
                v.F_REASON||'-'||t.F_COUNTRY as codrsn, v.F_PRICE, t.F_STORE, t.F_COUNTRY
                from T_GNDVOID v, T_EMP e, T_TIENDAS t
                where v.ID_TIENDA = e.ID_TIENDA 
                and v.F_EMPLOYEE = e.F_ID
                and v.ID_TIENDA = t.ID_TIENDA
            `}, {
            name : 'MV_RSN', 
            query: `
                select r.F_ID||'-'||t.F_COUNTRY as codrsn, 
                r.F_NAME 
                from T_RSN r, T_Tiendas t
                where r.ID_TIENDA = t.ID_TIENDA 
                group by F_NAME, F_ID, t.F_COUNTRY
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