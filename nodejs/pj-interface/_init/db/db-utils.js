const { v4: uuidv4 } = require('uuid');

module.exports.setTableName = setTableName;
module.exports.getFieldsWithType = getFieldsWithType;
module.exports.joinKeyFields = joinKeyFields;
module.exports.getConstraintName = getConstraintName;
module.exports.parseField = parseField;
module.exports.getBinds = getBinds;
module.exports.setValues = setValues;

function setTableName(fileName) {
    return 'T_' + fileName.toUpperCase().split('.')[0];
}

function getFieldsWithType(fields) {
    return fields
        .map( field => parseField(field) )
        .join(', ');    
}

function joinKeyFields(fields) {
    return fields
        .map( field => field.name )
        .join(', ');
}

function getConstraintName() {
    const constName = uuidv4().toString();
    const temp = constName.split('-');
    return 'PJ_' + temp[0].toUpperCase() + '_' + temp[1].toUpperCase();
}

function parseField(field) {
    var type;
    var preffix;

    if ( field.type == 'PK-NUMBER' ) {
        preffix = '';
        type = 'NUMBER';

     } else if ( field.type == 'PK-VARCHAR' ) {
        preffix = '';
        type = `VARCHAR2(${ field.size })`;

    } else if ( field.type == 'PK-DATE' ) {
        preffix = '';
        type = `DATE`;

    } else if ( field.type == 'D' ) {
        preffix = 'F_';
        // type = `VARCHAR2(10)`;
        type = `DATE`;

    } else if ( field.type == 'N' ) {
        preffix = 'F_';
        type = `NUMBER`;

    } else {
        preffix = 'F_';
        // Insurance to inconsistencies at Aloha's DB structure
        type = 'VARCHAR2(' + (parseInt(field.size) * 2) + ')';
        // type = 'VARCHAR2(' + (parseInt(field.size) + parseInt(field.decimalPlaces)) + ')';
    }

    return preffix + field.name + ' ' + type ;
}

function getBinds(fields) {
    return fields
        .map( (value, index) => ':' + (index + 1))
        .join(', ');
}

function setValues(fields, values, tableName) {
    var result = [];
    const names = fields[1].map( field => field.name );

    for(var i in values) {

        // BUSINESS RULE
        // Only GNDSALE, TYPE=1 allowed
        // if (tableName == 'T_GNDSALE' && values[i]["TYPE"] != 1 )
        //     continue;

        var rowValue = [];

        rowValue.push( uuidv4().toString() );

        for(var j in fields[0])
            rowValue.push( fields[0][j].value );

        for (var j in names) {
            var tmp = values[i][ names[j] ];
            if ( fields[1][j].type == 'D' )
                try {
                    rowValue.push( tmp.toISOString().split('T')[0] );
                } catch (error) {
                    rowValue.push( tmp );
                }
            else
                rowValue.push( tmp );
        }
        
        result.push(rowValue);
    }
    // console.log(values.length);
    // console.log(result.length);
    return result;
}