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
 * ElementInterface provides a set of functions that should be implemented by
 * Element class and overridden by its sub classes.
 * 
 * First set of set Functions set the parameters in an element data structure
 * The set of get Functions get the values of the parameters in the element data structure
 * The last set of functions are overridden by each sub class of element
 * and each sub class should have its own implementation
 */

public interface ElementInterface {
    /*
     * set functions to set and modify member variables of an element
     */
    public void setElementID(int id);
    public void setElementType(String typeName);
    public void setElementName(String name);
    public void setNumInputs(int number);
    public void setNumOutputs(int number);
    public void addInput(Input inp);
    public void addOutput(Output out);
    public void setLocation(Point newLoc);
    public void setInbuiltCkt(Circuit newCkt);
    public void setInputAt(int ind, Input inp);
    public void setOutputAt(int ind, Output out);
    public void setprocess_state(boolean st);
    public void setgatedelay(int state);

    /*
     * get functions to read values of member variables of an element
     */
    public int getElementID();
    public String getElementType();
    public String getElementName();
    public int getNumInputs();
    public int getNumOutputs();
    public Vector<Input> getInputList();
    public Vector<Output> getOutputList();
    public Input getInputAt(int index);
    public Output getOutputAt(int index);
    public int getMaxIO();
    public Point getLocation();
    public Circuit getInbuiltCircuit();
    public boolean getprocess_state();
    public int getgatedelay();
    /*
     * Processing functions that are overridden by all the sub classes of class Element
     */
    public void updateLocation(Point p);
    public void updateMatrix(Point p, int[][] matrixType, int[][] matrixID, Point prev);
    public void processInputs();
    public void draw(Graphics g, Point p);

}
