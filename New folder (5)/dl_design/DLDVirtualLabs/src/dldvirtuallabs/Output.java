/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dldvirtuallabs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

/**
 *
 * @author rajesh, buddy
 * 
 * An Output is an independent data structure that a processing element passes on values after processing inputs
 * It is defined by an "outputID" and is located at "outputIndex" index to the element "ancestor"
 * An output is placed at a certain "location" wrto its parent element and holds an integral "dataValue"
 * The data value in the output can be probed by setting the probed flag.
 * Outputs can also be time varying values that are denoted by label functions that are user-defined.
 * These time varying values depend on the time varying inputs that its parent element receives
 * 
 */
public class Output {

    /*
     * All the variables are private and can be read/written only using get/set functions defined below
     * 
     * private int outputID;                        id of the output - varies independent of the element to which it belongs.
     *                                              every output of every element in the circuit has a unique id
     * private int outputIndex;                     index of the output among its several companions for its parent element
     * private Element ancestor;                    parent element to which the output belongs to - to which it feeds its value
     * private int dataValue;                       the integral data value that it holds after processing by the element
     * private Vector<Input> destinationInputs;     A vector of input nodes to which it passes on its data value
     *                                              These inputs belong to elements to which this output is connected in the circuit
     * private Vector<Integer> destInpIds;          A vector of all the ids of input nodes to which this data value is fed
     *                                              It stores the ids of all the output nodes specified in the vector above
     *                                              This is used while loading a saved circuit
     * private Point location;                      downscaled location in circuit space where the output is located. Its wrt its parent
     *                                              The output is drawn in the circuit space using this location value.
     * private boolean probed;                      a boolean flag that is set if the data value of the output is being probed
     * private String timePulseLabel;               the string value of the time label that denotes the function of time varying output
     * 
     */
    private int outputID;
    private int outputIndex;
    private int dataValue;
    private int statevalue;
    private Element ancestor;
    private Vector<Input> destinationInputs;
    private Vector<Integer> destInpIds;
    private Point location;
    private boolean probed;
    private boolean timeVarying;
    private String name;

    /*
     * This constructor is used while loading a circuit
     * It is used without location as the location value is read at a later time
     * It creates an output with outputID(id), outputIndex(index) and sets its ancestor(parent)
     * The other member variables are set later using corresponding set functions
     * The destinationInputs vector is empty as connections are not yet specified.
     * Since it is not probed initially, probed(false)
     */
    Output(int id, int index, Element parent) {
        outputID = id;
        outputIndex = index;
        dataValue = -1;
        statevalue=0;
        ancestor = parent;
        destinationInputs = new Vector<Input>();
        destInpIds = new Vector<Integer>();
        location = new Point();
        probed = false;
        timeVarying = false;
        name="out";
    }


     Output(int id, int index, Element parent,String out_name) {
        outputID = id;
        outputIndex = index;
        dataValue = -1;
        statevalue=0;
        ancestor = parent;
        destinationInputs = new Vector<Input>();
        destInpIds = new Vector<Integer>();
        location = new Point();
        probed = false;
        timeVarying = false;
        name=out_name;
    }

    /*
     * This constructor is called when a new element is created using circuit formation
     * It creates an output with outputID(id), outputIndex(index), sets its ancestor(parent)
     * and also sets its location(loc)
     * The data value is default set to -1 as connections and destinationInputs are not yet specified.
     * Initially, since it is not probed, probed(flase)
     */
    Output(int id, int index, Element parent, Point loc) {
        outputID = id;
        outputIndex = index;
        dataValue = -1;
        statevalue=0;
        ancestor = parent;
        destinationInputs = new Vector<Input>();
        destInpIds = new Vector<Integer>();
        location = new Point(loc);
        probed = false;
        timeVarying = false;
        name="out";
    }


    Output(int id, int index, Element parent, Point loc,String out_name) {
        outputID = id;
        outputIndex = index;
        dataValue = -1;
        statevalue=0;
        ancestor = parent;
        destinationInputs = new Vector<Input>();
        destInpIds = new Vector<Integer>();
        location = new Point(loc);
        probed = false;
        timeVarying = false;
        name=out_name;
    }

    /*************************************************************************
     * Functions that set values/parameters for a particular output instance *
     *************************************************************************/

    /*
     * setDataValue(val) sets the data value that the output holds to val
     */
    public void setDataValue(int val) {
        dataValue = val;
    }
 public void setstatevalue(int val) {
        statevalue=val; 
    }
    /*
     * setAncestor(parent) sets the parent element of this output to parent
     */
    public void setAncestor(Element parent) {
        ancestor = parent;
    }

    /*
     * addDestInpId(id) function is called while loading a circuit
     * The ckt file stores a list of ids of all the inputs to which this output is connected
     * Hence, storing that list in destInpIds helps later 
     * to add actual input nodes to the destinationInputs vector
     */
    public boolean addDestInpId(int id) {
        if (!destInpIds.contains(id)) {
            destInpIds.add(id);
            return true;
        }
        return false;
    }

    /*
     * addDestinationInput(destInput) adds destInput to its list of connected inputs
     * to which it feeds forward its value after its ancestor has been processed.
     */
    public void addDestinationInput(Input destInput) {
        destinationInputs.add(destInput);
    }

    /*
     * setLocation(newLoc) sets the location of the output to newLoc from its previous value
     * It is called for every drag of its parente element
     */
    public void setLocation(Point newLoc) {
        location = newLoc;
    }

    /*
     * setOutputIndex(ind) sets the index of the current output for its ancestor element
     * It means that this output is the ind-th output of its parent element
     */
    public void setOutputIndex(int ind) {
        outputIndex = ind;
    }

     public void setname(String out_name) {
        name=out_name;
    }

    /*
     * setProbed(state) sets the probing state for this output
     * If set, then a label appears on the circuit space next to this output
     * showing the value that it holds after its parent element has been processed
     */
    public void setProbed(boolean state) {
        probed = state;
    }

    /*
     * setTimeVarying(state) sets the timeVarying state to (state)
     * If timeVarying is set, the data value varies with time
     * If timeVarying is false, then the value remains static
     */
    public void setTimeVarying(boolean state) {
        timeVarying = state;
    }

    /**************************************************************************
     * Functions that gets values/parameters for a particular output instance *
     **************************************************************************/
    /*
     * returns the vector containing integral values of ids of 
     * the input nodes to which its value is fed to at the next level
     * For a circuit level output, this list is empty
     */
    public Vector<Integer> getDestInpIdsList() {
        return destInpIds;
    }

    /*
     * getOutputID() returns an integral value of the id of the current output
     */
    public int getOutputID() {
        return outputID;
    }

    /*
     * getDataValue() returns the dataValue that it holds 
     * and gives to its parent element for processing
     */
    public int getDataValue() {
        return dataValue;
    }
    public int getstatevalue() {
        return statevalue;
    }
    /*
     * getAncestor() returns the element ancestor i.e. the parent element
     * to which it belongs
     */
    public Element getAncestor() {
        return ancestor;
    }


      public String getname() {
        return name;
    }

    /*
     * getDestinationInputList() returns a vector containing a list of input nodes
     * to which this output is connected and passes on its data value
     * This vector is empty in case of outptus at the circuit level 
     * i.e. those which are not connected to any inputs
     */
    public Vector<Input> getDestinationInputList() {
        return destinationInputs;
    }

    /*
     * getLocation() returns a Point data structure denoting the location in circuit space
     * where the input is located. It is used while drawing the input or saving the circuit
     */
    public Point getLocation() {
        return location;
    }

    /*
     * getOutputIndex() returns an integral value of the index of the output in its ancestor's
     * list of inputs. It is used for reading the outputIndex value
     */
    public int getOutputIndex() {
        return outputIndex;
    }

    /*
     * isProbed() returns true if this output is being probed, else false
     */
    public boolean isProbed() {
        return probed;
    }

    /*
     * isTimeVarying() returns true if the value at this output varies with time
     */
    public boolean isTimeVarying() {
        return timeVarying;
    }

    /*
     * delDestInpNode(delDestInput) removes delDestInput from the list of its destination inputs
     * This function is called when a connection is removed between any input and this output
     */
    public boolean delDestInpNode(Input delDestInput) {
        if (destInpIds.contains(delDestInput.getInputID())) {
            destInpIds.removeElement(delDestInput.getInputID());
            destinationInputs.remove(delDestInput);
            return true;
        }
        return false;
    }

    /*************************************************************************
     *                 Draw Function draws the input instance                *
     *************************************************************************/
    /*
     * draw(g, p) function is used to draw the output at location p
     */
    public void draw(Graphics g, Point p) {
        Graphics2D g2d = (Graphics2D) g;

        /* Upscale the location of the output */
        Point upLoc = mainPanel.upScale(location);

        /* draw the output line that connects the output to its parent element */
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(upLoc.x, upLoc.y, p.x, p.y);

        /* draw the red color rectangle that denotes an output node */
        g2d.setColor(Color.RED);
        g2d.fillRect(upLoc.x - 5, upLoc.y - 5, 10, 10);

        /* 
         * If the output is being probed and is not a circuit level output
         * draw a label showing the dataValue that the output holds
         * If the output is not yet processed, then it shows X, else its dataValue
         */
        if (probed && !destinationInputs.isEmpty()) {
            Point loc = new Point(this.getLocation());
            int val = this.getDataValue();
            if (this.getOutputIndex() == 0) {
                loc.y = loc.y - 1;
            } else {
                loc.y = loc.y + 1;
            }
            loc = mainPanel.upScale(new Point(loc));
            g2d.setColor(Color.orange);
            g2d.fillRect(loc.x-3, loc.y - 10, 40, 20);
            g2d.setColor(Color.BLACK);
            Font font = new Font("Times New Roman", Font.BOLD, 18);
            g2d.setFont(font);
            if (timeVarying) {
                g2d.drawString("-", loc.x - 4, loc.y + 6);
            } else if (val == -1) {
                g2d.drawString("X", loc.x - 4, loc.y + 6);
            } else {
                g2d.drawString(Integer.toString(val), loc.x - 4, loc.y + 6);
            }
             g2d.setColor(Color.blue);
             g2d.drawString(name+" ", loc.x + 10, loc.y + 6);
        }

        /*
         * If the output is a circuit level output, draw a label showing
         * X                - if the output value is not yet set
         * 0/1              - if either of the values are set at that output location
         * timePulseLabel   - if any specific user-defined time pulse is attached to it 
         */
        if (destinationInputs.isEmpty()) {
            int val = this.getDataValue();
            Point loc = new Point(this.getLocation());
            loc.x = loc.x + 1;
            loc = mainPanel.upScale(new Point(loc));
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(loc.x-3 , loc.y - 10, 40, 20);
            g2d.setColor(Color.BLACK);
            Font font = new Font("Times New Roman", Font.BOLD, 18);
            g2d.setFont(font);
            if (timeVarying) {
                g2d.drawString("-", loc.x - 4, loc.y + 6);
            } else if (val == -1) {
                g2d.drawString("X", loc.x - 4, loc.y + 6);
            } else {
                g2d.drawString(Integer.toString(val), loc.x - 4, loc.y + 6);
            }
             g2d.setColor(Color.blue);
            g2d.drawString(name+" ", loc.x + 10, loc.y + 6);
        }
    }
}
