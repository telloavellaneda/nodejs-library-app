require('winston-daily-rotate-file');
const winston = require('winston')

const timezoned = () => {
    return new Date().toLocaleString('en-US', {
        timeZone: 'America/Mexico_City',
        format: 'YYYY-MM-DD HH:mm:ss'
    });
}

const consoleFormat = winston.format.combine(
    winston.format.colorize(),
    winston.format.timestamp({ format : 'YYYY-MM-DD HH:mm:ss.SSS' }), //{ format: timezoned }
    winston.format.printf(info => `${ info.timestamp } [${info.level}] ${ info.message }`)
);

const fileFormat = winston.format.combine(
    winston.format.timestamp({ format : 'YYYY-MM-DD HH:mm:ss.SSS' }),
    winston.format.printf(info => `${ info.timestamp } ${ info.message }`)
);

var transport = new (winston.transports.DailyRotateFile)({
    filename: `log/${ process.env.WINSTON_FILENAME }`,
    // filename: 'log/app-%DATE%.log',
    datePattern: 'YYYYMMDD',
    zippedArchive: true,
    maxSize: '1g',
    format: fileFormat,
    level: 'info'
});

transport.on('rotate', function(oldFilename, newFilename) {
    // do something fun
});

const logger = winston.createLogger({
    transports: [
        transport,
        new winston.transports.Console({ format: consoleFormat })
    ],
});

module.exports = logger;