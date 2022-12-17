var Element = require('../element.js');

function not(_id) {
    Element.call(this);

    this.inputs = [
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
            return !this.inputs[0].value;
        }
    }
};

not.prototype = Object.create(Element);
not.prototype.constructor = not;

module.exports = not;