const createConnection = require('../../_init/db/create-connection');
const validateToken = require('../../_common/validate-token');
const logger = require('../../_common/logger');
const dotenv = require('dotenv');

dotenv.config({ path: './api/config/config.env' });

module.exports.execute = select;

async function select(req, res) {
    
    var result;
    // try {
    //     if (!req.headers.token) {
    //         logger.error(`/select/:id: Invalid Token`);
    //         return res.status(400).send({ status: 400, message:'Invalid Token' });
    //     }
    //     result = validateToken.execute(req.headers.token);
    // } catch (error) {
    //     logger.error(`Select endpoint: Invalid Token`);
    //     return res.status(403).send({ status: 403, message:'Invalid Token' });
    // }

    // console.log(req.params)
    const connection = await createConnection.open();
    result = await connection.execute(`
        SELECT
        a.f_employee,
        B.F_FIRSTNAME, 
        B.F_LASTNAME,
        f_dob,
        TO_CHAR(f_dob,'YYYY') ANIO ,
        TO_CHAR(f_dob,'MM') MES ,
        TO_CHAR(f_dob,'DD') DIA,
        a.f_closehour,
        a.f_check,
        SUM(A.F_AMOUNT) AMOUNT_, 
        COUNT(*) COUNT_
        FROM 
        T_GNDSALE A, 
        T_EMP B
        WHERE 1=1
        AND A.F_EMPLOYEE = B.F_ID
        and A.ID_TIENDA = ${ req.params.id }
        AND TO_CHAR(a.f_dob,'MM') = '${ req.params.month }'
        GROUP BY 
        a.f_employee,
        B.F_FIRSTNAME, 
        B.F_LASTNAME,
        f_dob,
        TO_CHAR(f_dob,'YYYY')  ,
        TO_CHAR(f_dob,'MM')  ,
        TO_CHAR(f_dob,'DD') ,
        a.f_closehour,
        a.f_check
    `);
    logger.info('Query ' + result.rows.length)
    createConnection.close(connection);

    res.send({
        meta: {
            count: result.rows.length
        },
        data: result.rows
    });
}

