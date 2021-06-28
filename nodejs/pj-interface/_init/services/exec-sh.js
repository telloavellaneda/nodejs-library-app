const util = require('util');
const childProcess = require('child_process');
const exec = util.promisify(childProcess.exec);

async function lsWithGrep() {
    try {
        const { stdout, stderr } = await exec('chown -R centos:centos wpmexico56/');
        console.log('stdout:', stdout);
        console.log('stderr:', stderr);
    } catch (err) {
        console.error(err);
    };
};

lsWithGrep();