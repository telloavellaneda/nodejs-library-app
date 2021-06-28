module.exports.execute = processFolder;

function processFolder(list, fileSet, args) {
    const { dbfDir, idTienda, fecha } = args;

    var result = [];
    list.forEach( (file) => {
        var fileName = '';
        var fileExt = '';

        try {
            fileName = file.split('.')[0].toUpperCase();
            fileExt = file.split('.')[1].toUpperCase();    
        } catch (error) {
            return;
        }

        // Breaks if file extension is not DBF
        if ( fileExt != 'DBF' )
            return;

        // Breaks if file is not part of processing set
        if ( !fileSet.find(element => element.toUpperCase() == fileName) )
            return;        

        result.push({
            directory: dbfDir,
            original: file,
            replaced: idTienda + '_' + fecha + '_' + file.split('.')[0] + '.' + fileExt
        });
    });

    return result;
}