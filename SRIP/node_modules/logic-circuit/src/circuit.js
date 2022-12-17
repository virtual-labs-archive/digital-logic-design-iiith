var Inlet = require('./circuit/inlet.js');

function circuit() {
    this.elements = {};
}

circuit.prototype = new Inlet();
circuit.prototype.constructor = circuit;

circuit.prototype.Elements = {
    'AndGate': require('./circuit/gates/and.js'),
    'NandGate': require('./circuit/gates/nand.js'),    
    'OrGate': require('./circuit/gates/or.js'),
    'NorGate': require('./circuit/gates/nor.js'),
    'NotGate': require('./circuit/gates/not.js')
}

circuit.prototype.addElement = function (_element) {
    this.elements[_element.id()] = _element;
};

circuit.prototype.getElement = function (_id) {
    return this.elements[_id];
}

circuit.prototype.reset = function () {
    this.elements.forEach(function (_element) {
        _element.reset();
    }, this);

    this.inputs.forEach(function (_input) {
        _input.value = undefined
    }, this);
    return this;
}

circuit.prototype.createInputs = function (_ids) {
    _ids.forEach(function (_id) {
        this.createInput(_id);
    }, this);
    return this;
}

circuit.prototype.createInput = function (_id) {
    this.inputs.push({
        'id': _id,
        'value': undefined
    });
    return this;
}

circuit.prototype.probe = function (_id) {
    return this.getElement(_id).output();
}

circuit.prototype.solve = function () {

    var allReady = false,
        id;
    
    while (!allReady) {
        allReady = true;

        Object.keys(this.elements).forEach(function (_id) {
            if (!this.elements[_id].isReady()) {
                allReady = false;
                this.elements[_id].getUnsetInputs().forEach(function (_unset) {
                    var element = this.getElement(_unset);
                    if (typeof element === 'undefined') {
                        this.elements[_id].setInput(_unset, this.getInput(_unset));
                    } else if (element.isReady()) {
                        this.elements[_id].setInput(_unset, element.output());
                    }
                }, this);
            }
        }, this);
    }
    return this;
};

module.exports = circuit;