//NAND gate using NOR gates example

var Circuit = require('./src/circuit.js'),
    circuit = new Circuit();

var nor1 = new Circuit.prototype.Elements.NorGate(),    //left top
    nor2 = new Circuit.prototype.Elements.NorGate(),    //left bottom
    nor3 = new Circuit.prototype.Elements.NorGate(),    //middle
    nor4 = new Circuit.prototype.Elements.NorGate();    //right

circuit.addElement(nor1);
circuit.addElement(nor2);
circuit.addElement(nor3);
circuit.addElement(nor4);

circuit.createInputs(['A', 'B']);

nor4.bindInput(0, nor3.id());
nor4.bindInput(1, nor3.id());

nor3.bindInput(0, nor1.id());
nor3.bindInput(1, nor2.id());

nor2.bindInput(0, 'A');
nor2.bindInput(1, 'A');

nor1.bindInput(0, 'B');
nor1.bindInput(1, 'B');

circuit.setInput('A', true);
circuit.setInput('B', true);

circuit.solve();
var result = circuit.probe(nor4.id());