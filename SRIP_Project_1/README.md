# Counters

This lab allows you to design and simulate any digital combinational or sequential circuit. Following instruction help you build the counter circuit. A Javascript-based basic tool and simulation enigne is at the hear of this laboratory. You can create many circuits and test them with fixed or time-varying input signals. You can also see the outputs as values or time-varying waveforms.

Counter
![Image](https://github.com/gauravggg21/digital-logic-design-iiith/blob/master/SRIP_Project_1/Codes/images/counter.png?raw=true)

Ring Counter
![Image](https://github.com/gauravggg21/digital-logic-design-iiith/blob/master/SRIP_Project_1/Codes/images/ringcounter.png?raw=true)

The tool consist of mainly two pannels: Left Pannel, Right Pannel.

* Left Pannel:You can change the settings from the left pane.

	1. Frequency Input:The frequency input changes the frequency of the oscillator named as OSC on the simulation panel

	2. Modules:The checkboxes are to show you the expected output of your circuit. An abstracted version of the circuit will appear in the simulation. Please try to replicate the output.

	* Please supply DC 1 to the middle input of the ring module counter for it to work properly.

	3. Save Settings:Click on the button to save your settings. You need to do it, in order for the settings to apply.

	4. Clear Settings:Click on the button to discard all your settings.

	* WARNING: When you click on save settings or clear settings, your circuit gets cleared automatically, so please choose your settings before hand.
	5. If you get stuck, and are not able to make the counter, the load counter and load ring counter buttons will help you out.

	6. Click the "Clear Workspace" button to clear the simulation.

* Right Pannel: The right pane contains the workspace where you will simulate your experiment.

	1. The toolbox on the left contains the pre-made logic circuits you will require to build a counter.

	* In case you want to build a Flip-Flop, please open the Flip-Flop experiment page.

	2. Drag them on the right, to place them on your breadboard.

	3. The left part of the abstracted circuit contains the inputs, and the right side the outputs.

	4. Click on the circle of the input or output to another input/output to make a connection.

	* You can connect only one line to an input, but an output can have multiple lines.

	5. The "DC" icon outputs digital 1 at all times, connnect it to:

	* -push on: to simulate the 1 only when you're pressing on the button

	* -push off: to simulate 0 when you're pushing the button

	* -toggle: to toggle the output between 1 and 0.

	6. The OSC supplies 1 and 0 in a specific frequency which you set in the settings pane.

	7. The joint icon is just a connection, to make your connections look neater, as the lines from an output to an input are straight.

	8. The DSO displays the output in a graph format for easy verification. It has 8 inputs, you can choose to use any number of them. If you want more, just drag drop more of them.

## [Project Documentation](https://gist.github.com/gauravggg21/5a2b0c0fd7e35f624b74854cb1a92cec)