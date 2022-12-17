var Element = require('../element.js');

function nand(_id) {
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
            return !(this.inputs[0].value && this.inputs[1].value);
        }
    }
};    

nand.prototype = Object.create(Element);
nand.prototype.constructor = nand;

module.exports = nand;