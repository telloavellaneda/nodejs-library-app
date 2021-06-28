// ------------------ MAIN Logger Setup ---------------------
// MUST BE HERE BECAUSE I NEED TO SET UP WHAT THE FILENAME IS
process.env.WINSTON_FILENAME = 'api-%DATE%.log';
const logger = require('./_common/logger');
// ------------------ MAIN Logger Setup ---------------------

const dotenv = require('dotenv');
const express = require('express');
const bodyParser = require('body-parser');
const multer = require('./api/service/multer');
const upload = require('./api/routes/upload');
const select = require('./api/routes/select');

// Load Environment Variables
dotenv.config({ path: './api/config/config.env' });

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(express.static('public'));

process.on('exit', (code) => {
    logger.info('Process exit event with code: ', code);
});

app.get('/select/:id/:month', select.execute);
app.post('/upload', multer.single('file'), upload.execute);

const port = process.env.PORT || 3000;
app.listen(port, () => logger.info(`PJ API REST started on ${port}`));