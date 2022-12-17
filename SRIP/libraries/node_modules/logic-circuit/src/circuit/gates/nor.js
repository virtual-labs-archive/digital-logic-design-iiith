var Element = require('../element.js');

function nor(_id) {
    Element.call(this);

    this.inputs = [
        {
            'id': undefined,
            'value': undefined
        },
        {
            'id': undefined,
            'value': undefined
        }
    ];

    /////////////////////////////////////////////
    
    this.output = function () {
        if (!this.isReady()) {
            return undefined;
        } else {
            return !(this.inputs[0].value || this.inputs[1].value);
        }
    }    
};

nor.prototype = Object.create(Element);
nor.prototype.constructor = nor;

module.exports = nor;