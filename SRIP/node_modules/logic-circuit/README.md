# Logic Circuit

This library let's you build a logic circuit using predefined gates and run a simulation to check the resulting logic outputs.

## Basic usage

It's best to explain using an example. Take a look at this sample logic circuit that we are going to implement. It's a NAND gate implemented using NOR gates.

![NAND](https://upload.wikimedia.org/wikipedia/commons/2/2b/NAND_from_NOR.svg "NAND")  
[source Wikipedia](https://en.wikipedia.org/wiki/NAND_gate)

First let's create an empty circuit itself.

```javascript
var Circuit = require('./src/circuit.js'),
    circuit = new Circuit();
```

Now, let's add some logic gates to it. All elements must be registered with the circuit object.

```javascript
var nor1 = new Circuit.prototype.Elements.NorGate(),    //left top
    nor2 = new Circuit.prototype.Elements.NorGate(),    //left bottom
    nor3 = new Circuit.prototype.Elements.NorGate(),    //middle
    nor4 = new Circuit.prototype.Elements.NorGate();    //right

circuit.addElement(nor1)
    .addElement(nor2)
    .addElement(nor3)
    .addElement(nor4);
```

Let's register and name the input pins of the circuit. There will be two of them. I stick to the naming convention from the image. These are the "pins" which logic values can be freely changed, all other logic states are implied based on them.

```javascript
circuit.createInputs(['A', 'B']);
```

Now we must wire up the entire setup. The order in which you do this is arbitrary. In this case it's more legible to start from the output. Note the use of the circuit inputs ``A`` and ``B``.

```javascript
nor4.bindInput(0, nor3.id())
    .bindInput(1, nor3.id());

nor3.bindInput(0, nor1.id())
    .bindInput(1, nor2.id());

nor2.bindInput(0, 'A')
    .bindInput(1, 'A');

nor1.bindInput(0, 'B')
    .bindInput(1, 'B');

```
Ok everything is pretty much ready. Just one last thing, we need to set the reference states of the inputs ``A`` and ``B``

```javascript
circuit.setInput('A', false)
    .setInput('B', true);
```

To check the results run ``solve`` first and then use ``probe`` to check output values at any point of the circuit.

```javascript
circuit.solve();
var result = circuit.probe(nor4.id());
```

## Examples

Check the ``examples`` folder for more.