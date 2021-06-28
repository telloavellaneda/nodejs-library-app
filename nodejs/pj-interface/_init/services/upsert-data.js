const upsertUtils = require('../db/upsert-utils');
const logger = require('../../_common/logger');
const { DBFFile } = require('dbffile');
const fs = require('fs');

module.exports.execute = async (connection, bandera) => {

    await connection.execute(`ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD'`);

	const uploadsDir = './uploads';
	const files = fs.readdirSync(uploadsDir);

	for (var i in files) {

		// if (parseInt(i) == 1500)
			// break;

		const file = files[i];

		try {
			if ( file.split('.')[1].toUpperCase() != 'DBF')
				continue;
		} catch (error) {
			continue;
		}

		const dbf = await DBFFile.open(uploadsDir + '/' + file);

		if (await dbf.recordCount == 0) {
			fs.unlinkSync(uploadsDir + '/' + file);
			continue;
		}

		const tmpArr = file.split('_');
		const idTienda = tmpArr[0];
		const tmpFecha = tmpArr[1];
		const fileName = tmpArr[2];

		var pk = [
			{ name: 'ID_TIENDA', value: idTienda }
		];

		var tmpLoggedFecha = '';
		if (tmpFecha != '&') {
			tmpLoggedFecha = tmpFecha.substr(0,4) + '-' + tmpFecha.substr(4,2) + '-' + tmpFecha.substr(6,2);
			pk.push({ name: 'FECHA', value: tmpLoggedFecha });

			tmpLoggedFecha = `, fecha=${ tmpLoggedFecha }`;
		}

		var tmpDbfFields = dbf.fields;
		// -----------------------------
		// FIX 1.0 Aloha DBF Inconsistencies
		if (fileName.toUpperCase() == 'GNDITEM.DBF')
			tmpDbfFields = dbf.fields.filter(field => !['OCCTAXID','PARENTENID'].includes(field.name));
		else if (fileName.toUpperCase() == 'GNDLINE.DBF')
			tmpDbfFields = dbf.fields.filter(field => !['ENTRYIDITM'].includes(field.name));
		else if (fileName.toUpperCase() == 'GNDTNDR.DBF')
			tmpDbfFields = dbf.fields.filter(field => !['WAGEGRAT'].includes(field.name));
		// console.log(tmpDbfFields);
		// -----------------------------

		const table = {
			name: fileName,
			fields: [
					pk,
					tmpDbfFields
			],
			values: await dbf.readRecords()
		};

		const { flag, message } = await upsertUtils.upsert(connection, table);

		const label = `[${ file }] ${ message }`;
		if (!flag)
			logger.error(label);
		else
			try {
				fs.unlinkSync(uploadsDir + '/' + file);
				logger.info(label);	
			} catch (error) {
				logger.error(uploadsDir + '/' + file + ' FILE COULD NOT BE DELETED')
			}
	}

	if (files.length > 0 && bandera)
		await require('../db/create-mv-new').execute(connection);
} 