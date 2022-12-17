var Element = require('../element.js');

function and(_id) {
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
            return this.inputs[0].value && this.inputs[1].value;
        }
    }
};

and.prototype = Object.create(Element);
and.prototype.constructor = and;

module.exports = and;