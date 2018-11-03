/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dldvirtuallabs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;


/**
 *
 * @author rajesh, buddy
 *
 * An Not Gate is a freely existing processing element that is a child class of Element Class
 * It has 1 input and 1 output and has its own logic of simulation.
 * It inherits protected variables from the Element class and
 * implements the functions in ElementInterface
 *
 */

public class Connector extends Element {

    /*
     * Null Constructor creates a null definition of an NotGate.
     * with width = 120 pixels, height = 40 pixels, type = "Not_Gate"
     * This constructor is called while loading a circuit.
     */
    Connector() {
        elementID = 0;
        elementType = "Connector";
        String elementName = "elementName";
        numInputs = 0;
        numOutputs = 0;
        maxIO = 3;
        width = 120;
        height = 40;
        inputList = new Vector<Input>();
        outputList = new Vector<Output>();
        location = new Point();
    }

    /*
     * Constructor with arguments - called when a new NotGate is added to current circuit
     * Creating an NotGate with elementID(id), elementType(type) at location(coord)
     * Creates an NotGate with 2 inputs with ids starting with "inpID", incrementally
     * and outputs with ids starting with "outID" incrementally.
     */
    Connector(int id, String type, int inpId, int outId, Point coord) {
        elementID = id;
        elementType = type;
        elementName = type + String.valueOf(id);
        numInputs = 1;
        numOutputs = 1;
        maxIO = 3;
        width = 120;
        height = 40;
        inputList = new Vector<Input>();
        outputList = new Vector<Output>();
        inputList.add(new Input(inpId, 0, this, new Point(coord.x-3, coord.y)));
        outputList.add(new Output(outId, 0, this, new Point(coord.x+3, coord.y)));
        location = new Point(coord);
    }

    /*
     * Overridden function - updates the location of the NotGate to Point "p"
     * input and output locations are also updated.
     */
    @Override
    public void updateLocation(Point p) {
        // Update this.location to (p.x, p.y) for the element
        location.x = p.x;
        location.y = p.y;

        // Update the locations of 2 input nodes to the element
        inputList.elementAt(0).setLocation(new Point(p.x-3, p.y));

        // Update the locations of the single output node to the element
        outputList.elementAt(0).setLocation(new Point(p.x+3, p.y));
    }

    /*
     * Overridden function updateMatrix updates the circuit matrices
     * shifting the Not Gate from location "prev" to "p"
     */
    @Override
    public void updateMatrix(Point p, int[][] matrixType, int[][] matrixID, Point prev) {
        /*
         * For a new Not Gate, prev is null
         * For an existing Not Gate, it is moved from "prev" to "p"
         * Update the matrix values at prev to 0 => no Not Gate exists there
         */
        if(prev != null) {
            for (int i = -3; i < 4; i++) {                               // update element type and ID
                for (int j = -1; j < 2; j++) {
                    matrixType[prev.y - j][prev.x - i] = 0;
                    matrixID[prev.y - j][prev.x - i] = 0;
                }
            }
            matrixType[prev.y][prev.x + 3] = 0;                                 // update output type and ID
            matrixID[prev.y][prev.x + 3] = 0;

            matrixType[prev.y][prev.x - 3] = 0;                               // update input type and ID
            matrixID[prev.y][prev.x - 3] = 0;
        }

        /*
         * Update the matrix values at location "p" to elementID and elementType
         */
        for(int i=-3; i<4; i++) {                               // update element type and ID
            for(int j=-1; j<2; j++) {
                matrixType[p.y-j][p.x-i] = 3;
                matrixID[p.y-j][p.x-i] = this.elementID;
            }
        }

        matrixType[p.y][p.x+3] = 2;                                 // update output type and ID
        matrixID[p.y][p.x+3] = this.getOutputAt(0).getOutputID();

        matrixType[p.y][p.x-3] = 1;                               // update input type and ID
        matrixID[p.y][p.x-3] = this.getInputAt(0).getInputID();
    }

    /*
     * Overridden function processInputs()
     * processInputs() does a Not operation on all its inputs and fills the output nodes
     */
    @Override
    public void processInputs() {
        if(inputList.elementAt(0).getDataValue() == 0)
            outputList.elementAt(0).setDataValue(0);
        else
            outputList.elementAt(0).setDataValue(1);
    }

    /*
     * Overridden function draw
     * Draws an And Gate at point "p"
     */
    @Override
    public void draw(Graphics g, Point p){
        Graphics2D g2d = (Graphics2D)g;

        /* Define end points of the triangle for NOT gate */
        int[] xPoints = {p.x-20, p.x+20, p.x-20};
        int[] yPoints = {p.y-20, p.y, p.y+20};

        /* Draw a filled gray polygon inside the NOT gate */
        g2d.setColor(Color.GRAY);                               // fill Gray Color
        g2d.fillPolygon(xPoints, yPoints, 3);

        /* Draw the boundary polygon for the NOT gate */
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPoints, yPoints, 3);

        /* Draw the circle in front of the triangle to denote a NOT gate */
        g2d.setColor(Color.BLACK);
 //       g2d.fillOval(p.x+25-7, p.y-7, 14, 14);                        // circle for the Not Gate

        /* Draw the inputs and outputs for the NOT gate */
        inputList.elementAt(0).draw(g, new Point(p.x-20, p.y));
        outputList.elementAt(0).draw(g, new Point(p.x+30, p.y));
    }
}
