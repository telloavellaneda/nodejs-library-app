const logger = require('../../_common/logger');
const oracledb = require('oracledb');

// execute(false);

module.exports.execute = execute;

async function execute(live) {

	const DB_URL = 'pjc-bi-01.eastus.cloudapp.azure.com:1521/XE';
	const DB_USER = process.env.DB_USER;
	const DB_PASSWORD = process.env.DB_PASSWORD;
	const DB_LINK_NAME = 'pjcdblink';

	var sql;
	var result;
	var connection;

	connection = await oracledb.getConnection({
		user: DB_USER,
		password: DB_PASSWORD,
		connectString: DB_URL
	});
	logger.info(`Connected [${ DB_URL }]`);

	const tablespaces = ['PJ' , 'PJ_EXT'];

	const filters = [
		`AND table_name NOT IN (
			'T_GNDSALE',
			'T_GNDITEM',
			'T_GNDLINE',
			'T_GNDTNDR',
			'T_GNDTURN',
			'T_GNDVOID',
			'MV_RSN',
			'MV_GNDVOID',
			'MV_GNDITEM',
			'MV_GNDITEM_SUM',
			'MV_GNDITEM_GNDLINE',
			'MV_FORECAST_BUDGET'
		)`,
		`AND table_name IN (
			'T_GNDVOID',
			'T_GNDTNDR',
			'T_GNDTURN',
			'MV_RSN',
			'MV_GNDVOID',  
			'MV_GNDITEM',
			'MV_GNDITEM_SUM',
			'MV_GNDITEM_GNDLINE',
			'MV_FORECAST_BUDGET'
		)`
	];

	for (var j in filters) {
		sql = `SELECT table_name tabla FROM all_tables@${ DB_LINK_NAME } WHERE owner='${ DB_USER }' ${ filters[j] } ORDER BY TABLE_NAME`;
		var tables = await connection.execute(sql);

		for (var i in tables.rows) {
			const name = tables.rows[i][0];
	
			try {
				logger.info(`[${ name }] Dropping...`);
				sql = `DROP MATERIALIZED VIEW ${ name }`;
				// console.log(sql)
				if (live) result = await connection.execute(sql);
				logger.info(`[${ name }] Dropped.`);	
			} catch (error) {
				logger.info(`[${ name }] doesn't exist`);
			}
	
			try {
				logger.info(`[${ name }] Creating...`);
				sql = `
				 	CREATE MATERIALIZED VIEW ${ name } TABLESPACE ${ tablespaces[j] }
				 	NOLOGGING CACHE BUILD IMMEDIATE REFRESH ON DEMAND AS 
				 	SELECT * FROM ${ name }@${ DB_LINK_NAME }
				`;
				// console.log(sql)
				if (live) result = await connection.execute(sql);
				logger.info(`[${ name }] Created.`);
			} catch (error) {
				console.log(error)
				logger.error(`[${ name }] ERROR: ${ JSON.stringify(error) }`);
			}
		}
	}

	connection.close();
	logger.info(`Disconnected [${ DB_URL }]`);
}