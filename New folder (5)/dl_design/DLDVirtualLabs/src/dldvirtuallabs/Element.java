/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dldvirtuallabs;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

/**
 * 
 * @author rajesh, buddy
 * 
 * Element Class implements ElementInterface
 * An element is any individual freely existing processing item in a circuit. 
 * It has its own logic and functionality. An element will have a certain number
 * of inputs and outputs. An element processes its inputs, populates its output 
 * nodes and their values can be passed on to the next level elements.
 * 
 * Element is the parent class for every other class like
 * And_Gate, Or_Gate, Not_Gate, Nand_Gate, Nor_Gate, Xor_Gate, Xnor_Gate, Generic_Element
 * 
 */

public class Element implements ElementInterface {
    
    /*
     * All are protected variables that can be accessed only with get and set functions. 
     * None of them can be modified directly by external functions, but are accessible by child classes.
     * 
     * protected int elementID;                 ID of the element [0,1,2...]
     * protected String elementName;            Name of the element [Element_Type{ID}]
     * protected String elementType;            Type of the element [And_Gate/Or_Gate..]
     * protected int numInputs;                 Number of Inputs for the element [1/2/3...]
     * protected int numOutputs;                Number of Outputs for the element [1/2/3...]
     * protected int maxIO;                     Defines the max. extension on either side for a generic element
     *                                          maxIO = Math.max(numInputs, numOutputs) + 1;
     * protected int width;                     width of the bounding box while drawing the element
     * protected int height;                    height of the bounding box while drawing the element
     * protected Vector<Input> inputList;       List of all the inputs to the element in index order - Data structure stored here
     * protected Vector<Output> outputList;     List of all the outputs to the element in index order - Data structure stored here
     * protected Point location;                Downscaled Location (coordinates) where the element is placed in the circuit space
     *                                          Also maps directly to the corresponding indices for the matrixType and matrixID
     * protected Circuit inbuiltCkt;            Circuit data structure that exists internal to a Generic element
     *                                          Not necessary for a regular gate - used only for Generic elment
     */
    
    
    protected int elementID;
    protected String elementName;
    protected String elementType;
    protected int numInputs;
    protected int numOutputs;
    protected int maxIO;
    protected int width;
    protected int height;
    protected int delay;
    protected Vector<Input> inputList;
    protected Vector<Output> outputList;
    protected Point location;
    protected Circuit inbuiltCkt;
    protected boolean process_state;

    /*
     * Null Constructor - Used while loading a previously saved circuit.
     * Overridden by child class constructors
     */
    Element() {
        elementID = -1;
        elementName = "NullElement";
        elementType = "NullType";
        numInputs = 0;
        numOutputs = 0;
        maxIO = 0;
        width = 0;
        height = 0;
        delay=-1;
        inputList = new Vector<Input>();
        outputList = new Vector<Output>();
        location = new Point();
        process_state=true;
    }

    /*
     * Generic Constructor that creates an element with elementID as "id", elementType as "type",
     * "numInp" inputs starting with "inpID", "numOut" outputs starting with "outID"
     * Element is placed at downscaled location "loc"
     */
    Element(int id, String type, int inpID, int numInp, int outID, int numOut, Point loc) {
        elementID = id;
        process_state=true;
        delay=-1;
        elementType = type;
        elementName = type+Integer.valueOf(id);
        numInputs = numInp;
        numOutputs = numOut;
        maxIO = Math.max(numInputs, numOutputs) + 1;
        width = 2*(20*maxIO);
        height = 2*(20*maxIO);
        location = new Point(loc);
        inputList = new Vector<Input>();
        outputList = new Vector<Output>();
        for(int i=0; i<numInp; i++)
            inputList.add(new Input(inpID+i, i, this, new Point(loc)));
        for(int i=0; i<numOut; i++)
            outputList.add(new Output(inpID+i, i, this, new Point(loc)));
    }

    /**************************************************************************
     * Functions that set values/parameters for a particular element instance *
     **************************************************************************/
     
    /*
     * setNumInputs(inpCount) sets the number of inputs to inpCount
     * This function is used during loadCircuit when the numInputs is specified
     */
  public boolean getprocess_state() {
        return process_state;
    }
public void setprocess_state(boolean st) {
        process_state = st;
    }

    public void setNumInputs(int inpCount) {
        numInputs = inpCount;
    }

    /*
     * setElementID(id) sets the elementID of the element
     * This function is used during loadCircuit when the null constructor is called
     */
    public void setElementID(int id) {
        elementID = id;
    }

    public void setgatedelay(int d) {
        delay = d;
    }
    
    /*
     * setElementType(typeName) sets the type of the element
     * This function is used during loadCircuit when the null constructor is called
     */
    public void setElementType(String typeName) {
        elementType = typeName;
    }
        
    /*
     * setElementName(name) sets the name of the element scanned from the ckt file
     * This function is used during loadCircuit when the null constructor is called
     */
    public void setElementName(String name) {
        elementName = name;
    }

    /*
     * setNumOuptuts(outCount) sets the number of outputs for the element
     * This function is used during loadCircuit when the null constructor is called
     */
    public void setNumOutputs(int outCount) {
        numOutputs = outCount;
    }

    /*
     * setInputAt(ind, inp) sets "inp" as the input at index "ind"
     * This function is used during loadCircuit when the null constructor is called
     */
    public void setInputAt(int ind, Input inp) {
        inputList.setElementAt(inp, ind);
    }

    /*
     * setOutputAt(ind, out) sets "out" as the input at index "ind"
     * This function is used during loadCircuit when the null constructor is called
     */
    public void setOutputAt(int ind, Output out) {
        outputList.setElementAt(out, ind);
    }

    /*
     * addInput(inpNode) adds "inpNode" as an input to the list of existing inputs to the element
     * This function is used during loadCircuit when the null constructor is called
     */
    public void addInput(Input inpNode) {
        numInputs = numInputs + 1;
        inputList.add(inpNode);
    }

    /*
     * addOutput(outNode) adds "outNode" as an output to the list of exisiting outputs to the element
     * This function is used during loadCircuit when the null constructor is called
     */
    public void addOutput(Output outNode) {
        numOutputs = numOutputs + 1;
        outputList.add(outNode);
    }

    /*
     * setLocation(newPoint) shift the existing location of the element to newPoint
     * This function is called with every movement(drag) of the element in circuit space
     */
    public void setLocation(Point newPoint) {
        location = newPoint;
    }
    
    /*
     * setInbuiltCkt(newCkt) sets "newCkt" as the inbuiltCkt for a generic element
     * This function is called when an exisitng circuit file is imported as an element
     * It is also called when a generic elment is a part of a saved circuit and is loaded back.
     */
    public void setInbuiltCkt(Circuit newCkt) {
        inbuiltCkt = new Circuit(newCkt);
        maxIO = Math.max(newCkt.getNumInputs(), newCkt.getNumOutputs()) + 1;
        width = 2*(20*maxIO);
        height = 2*(20*maxIO);
        System.out.println("Inside setInbuiltCkt : maxIO : " + maxIO);
    }

    
    /***************************************************************************
     * Functions that gets values/parameters for a particular element instance *
     ***************************************************************************/
     
    /*
     * getElementID() returns an integer value specifying 
     * the ID of the current element (this)
     */
    public int getElementID() {
        return elementID;
    }
     public int getgatedelay() {
        return delay;
    }
    
    /*
     * getElementType returns a string value specifying 
     * the type of the current Element (this)
     */
    public String getElementType() {
        return elementType;
    }
    
    /*
     * getElementName() returns a string value specifiying 
     * the name of the current Element (this)
     */
    public String getElementName() {
        return elementName;
    }

    /*
     * getNumInputs() returns the an integer value specifying the 
     * number of inputs for the current Element(this)
     */
    public int getNumInputs() {
        return numInputs;
    }

    /*
     * getNumOutputs() returns an integer value specifying the 
     * number of outputs for the current Element(this)
     */
    public int getNumOutputs() {
        return numOutputs;
    }

    /*
     * getMaxIO() retuns an integer value specifying the value of maxIO
     * (maxIO-1) denotes the max. among the number of inputs and outputs
     * This is useful to identify the bounding box for drawing a generic element
     */
    public int getMaxIO() {
        return maxIO;
    }
    
    /*
     * getLocation() returns the downscaled location of the current element(this)
     * This also equals the (location.y, location.x) indexes in matrixType, matrixID matrices of the circuit
     */
    public Point getLocation() {
        return location;
    }
    
    /*
     * getInputList() returns a list of all the inputs for the current element (this)
     */
    public Vector<Input> getInputList() {
        return inputList;
    }

    /*
     * getInputAt(index) returns the input at index "index" for the current element (this)
     */
    public Input getInputAt(int index) {
        return inputList.elementAt(index);
    }

    /*
     * getOutputList() returns a list of all outputs for the current element (this)
     */
    public Vector<Output> getOutputList() {
        return outputList;
    }

    /*
     * getOuptutAt(index) returns the output node at index "index" for the current element (this)
     */
    public Output getOutputAt(int index) {
        return outputList.elementAt(index);
    }

    /*
     * getInbuiltCircuit() returns the internal circuit for a generic element
     */
    public Circuit getInbuiltCircuit() {
        return inbuiltCkt;
    }
    
    
    /*************************************************************************
     *        Overridden Functions - Implemented by child classes            *
     * They have to be implemented compulsorily by each of its child classes *
     *************************************************************************/
    
    /*
     * processInputs() implements the internal logic of the element (this)
     * It processes the populated inputs and populates the outputs.
     * These outputs can be passed on to next level.
     */
    public void processInputs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * updateLocation(p) updates the location of the current element to Point p.
     * The function also updates the locations of all its input and output nodes simulataneously.
     * This function is called at ever element Drag step.
     */
    public void updateLocation(Point p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * updateMatrix(p, matrixType, matrixID, prev) updates the matrixID and matrixType matrices
     * that define the circuit space matrix. 
     * This function is called only at mouseRelease after a drag -
     * when the current element (this) is moved form Point "prev" to Point "p"
     * matrixID and matrixType of location "prev" are made 0
     * matrixID and matrixType of location "p" are updated.
     * The function also updates the locations of all its input and output nodes simulataneously.
     */
    public void updateMatrix(Point p, int[][] matrixType, int[][] matrixID, Point prev) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * draw(g, p) draws the element using Java 2D graphics with point p as center location.
     */
    public void draw(Graphics g, Point p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
