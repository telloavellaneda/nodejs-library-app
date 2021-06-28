const crypto = require('crypto');
const algorithm = 'aes-256-cbc';
const key = `112577f4b2ad6a955f87f307c0c2335b19ee3ae6a0d2172b4dddade071c9cd7d`; // crypto.randomBytes(32);
const iv = `e06568d3750bf89bb7ac4d1b25795ce0`; // crypto.randomBytes(16);

function encrypt(text) {
    let cipher = crypto.createCipheriv('aes-256-cbc', Buffer.from(key, 'hex'), Buffer.from(iv,'hex'));
    let encrypted = cipher.update(text);
    encrypted = Buffer.concat([encrypted, cipher.final()]);
    return encrypted.toString('hex');
}

function decrypt(text) {
    let encryptedText = Buffer.from(text, 'hex');
    let decipher = crypto.createDecipheriv('aes-256-cbc', Buffer.from(key,'hex'), Buffer.from(iv,'hex'));
    let decrypted = decipher.update(encryptedText);
    decrypted = Buffer.concat([decrypted, decipher.final()]);
    return decrypted.toString();
}

module.exports.encrypt = encrypt;
module.exports.decrypt = decrypt;