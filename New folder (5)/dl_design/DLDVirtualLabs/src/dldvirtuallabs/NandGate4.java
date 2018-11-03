/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dldvirtuallabs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Arc2D;
import java.util.Vector;



/**
 *
 * @author rajesh, buddy
 *
 * An Nand Gate is a freely existing processing element that is a child class of Element Class
 * It has 2 inputs and one output and has its own logic of simulation.
 * It inherits protected variables from the Element class and
 * implements the functions in ElementInterface
 *
 */

public class NandGate4 extends Element {
    /*
     * Null Constructor creates a null definition of an NandGate.
     * with width = 120 pixels, height = 80 pixels, type = "Nand_Gate"
     * This constructor is called while loading a circuit.
     */
    NandGate4() {
        elementID = 0;
        elementType = "Nand_Gate4";
        String elementName = "elementName";
        numInputs = 0;
        numOutputs = 0;
        maxIO = 5;
        width = 120;
        height = 160;
        inputList = new Vector<Input>();
        outputList = new Vector<Output>();
        location = new Point();
    }

    /*
     * Constructor with arguments - called when a new AndGate is added to current circuit
     * Creating an AndGate with elementID(id), elementType(type) at location(coord)
     * Creates an AndGate with 2 inputs with ids starting with "inpID", incrementally
     * and outputs with ids starting with "outID" incrementally.
     */
    NandGate4(int id, String type, int inpId, int outId, Point coord) {
        elementID = id;
        elementType = type;
        elementName = type + String.valueOf(id);
        numInputs = 4;
        numOutputs = 1;
        maxIO = 5;
        width = 120;
        height = 160;
        inputList = new Vector<Input>();
        outputList = new Vector<Output>();
        inputList.add(new Input(inpId, 0, this, new Point(coord.x-3, coord.y-3)));
        inputList.add(new Input(inpId+1, 1, this, new Point(coord.x-3, coord.y-1)));
        inputList.add(new Input(inpId+2, 2, this, new Point(coord.x-3, coord.y+1)));
        inputList.add(new Input(inpId+3, 3, this, new Point(coord.x-3, coord.y+3)));
        outputList.add(new Output(outId, 0, this, new Point(coord.x+3, coord.y)));
        location = new Point(coord);
    }

    /*
     * Overridden function - updates the location of the NandGate to Point "p"
     * input and output locations are also updated.
     */
    @Override
    public void updateLocation(Point p) {
        // Update this.location to (p.x, p.y) for the element
        location.x = p.x;
        location.y = p.y;

        // Update the locations of 2 input nodes to the element
        inputList.elementAt(0).setLocation(new Point(p.x-3, p.y-3));
        inputList.elementAt(1).setLocation(new Point(p.x-3, p.y-1));
        inputList.elementAt(2).setLocation(new Point(p.x-3, p.y+1));
        inputList.elementAt(3).setLocation(new Point(p.x-3, p.y+3));
        // Update the locations of the single output node to the element
        outputList.elementAt(0).setLocation(new Point(p.x+3, p.y));
    }

    /*
     * Overridden function updateMatrix updates the circuit matrices
     * shifting the Nand Gate from location "prev" to "p"
     */
    @Override
    public void updateMatrix(Point p, int[][] matrixType, int[][] matrixID, Point prev) {
        /*
         * For a new Nand Gate, prev is null
         * For an existing Nand Gate, it is moved from "prev" to "p"
         * Update the matrix values at prev to 0 => no Nand Gate exists there
         */
        if(prev != null) {
            for (int i = -3; i < 4; i++) {                               // update element type and ID
                for (int j = -4; j < 5; j++) {
                    matrixType[prev.y - j][prev.x - i] = 0;
                    matrixID[prev.y - j][prev.x - i] = 0;
                }
            }
            matrixType[p.y][p.x + 3] = 0;                                // update output type and ID
            matrixID[p.y][p.x + 3] = 0;

            matrixType[p.y - 1][p.x - 3] = 0;                            // update input type and ID
            matrixType[p.y + 1][p.x - 3] = 0;
            matrixID[p.y - 1][p.x - 3] = 0;
            matrixID[p.y + 1][p.x - 3] = 0;
        }

        /*
         * Update the matrix values at location "p" to elementID and elementType
         */
        for(int i=-3; i<4; i++) {                               // update element type and ID
            for(int j=-4; j<5; j++) {
                matrixType[p.y-j][p.x-i] = 3;
                matrixID[p.y-j][p.x-i] = this.elementID;
            }
        }

        matrixType[p.y][p.x+3] = 2;                                 // update output type and ID
        matrixID[p.y][p.x+3] = this.getOutputAt(0).getOutputID();

        matrixType[p.y-3][p.x-3] = 1;                               // update input type and ID
        matrixType[p.y-1][p.x-3] = 1;
        matrixType[p.y+1][p.x-3] = 1;
        matrixType[p.y+3][p.x-3] = 1;
        matrixID[p.y-3][p.x-3] = this.getInputAt(0).getInputID();
        matrixID[p.y-1][p.x-3] = this.getInputAt(1).getInputID();
        matrixID[p.y+1][p.x-3] = this.getInputAt(2).getInputID();
        matrixID[p.y+3][p.x-3] = this.getInputAt(3).getInputID();
    }

    /*
     * Overridden function processInputs()
     * processInputs() does an Nand operation on all its inputs and fills the output nodes
     */
    @Override
    public void processInputs() {
        int value = inputList.elementAt(0).getDataValue();
        for(int i=1; i<inputList.size(); i++) {
            value = value & inputList.elementAt(i).getDataValue();
        }
        if(value == 0)
            outputList.elementAt(0).setDataValue(1);
        else
            outputList.elementAt(0).setDataValue(0);
    }

    /*
     * Overridden function draw
     * Draws an Nand Gate at point "p"
     */
    @Override
    public void draw(Graphics g, Point p){
        Graphics2D g2d = (Graphics2D)g;
        Dimension ele = new Dimension(width, height);
        Dimension delta = new Dimension(10, 10);

        /* Defines a semicircular arc */
        Arc2D.Double arc2 = new Arc2D.Double(Arc2D.CHORD);
        arc2.setFrame(p.x-(ele.width/2)-delta.width, p.y-60-delta.height, ele.width-20, ele.height-20);
        arc2.setAngleStart(90);
        arc2.setAngleExtent(-180);

        /* Draw the center of nand gate - a filled semi circle and its border */
        g2d.setColor(Color.GRAY);
        g2d.fill(arc2);
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.BLACK);
        g2d.draw(arc2);

        /* Draw the circle for nand gate in front to denote the NOT */
        g2d.setColor(Color.BLACK);
        g2d.fillOval(p.x+35 - 7, p.y - 7, 14, 14);                                // circle for NAND

        /* Draw the inputs and outputs for the and gate */
        inputList.elementAt(0).draw(g, new Point(p.x-20, p.y-60));
        inputList.elementAt(1).draw(g, new Point(p.x-20, p.y-20));
        inputList.elementAt(2).draw(g, new Point(p.x-20, p.y+20));
        inputList.elementAt(3).draw(g, new Point(p.x-20, p.y+60));
        outputList.elementAt(0).draw(g, new Point(p.x+30, p.y));
    }
}

