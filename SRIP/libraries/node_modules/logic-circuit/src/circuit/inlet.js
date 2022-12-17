function inlet() {

    this.inputs = [];

    /////////////////////////////////////////////

    this.isReady = function () {
        var i = 0;

        for (i = 0; i < this.inputs.length; i += 1) {
            if (typeof this.inputs[i].id === 'undefined' || typeof this.inputs[i].value === 'undefined') {
                return false;
            }
        }
        return true;
    }

    this.reset = function () {
        this.inputs.forEach(function (_input) {
            _input.value = undefined
        }, this);
        return this;
    }

    this.bindInput = function (_index, _id) {
        this.inputs[_index].id = _id;
        return this;
    }

    this.setInput = function (_id, _value) {
        this.inputs.forEach(function (_input) {
            if (_input.id === _id) {
                _input.value = _value;
                return this;
            }
        }, this);
        return this;
    }

    this.getInput = function (_id) {
        var i = 0;
        for (i = 0; i < this.inputs.length; i += 1) {
            if (this.inputs[i].id === _id) {
                return this.inputs[i].value;
            }
        }
        return undefined;
    }

    this.countInputs = function () {
        return this.inputs.length;
    }

    this.getUnsetInputs = function () {
        var ids = [];
        this.inputs.forEach(function (_input) {
            if (typeof _input.value === 'undefined') {
                ids.push(_input.id);
            }
        }, this);
        return ids;
    }
};

module.exports = inlet;