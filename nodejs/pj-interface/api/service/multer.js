const multer = require('multer');

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, `./uploads/`);
    },

    filename: (req, file, cb) => {
        cb(null, file.originalname);
    }
});

const filterFile = (req, file, cb) => {
    if (!file.originalname.match(/\.(DBF)$/i)) {
        req.fileValidationError = 'Only DBF files are allowed!';
        return cb(new Error('Only DBF files are allowed!'), false);
    }
    cb(null, true);
};

module.exports = multer({ storage: storage, fileFilter: filterFile.filter });
