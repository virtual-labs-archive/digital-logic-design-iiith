var Inlet = require('./inlet.js');

function element(_id) {
    Inlet.call(this);
    
    if (typeof _id === 'undefined') {
        _id = require('node-uuid').v4();
    }

    /////////////////////////////////////////////

    this.id = function () {
        return _id;
    }
};

element.prototype = Object.create(Inlet);
element.prototype.constructor = element;

module.exports = element;