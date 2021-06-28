const fs = require('fs');
const parse = require('csv-parse');

module.exports.execute = (filename) => {
    return new Promise( (resolve) => {
        
        var inputPath = filename;

        fs.readFile(inputPath, (err, fileData) => {

            parse(fileData, { columns: false, trim: true, delimiter: ';' }, (err, rows) => {
                var data = [];
                var fields = rows[0];
                rows.shift();

                for (var i in rows) {
                    var strJson = '{ ';
                    var comma = '';
                    for (var j in fields) {
                        if (j > 0) comma = ', ';
                        strJson += `${ comma }"${ fields[j].trim() }" : "${ rows[i][j] }"`;
                    }
                    strJson += ' }';

                    data.push(JSON.parse(strJson));
                }
                resolve(data);
            })

        });        
    });
}