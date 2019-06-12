/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dldvirtuallabs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.QuadCurve2D;
import java.util.Vector;



/**
 *
 * @author rajesh, buddy
 *
 * An Or Gate is a freely existing processing element that is a child class of Element Class
 * It has 2 inputs and one output and has its own logic of simulation.
 * It inherits protected variables from the Element class and
 * implements the functions in ElementInterface
 *
 */

public class OrGate4 extends Element {

    /*
     * Null Constructor creates a null definition of an OrGate.
     * with width = 120 pixels, height = 80 pixels, type = "Or_Gate"
     * This constructor is called while loading a circuit.
     */
    OrGate4() {
        elementID = 0;
        elementType = "Or_Gate4";
        elementName = new String();
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
     * Constructor with arguments - called when a new OrGate is added to current circuit
     * Creating an OrGate with elementID(id), elementType(type) at location(coord)
     * Creates an OrGate with 2 inputs with ids starting with "inpID", incrementally
     * and outputs with ids starting with "outID" incrementally.
     */
    OrGate4(int id, String type, int inpId, int outId, Point coord) {
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
     * Overridden function - updates the location of the OrGate to Point "p"
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
     * shifting the Or Gate from location "prev" to "p"
     */
    @Override
    public void updateMatrix(Point p, int[][] matrixType, int[][] matrixID, Point prev) {
        /*
         * For a new Or Gate, prev is null
         * For an existing Or Gate, it is moved from "prev" to "p"
         * Update the matrix values at prev to 0 => no Or Gate exists there
         */
        if(prev != null) {
            for (int i = -3; i < 4; i++) {                               // update element type and ID
                for (int j = -4; j < 5; j++) {
                    matrixType[prev.y - j][prev.x - i] = 0;
                    matrixID[prev.y - j][prev.x - i] = 0;
                }
            }
            matrixType[p.y][p.x + 3] = 0;                                 // update output type and ID
            matrixID[p.y][p.x + 3] = 0;

            matrixType[p.y - 1][p.x - 3] = 0;                               // update input type and ID
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
     * processInputs() does an Or operation on all its inputs and fills the output nodes
     */
    @Override
    public void processInputs() {
        int value = inputList.elementAt(0).getDataValue();
        for(int i=1; i<inputList.size(); i++) {
            value = value | inputList.elementAt(i).getDataValue();
        }
        outputList.elementAt(0).setDataValue(value);
    }

    /*
     * Overridden function draw
     * Draws an Or Gate at point "p"
     */
    @Override
    public void draw(Graphics g, Point p){
        Graphics2D g2d = (Graphics2D)g;

        /* Define three curves for OR Gate */
        QuadCurve2D q1 = new QuadCurve2D.Float(p.x-30, p.y-75, p.x+10, p.y-70, p.x+30, p.y);
        QuadCurve2D q2 = new QuadCurve2D.Float(p.x-30, p.y+75, p.x+10, p.y+70, p.x+30, p.y);
        QuadCurve2D q3 = new QuadCurve2D.Float(p.x-30, p.y-75, p.x, p.y, p.x-30, p.y+75);

        /* Draw filled curves q1, q2 (chords) using the intersection principle */
        g2d.setColor(Color.GRAY);
        g2d.fill(q1);
        g2d.fill(q2);
        g2d.fillPolygon(new int[] {p.x-30, p.x-30, p.x+30}, new int[] {p.y-75, p.y+75, p.y}, 3);

        /* Draw filled q3 region (negative) - q3 region intersects q1 and q2 regions */
        g2d.setColor(new Color(240, 240, 240));
        g2d.fill(q3);

        /* Draw the bounding strokes for each of those curves */
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.BLACK);
        g2d.draw(q1);
        g2d.draw(q2);
        g2d.draw(q3);

        /* Draw the inputs and outputs for the and gate */
        inputList.elementAt(0).draw(g, new Point(p.x-20, p.y-60));
        inputList.elementAt(1).draw(g, new Point(p.x-20, p.y-20));
        inputList.elementAt(2).draw(g, new Point(p.x-20, p.y+20));
        inputList.elementAt(3).draw(g, new Point(p.x-20, p.y+60));
        outputList.elementAt(0).draw(g, new Point(p.x+30, p.y));
    }
}
