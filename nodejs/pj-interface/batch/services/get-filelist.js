const fileUtils = require('../../_common/file-utils');
const createFechas = require('../util/create-fechas');
const processFolder = require('./process-folder');
const env = require('./read-environment');

module.exports.execute = get;

function get(tienda) {
    env.get();
    const { files, dbfDir, initLoad } = global.env;

    // Validate what files have DOB
    var catFiles = [];
    var dobFiles = [];
    files.forEach(element => {
        const tmp = element.toUpperCase().split('*');
        if ( tmp.length > 1 )
            dobFiles.push(tmp[0]);
        else 
            catFiles.push(tmp[0]);
    });

    var list;
    var list2process = [];

    // DOB files
    var lastValidDate = '';
    const fechas = createFechas.execute(initLoad, 2);
    for (index in fechas) {        
        const path = dbfDir + '/' + fechas[index];
        
        try {
            list = fileUtils.list(path);
        } catch (error) {
            continue;
        }

        list2process = list2process.concat( processFolder.execute(
            list,
            dobFiles,
            {
                dbfDir: path,
                idTienda: tienda.idTienda,
                fecha: fechas[index]
            }
        ));

        lastValidDate = fechas[index];
    }
    
    if (lastValidDate == '')
        return list2process;

    // CAT files
    list = fileUtils.list( dbfDir + '/' + lastValidDate );
    list2process = processFolder.execute(
        list,
        catFiles,
        {
            dbfDir: dbfDir + '/' + lastValidDate,
            idTienda: tienda.idTienda,
            fecha: '&'
        }
    ).concat(list2process);

    return list2process;
}