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

/**
 *
 * @author rajesh, buddy
 * 
 * An Input is an independent data structure that feeds input to any element.
 * It is defined by an "inputID" and is located at "inputIndex" index to the element "ancestor"
 * An input is placed at a certain "location" wrto its parent element and holds an integral "dataValue"
 * The data value in the input can be probed by setting the probed flag.
 * Inputs can also have time varying values that are denoted by label functions that are user-defined.
 * 
 */
public class Input {

    /*
     * All the variables are private and can be read/written only using get/set functions defined below
     * 
     * private int inputID;                 id of the input - varies independent of the element to which it belongs.
     *                                      every input of every element in the circuit has a unique id
     * private int inputIndex;              index of the input among its several companions for its parent element
     * private Element ancestor;            parent element to which the input belongs to - to which it feeds its value
     * private int dataValue;               the integral data value that it holds for processing by the element
     * private Output sourceOutput;         the output node(if_not_null) from which it got its data value
     *                                      sourceOutput = null if the input is a circuit level input - has no predecessor outputs
     * private Point location;              downscaled location in circuit space where the input is located. Its wrt its parent
     *                                      The input is drawn in the circuit space using this location value.
     * private boolean probed;              a boolean flag that is set if the data value of the input is being probed
     * private boolean Input;         a boolean flag to detect if this input node is getting time-varying values or not
     * private String timePulseLabel;       the string value of the time label that denotes the function of time varying input
     * 
     */
    private int inputID;
    private int inputIndex;
    private Element ancestor;
    private int dataValue;
    private int newValue;
    private Output sourceOutput;
    private Point location;
    private boolean probed;
    private boolean timeVarying;
    private String timePulseLabel;
    private String name;
    /*
     * This constructor is used while loading a circuit
     * It is used without location as the location value is read at a later time
     * It creates an input with inputID(id), inputIndex(index) and sets its ancestor(parent)
     * The other member variables are set later using corresponding set functions
     * The sourceOutput is null as connections are not yet specified.
     * Since it is not probed initially, probed(false)
     */
    Input(int id, int index, Element parent) {
        inputID = id;
        inputIndex = index;
        dataValue = -1;
        newValue=-1;
        ancestor = parent;
        sourceOutput = null;
        location = new Point();
        probed = false;
        timeVarying = false;
        name="in";
    }

    Input(int id, int index, Element parent,String in_name) {
        inputID = id;
        inputIndex = index;
        dataValue = -1;
        newValue=-1;
        ancestor = parent;
        sourceOutput = null;
        location = new Point();
        probed = false;
        timeVarying = false;
        name=in_name;
    }

    /*
     * This constructor is called when a new element is created using circuit formation
     * It creates an input with inputID(id), inputIndex(index), sets its ancestor(parent)
     * and also sets its location(loc)
     * The data value is default set to -1 as connections and sourceOutput are not yet specified.
     * Initially, since it is not probed, probed(flase)
     */
    Input(int id, int index, Element parent, Point loc) {
        inputID = id;
        inputIndex = index;
        dataValue = -1;
        newValue=-1;
        ancestor = parent;
        sourceOutput = null;
        location = new Point(loc);
        probed = false;
        timeVarying = false;
        name="in";
    }

    Input(int id, int index, Element parent, Point loc,String in_name) {
        inputID = id;
        inputIndex = index;
        dataValue = -1;
        newValue=-1;
        ancestor = parent;
        sourceOutput = null;
        location = new Point(loc);
        probed = false;
        timeVarying = false;
        name=in_name;
    }

    /************************************************************************
     * Functions that set values/parameters for a particular input instance *
     ************************************************************************/
    /*
     * setAncestor(parent) sets the parent element of this input to parent
     */
    public void setAncestor(Element parent) {
        ancestor = parent;
    }

    /*
     * setDataValue(val) sets the data value that the input holds to val
     */
    public void setDataValue(int val) {
        dataValue = val;
    }

    public void setNewValue(int val) {
        newValue = val;
    }
    /*
     * setSourceOuptut(newOutput) sets the sourceOutput to newOutput
     * It means that the value in newOutput is passed on to this input
     */
    public void setSourceOutput(Output newOutput) {
        sourceOutput = newOutput;
    }

    /*
     * setLocation sets the location of this input to newLoc from its previous value
     * This function is called for every drag of its parent element
     */
    public void setLocation(Point newLoc) {
        location = newLoc;
    }

    /*
     * setInputIndex(ind) sets the inputIndex(ind)
     * It means that this input is the ind-th input to its parent element ancestor
     */
    public void setInputIndex(int ind) {
        inputIndex = ind;
    }

    public void setname(String in_name) {
        name=in_name;
    }

    /*
     * setProbed(state) sets the probed state to (state)
     * If probed is set, the data value at the input is being probed, and a label appears showing the value
     * If probed is false, then the value remains incognito - There is no label on the circuit
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

    /*
     * setTimePulseLabel(label) sets the timePulseLabel(label)
     * label denotes a time varying input function that is fed to the input
     * The data value in the input varies with it.
     */
    public void setTimePulseLabel(String label) {
        if (label != null) {
            timeVarying = true;
        } else {
            timeVarying = false;
        }
        timePulseLabel = label;
    }

    /*************************************************************************
     * Functions that gets values/parameters for a particular input instance *
     *************************************************************************/
    /*
     * getInputID() returns an integral value of the id of the current input
     */
    public int getInputID() {
        return inputID;
    }

    /*
     * getAncestor() returns the element ancestor i.e. the parent element
     * to which it belongs
     */
    public Element getAncestor() {
        return ancestor;
    }

    /*
     * getDataValue() returns the dataValue that it holds 
     * and gives to its parent element for processing
     */
    public int getDataValue() {
        return dataValue;
    }

    public int getNewValue() {
        return newValue;
    }

    /*
     * getSourceOutputNode() returns the output node data structure from which
     * this input gets its data value
     * If it returns null, then it has no predecessor and its also a circuit level input
     */
    public Output getSourceOutputNode() {
        return sourceOutput;
    }

    /*
     * getLocation() returns a Point data structure denoting the location in circuit space
     * where the input is located. It is used while drawing the input or saving the circuit
     */
    public Point getLocation() {
        return location;
    }

    /*
     * getInputIndex() returns an integral value of the index of the input in its ancestor's
     * list of inputs. It is used for reading the inputIndex value
     */
    public int getInputIndex() {
        return inputIndex;
    }


     public String getname() {
        return name;
    }

    /*
     * isProbed() returns true if this input is being probed, else false
     */
    public boolean isProbed() {
        return probed;
    }

    /*
     * isTimeVarying() returns true if the value at this input varies with time
     */
    public boolean isTimeVarying() {
        return timeVarying;
    }

    /*
     * getTimePulseLabel() returns a string label that denotes the time varying function that
     * the input uses to change its value while feeding the value to its ancestor element
     */
    public String getTimePulseLabel() {
        return timePulseLabel;
    }

    /*************************************************************************
     *                 Draw Function draws the input instance                *
     *************************************************************************/
    /*
     * draw(g, p) function is used to draw the input at location p
     */
    public void draw(Graphics g, Point p) {
        Graphics2D g2d = (Graphics2D) g;

        /* Upscale the location of the input */
        Point upLoc = mainPanel.upScale(location);

        /* draw the input line that connects the input to its parent element */
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(upLoc.x, upLoc.y, p.x, p.y);

        /* draw the red color rectangle that denotes an input node */
        g2d.setColor(Color.RED);
        g2d.fillRect(upLoc.x - 5, upLoc.y - 5, 10, 10);

        /* 
         * If the input is being probed and is not a circuit level input
         * draw a label showing the dataValue that the input holds
         * If the input is not yet processed, then it shows X, else its dataValue
         */
        if (probed && sourceOutput != null) {
            Point loc = new Point(this.getLocation());
            int val = this.getDataValue();
            if (this.getInputIndex() < this.getAncestor().numInputs / 2) {
                loc.y = loc.y - 1;
            } else {
                loc.y = loc.y + 1;
            }
            loc = mainPanel.upScale(new Point(loc));
            g2d.setColor(Color.orange);
            g2d.fillRect(loc.x - 30, loc.y - 10, 40, 20);
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
            g2d.drawString(name, loc.x - 30, loc.y + 6);
        }

        /*
         * If the input is a circuit level input, draw a label showing
         * X                - if the input value is not yet set
         * 0/1              - if either of the values are set at that input location
         * timePulseLabel   - if any specific user-defined time pulse is attached to it 
         */
        if (sourceOutput == null) {
            int val = this.getDataValue();
            Point loc = new Point(this.getLocation());
            loc.x = loc.x - 1;
            loc = mainPanel.upScale(new Point(loc));
            g2d.setColor(Color.green);
            g2d.fillRect(loc.x - 30, loc.y - 10, 40, 20);
            g2d.setColor(Color.BLACK);
            Font font = new Font("Times New Roman", Font.BOLD, 18);
            g2d.setFont(font);
            if (timePulseLabel != null) {
                g2d.drawString(timePulseLabel, loc.x - 4, loc.y + 6);
            } else if (val != -1) {
                g2d.drawString(Integer.toString(val), loc.x - 4, loc.y + 6);
            } else {
                g2d.drawString("X", loc.x - 4, loc.y + 6);
            }
            g2d.setColor(Color.blue);
             g2d.drawString(name+" ", loc.x - 30, loc.y + 6);
        }
    }
}
