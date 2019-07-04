Experiment Project Documentation
========================

Introduction
-----------------

This section captures the technical details related to the experiment development.

Project
-----------------

**Domain Name : **Computer Science & Engineering

**Lab Name : **Design logic Circuit** **

**Experiment Name : **Decoder with seven segment display

A BCD to 7-segment Decoder generates 7 outputs from a BCD (4-bit) input for driving a 7-segment LED display device to display BCD digits. A 7-segment display consists of seven segments designated as a, b, c, d, e, f and g in a clockwise sequence. An optional DP decimal point (an "eighth segment") is used for the display of non-integer numbers. In a 7-segment LED display, either all the anodes or all the cathodes of the LEDs in the seven segments are tied together and brought out at a single pin, and the device is accordingly called either a common-anode or a common-cathode display. In this experiment, we will use a common-anode display, and so the Decoder outputs are applied through resistors placed in series with the cathode of each segment. The objective of this experiment is to design, assemble and test a BCD to 7-segment Decoder generating these seven outputs. Note that each segment is lighted when the corresponding decoder output, also denoted by a, b, c, d, e, f and g, is LOW.

Purpose of the project
-----------------

The purpose of the project is to convert the **BCD to 7 Segment Display** experiment simulation from **Java** to **Javascript**.

Project Developer Details

| S.NO | Name            | Year of study | Role          | E-mail Id                  | Github Handle                   |
|------|-----------------|---------------|---------------|----------------------------|---------------------------------|
| 1.   | Pratyush Narain | 3rd           | Web Developer | pratyushnarain97@gmail.com | https://github.com/Pratyush1197 |

Technologies and Libraries
-----------------

Technologies :
-----------------

1.  HTML
2.  CSS
3.  Javascript

Libraries :   GoJs

Development Environment
-----------------

**OS :** LINUX

 Documents :
 -----------------

| S.NO | Link to Document   | Role                                                                          |
|------|--------------------|-------------------------------------------------------------------------------|
| 1.   | Procedure          | This document captures the instructions to run the  simulations               |
| 2.   | Test Cases         | This document captures the functional test cases of the experiment simulation |
| 3.   | Code Documentation | This document captures the  details related to code                           |

Process Followed to convert the experiment

1.  Understand the assigned experiment Java simulation
2.  Understanding the experiment concept
3.  Re-implement the same in javascript

Value Added by our Project
-----------------

1.  It would be beneficial for engineering students
2.  Highly beneficial for tier 2 and tier 3 college students who can use this to learn and understand the concept of perception learning.

Risks and Challenges
-----------------

Using a new library like GoJs and integrating it with Javascript was a bit challenging as it is very less support on stackoverflow. One of the biggest challenge was to make a Multiplexer as it required to address every port of a node (A node is basically logic gates which is termed as nodes in GoJs). For this I took the help of array concept and fetched all the port Id to an array and called it sequentially. It was a great experience using this library with javascript which helped to brush up my javascript concepts.

Experiment Procedure Documentation
========================

Introduction
-----------------

This document captures the instructions to run the simulation.

Instructions
-----------------

1.  To run the experiment Clone the repository given below

     [https://github.com/Pratyush1197/LogicCircuit.gi](https://github.com/Pratyush1197/LogicCircuit.git)t

2.  Now open the folder LogicCircuit.html
3.  Inside it again open the folder logicCircuit
4.   Now simply run the logicCircuit.html file by clicking on it and the experiment will open in the browser.

 It has all the logic gates (AND, OR, NOT,NOR,NAND,XOR and XNOR) fully functional with input and output ports provided for inputing the data and collecting it respectively. Project also has a BCD to seven segment display decoder library predefined which can be simply loaded by clicking the load button. Uses of different gates is as follows

![image](https://user-images.githubusercontent.com/32239181/60384845-556a8580-9aa0-11e9-9ed0-d96454ec62d5.png)

**INPUT - **We can give any input just by dragging the input node to the drawing area and double clicking on it. It will open a small input box where you can provide your value

**Output- **We can get the output value when this is connected to endpoint. As at present its work is to show only the seven segment display so when the output is **one **it will display red LED of that particular segment else it will be gray.It has seven output templates to take output for seven segments simutaneously.

**Gates **T****he gates works when we connect the wires with their ports we will get the output which can be calculated by taking out the wire from its output port.

**Multiplexer **Its work is to take four inputs and it has two select lines when we give it output in binary form (i.e 0 or 1) we will get the selected input as output. Suppose we give input as 0 and 1 i.e MSB is given output 0 and LSB as 1.(The select line port are the bottom two and the left side one is the MSB and right is LSB). The decimal equivalent of it is one so output will be input which is given to port 2 of Input port(These are the ports which are connected to the left of mux.

**BCD to seven segment display decoder **Click on the load button. The Circuit will be loaded. Now the four input ports signify the four inputs with first input from left is MSB and last is LSB. Input is initially **zero**. Double click on any input port and set **one **as input. The color will be changed from red to green. When setting the value again to zero will make it red again.

Load button- This will load the working circuit of our experiment.


![image](https://user-images.githubusercontent.com/32239181/60384852-60bdb100-9aa0-11e9-8dae-4223e09a2f66.png)


![image](https://user-images.githubusercontent.com/32239181/60384854-6f0bcd00-9aa0-11e9-854f-3edad134b4ff.png)


 **Clear button- **Clicking on the clear button will reload the screen and clear all the circuits on the screen

**Experiment Test Cases Documentation**
========================

**Introduction **
-----------------

This document captures the test cases of the experiment.

**Functional Test Cases**
-----------------



| Test Case Id | Test Scenario                                                    | Test Case                                     | Test Data                     | Expected Output                      | Actual Output                  | Test Result | Comments |
|--------------|------------------------------------------------------------------|-----------------------------------------------|-------------------------------|--------------------------------------|--------------------------------|-------------|----------|
| 1.           | To get the number which is given as input, on the seven segment. | We give input as 0 1 1 0( i.e in binary form) | Data can only be binary value |  Seven segment display should show 6 | Seven segment display shows 6  | Pass        | None     |
| 2.           | To get the number which is given as input, on the seven segment. | We give input as 1 0 0 1( i.e in binary form) | Data can only be binary value | Seven segment display should show 9  | Seven segment display shows  9 | Pass        | None     |

****

In case if it doesn’t work on some browsers, list down the browsers along with the versions here

| S. No | Browser           | Version  | Works(Yes/No) |
|-------|-------------------|----------|---------------|
| 1.    | Internet Explorer | 18, 17    | Yes           |
| 2.    | Chrome            | 77, 75, 74 | Yes           |
| 3.    | Firefox           | 66, 67, 68 | Yes           |
| 4.    | Opera             | 60, 58, 61 | Yes           |

Experiment Code Documentation
========================

Introduction
-----------------

This document captures the experiment implementation details.

Code Details
-----------------

**File Name : LogicCircuit.js**

**File Description : **This is a digital logic circuit simulator which is made with GoJs library,Vanilla Javascript HTML and CSS**.**

Other details:

BCD to 7 Segment Display
========================

A seven-segment display is an electronic display device for displaying decimal numerals. Seven-segment displays are widely used in digital clocks, electronic meters and other electronic devices that display numerical information.

7 Segment Display
-----------------

A 7 Segment LED display generally has 8 input connections, one for each LED segment and one that acts as a common terminal. There are 2 types of 7 Segment LED digital display.

-   Common Cathode Display – all the cathode connections of the LEDs are connected to ground. A logic '1' applied to the anode terminal of the individual segment illuminates it.
-   Common Anode Display – all the anode connections of the LEDs are connected to VCC. A logic '0' applied to the cathode terminal of the individual segment illuminates it.

BCD to 7 Segment Display Decoder
--------------------------------

A BCD to Seven Segment decoder is a combinational logic circuit that accepts a decimal digit in BCD (input) and generates appropriate outputs for the segments to display the input decimal digit.

Here is the truth table for the working:

![image](https://user-images.githubusercontent.com/32239181/60384857-7e8b1600-9aa0-11e9-89bd-c41880ff3e30.png)


The input bits are D (MSB) to A (LSB) and the outputs are the segments a to g. For input values A to F, the display is blanked (outputs are all 0).

<span id="anchor"></span>
**Karnaugh Maps Simplification**

For other combinations of input, the output is “**don’t care X**” as there are no more digits to display. We will derive the expression for each output using [ ](https://www.electricaltechnology.org/2018/05/karnaugh-map-k-map.html)****Karnaugh Maps****.

**For output a:**

![image](https://user-images.githubusercontent.com/32239181/60384860-85b22400-9aa0-11e9-9177-6bf88d776156.png)



**For output b:**

![image](https://user-images.githubusercontent.com/32239181/60384862-8c409b80-9aa0-11e9-943a-8fa8f5042511.png)


**For output c:**

![image](https://user-images.githubusercontent.com/32239181/60384865-99f62100-9aa0-11e9-934a-61c212802b7d.png)


<span id="anchor-1"></span><span id="anchor-2"></span><span id="anchor-1"></span>**For output d:**


![image](https://user-images.githubusercontent.com/32239181/60384879-a2e6f280-9aa0-11e9-80a4-41bc75f1b972.png)

**For output e:**


![image](https://user-images.githubusercontent.com/32239181/60384884-aed2b480-9aa0-11e9-8c53-d05f17a5f286.png)



**For output f:**

![image](https://user-images.githubusercontent.com/32239181/60384887-b7c38600-9aa0-11e9-8dc8-29b13d2a1d92.png)


**For output g:**

![image](https://user-images.githubusercontent.com/32239181/60384895-c1e58480-9aa0-11e9-8b4e-8993414ef79a.png)

Now using these formulas for **a,b,c,d,e,f,g **we design logic circuits using Gates(AND, OR,NOT)

so the final circuit looks like this

![image](https://user-images.githubusercontent.com/32239181/60384898-ca3dbf80-9aa0-11e9-951f-4e180893016c.png)
