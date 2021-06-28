const createUtils = require('../db/create-utils');
const { DBFFile } = require('dbffile');

module.exports.execute = async (connection, files) => {

    for (index in files) {
        for (inner in files[index].list) {
            const fileName = files[index].list[inner].name;
            const dbf = await DBFFile.open(`_init/_tmp/${ fileName }`);
    
            const table = {
                name: fileName,
                fields: [
                    files[index].keys,
                    dbf.fields
                ],
                indexes: files[index].list[inner].indexes
            };
            
            await createUtils.create(connection, table);
        }
    }
}
