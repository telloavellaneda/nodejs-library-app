module.exports.wait = wait;
module.exports.getFlagDate = getFlagDate;

function wait(seconds) {
    return new Promise( (resolve ) => {
        // logger.info(`Waaaait for ${ parseInt(seconds) } seconds, after batch'es execution`);
        setTimeout(() => {
                // logger.info(`Go ahead!`);
                resolve();
            }, 
            parseInt(seconds) * 1000 
        );
    });
}

function getFlagDate(time) {
    const tmpDate = new Date();
    return (
        time.hours == tmpDate.getHours() && 
        time.minutes == tmpDate.getMinutes() &&
        ( 
            tmpDate.getSeconds() == 0 || 
            tmpDate.getSeconds() == 1 || 
            tmpDate.getSeconds() == 2 || 
            tmpDate.getSeconds() == 3 || 
            tmpDate.getSeconds() == 4 
        )
    );
}