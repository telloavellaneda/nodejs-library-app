const fs = require('fs');

module.exports.read = readFile;
module.exports.write = writeFile;
module.exports.delete = deleteFile;
module.exports.list = listFiles;
module.exports.recursive = listFilesRecursive;
module.exports.createDirectory = createDirectory;

function createDirectory(directory) {
    if ( !fs.existsSync(directory) ){
        fs.mkdirSync(directory);
    }
}

function readFile(file) {
    const data = fs.readFileSync(file, "utf8");
    return data.toString().split(/[\r\n]/g).filter(Boolean);
    //return data.toString().split("\n").filter(Boolean);
}

function writeFile(file, data) {
    fs.writeFileSync(file, data);
}

function deleteFile(file) {
    fs.unlinkSync(file);
}

function listFiles(directory) {
    return fs.readdirSync(directory);
}

function listFilesRecursive(dirPath, arrayOfFiles) {
    const files = fs.readdirSync(dirPath);

    arrayOfFiles = arrayOfFiles || [];

    files.forEach( (file) => {
        if (fs.statSync(dirPath + "/" + file).isDirectory()) {
            arrayOfFiles = listFiles(dirPath + "/" + file, arrayOfFiles)
        } else {
            arrayOfFiles.push(path.join(dirPath, "/", file))
        }
    });

    return arrayOfFiles;
}