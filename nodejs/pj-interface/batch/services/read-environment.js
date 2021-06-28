const fileUtils = require('../../_common/file-utils');
const fs = require('fs');

module.exports.get = get;
module.exports.searchDbfDir = searchDbfDir;

function searchDbfDir() {
    var temp = '';
    require('../../_common/file-utils').read('./batch/config/directories.txt').forEach( directory => {
        if (fs.existsSync(directory)) {
            temp = directory;
            return;
        }
    });
    return temp;
}

function get() {
    const execFile = './exec-now.txt';
    const initLoadFile = './init.txt';
    const timeDir = `./batch/config/time.txt`;
    const filesDir = `./batch/config/files.txt`;
    const urlFile = `./batch/config/url.txt`;

    fileUtils.createDirectory('./workdir');

    const execNow = fileUtils.read(execFile)[0];
    const time = fileUtils.read(timeDir)[0].split(':');

    var initLoad;
    try {
        fileUtils.read(initLoadFile);
        initLoad = true;
    } catch (error) {
        initLoad = false;
    }

    var dbfDir = searchDbfDir();

    if (process.env.NODE_ENV == 'prod')
        process.env.API_UPLOAD = fileUtils.read(urlFile)[0];
    else
        process.env.API_UPLOAD = 'http://localhost/upload';
    
    const env = {
        execNow: (execNow == 'NO') ? false : true,
        initLoad: initLoad,
        time: {
            hours: parseInt(time[0]),
            minutes: parseInt(time[1])
        },
        dbfDir: dbfDir,
        files: fileUtils.read(filesDir)
    };

    global.env = env;
}