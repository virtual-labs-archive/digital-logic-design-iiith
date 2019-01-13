/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dldvirtuallabs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author rajesh, buddy
 *
 * A circuit is a collection of interconnected processing elements each which has
 * a certain number of inputs and outputs. It has an integrated logic that cascades
 * across elements connected to each other. Input Values can be specified at circuit level inputs
 * and simulated to see output values at circuit level outputs.
 * Each of the constituent elements are processed when all its input values are available
 * and the output values are passed on via connections to next level inputs.
 */
public class Circuit {
    /*
     * private int circuitID;                               id of the circuit - initially one
     *                                                      until a specific circuit is loaded from a file
     * private String circuitName;                          Name of the circuit - generally it is "Circuit<CircuitID>"
     * private int numInputs;                               Number of inputs to the circuit - at circuit level (if treated as a black box)
     * private int numOutputs;                              Number of outputs to the circuit - at circuit level (if treated as a black box)
     * private Vector<Input> circuitInputsList;             Vector of circuit level input nodes - predecessor outputs are null for them
     * private Vector<Output> circuitOutputsList;           Vector of circuit level output nodes - destination inputs are null for them
     * private HashSet<Element> inputElementSet;            Vector of elements that occur at level 0 of the circuit
     *                                                      - to which atleast one circuit level input is attached
     * private HashSet<Element> outputElementSet;           Vector of elemenets that occur at the last level of the circuit
     *                                                      - to which atleast one circuit level output is attached
     * public int[][] meshType;                             Matrix that stores what type of element exists at each point in the circuit space
     * public int[][] meshID;                               Matrix that stores the id of the element/input/output/-1 at each point in circuit space
     * public HashMap<Integer, Element> allElementsList;    List of all the constituent elements in the circuit
     * public HashMap<Integer, Input> allInputsList;        List of all the constituent inputs in the circuit
     * public HashMap<Integer, Output> allOutputsList;      List of all the constituent outputs in the circuit
     */

    private int circuitID;
    private String circuitName;
    public int numInputs;
    public int numOutputs;
    public Vector<Input> circuitInputsList;
    public Vector<Output> circuitOutputsList;
    private HashSet<Element> inputElementSet;
    private HashSet<Element> outputElementSet;
    public int[][] meshType;
    public int[][] meshID;
    public HashMap<Integer, Element> allElementsList;
    public HashMap<Integer, Input> allInputsList;
    public HashMap<Integer, Output> allOutputsList;

    /*
     * Null Constructor is used while loading a circuit
     * All member variables are initialized to default values
     * They are later populated or set with set functions
     */
    Circuit() {
        circuitID = 0;
        circuitName = "New Circuit";
        numInputs = 0;
        numOutputs = 0;
        circuitInputsList = new Vector<Input>();
        circuitOutputsList = new Vector<Output>();
        inputElementSet = new HashSet<Element>();
        outputElementSet = new HashSet<Element>();
        allElementsList = new HashMap<Integer, Element>();
        allInputsList = new HashMap<Integer, Input>();
        allOutputsList = new HashMap<Integer, Output>();
        meshType = new int[1024][768];
        meshID = new int[1024][768];
    }

    /*
     * This constructor creates a circuit with circuitID(id) and circuitName(name)
     * It is called when the applet is launched i.e. a new circuit is created
     * Initially, all the vectors are empty and numInputs, numOutputs are 0
     */
    Circuit(int id, String name) {
        circuitID = id;
        circuitName = name;
        numInputs = 0;
        numOutputs = 0;
        circuitInputsList = new Vector<Input>();
        circuitOutputsList = new Vector<Output>();
        inputElementSet = new HashSet<Element>();
        outputElementSet = new HashSet<Element>();
        allElementsList = new HashMap<Integer, Element>();
        allInputsList = new HashMap<Integer, Input>();
        allOutputsList = new HashMap<Integer, Output>();
        meshType = new int[1024][768];
        meshID = new int[1024][768];
    }

    /*
     * This copy constructor creats another copy of an existing circuit.
     * All the values of member variables are copied from orgCkt to the calling circuit instance
     * The vectors are also wholly copied.
     */
    Circuit(Circuit orgCkt) {
        circuitID = orgCkt.getCircuitID();
        circuitName = orgCkt.getCircuitName();
        numInputs = orgCkt.getNumInputs();
        numOutputs = orgCkt.getNumOutputs();
        circuitInputsList = new Vector<Input>(orgCkt.getCircuitInputsList());
        circuitOutputsList = new Vector<Output>(orgCkt.getCircuitOutputsList());
        inputElementSet = new HashSet<Element>(orgCkt.getInputElementsSet());
        outputElementSet = new HashSet<Element>(orgCkt.getOutputElementsSet());
        allElementsList = new HashMap<Integer, Element>(orgCkt.allElementsList);
        allInputsList = new HashMap<Integer, Input>(orgCkt.allInputsList);
        allOutputsList = new HashMap<Integer, Output>(orgCkt.allOutputsList);
       // meshType = new int[1024][768];
       // meshID = new int[1024][768];
    }

    /**************************************************************************
     * Functions that set values/parameters for a particular circuit instance *
     **************************************************************************/

    /*
     * setCircuitName(name) sets the circuitName(name)
     */
    public void setCiruitName(String name) {
        circuitName = name;
    }

    /*
     * setNumInputs(number) sets the numInputs(number)
     * It is called while loading a circuit from a saved file
     */
    public void setNumInputs(int number) {
        numInputs = number;
    }

    /*
     * setNumOutputs(number) sets numOutputs(number)
     * It is called while loading a circuit from a saved file
     */
    public void setNumOutputs(int number) {
        numOutputs = number;
    }

    /*
     * addElement(newElement) adds newElement to the list of all the constituent elements
     * Along with this, the inputs and outputs of newElement are added to corresponding lists
     */
    public void addElement(Element newElement) {
        allElementsList.put(newElement.getElementID(), newElement);
        for (int i = 0; i < newElement.getNumInputs(); i++) {
            allInputsList.put(newElement.getInputAt(i).getInputID(), newElement.getInputAt(i));
        }
        for (int i = 0; i < newElement.getNumOutputs(); i++) {
            allOutputsList.put(newElement.getOutputAt(i).getOutputID(), newElement.getOutputAt(i));
        }
    }

    /*
     * setMeshValues(xIndex, yIndex, nodeType, nodeId) sets the meshType and meshID matrices
     * at (yIndex, xIndex) to nodeType and nodeID values
     */
    public void setMeshValues(int xIndex, int yIndex, int nodeType, int nodeId) {
        meshType[yIndex][xIndex] = nodeType;
        meshID[yIndex][xIndex] = nodeId;
    }

    /***************************************************************************
     * Functions that gets values/parameters for a particular circuit instance *
     ***************************************************************************/

    /*
     * getCircuitID() returns an integral value of this circuit
     */
    public int getCircuitID() {
        return circuitID;
    }

    /*
     * getCircuitName() returns a string value of the name of the circuit
     */
    public String getCircuitName() {
        return circuitName;
    }

    /*
     * getNumInputs() returns an integral value of circuit level inputs
     * i.e. inputs for the entire circuit - if treated as a black box
     */
    public int getNumInputs() {
        return numInputs;
    }

    /*
     * getNumOutputs() returns an integral value of circuit level outputs
     * i.e. outputs for the entire circuit - if treated as a black box
     */
    public int getNumOutputs() {
        return numOutputs;
    }

    /*
     * getCircuitInputsList() returns a vector of all the circuit level inputs
     * i.e. input nodes to which circuit input values are fed
     */
    public Vector<Input> getCircuitInputsList() {
        return circuitInputsList;
    }

    /*
     * getCircuitOutputsList() returns a vector of all the circuit level outputs
     * i.e. output nodes of the circuit from where output values are received
     */
    public Vector<Output> getCircuitOutputsList() {
        return circuitOutputsList;
    }

    /*
     * getCircuitInputAt(ind) returns the ind-th input node of the circuit
     * returns the ind-th circuit level input
     */
    public Input getCircuitInputAt(int ind) {
        return circuitInputsList.elementAt(ind);
    }

    /*
     * getCircuitOutputAt(ind) returns the ind-th output node of the circuit
     * returns the ind-th circuit level output
     */
    public Output getCircuitOutputAt(int ind) {
        return circuitOutputsList.elementAt(ind);
    }

    /*
     * getCircuitInputElementSet() returns a set of elements that have
     * atleast one circuit level input
     */
    public HashSet<Element> getInputElementsSet() {
        return inputElementSet;
    }

    /*
     * getCircuitOutputElementSet() returns a set of elements that have
     * atleast one circuit level output
     */
    public HashSet<Element> getOutputElementsSet() {
        return outputElementSet;
    }

    /*
     * getAllElementsList() returns a list of all the constituent elements of the circuit
     */
    public HashMap<Integer, Element> getAllElementsList() {
        return allElementsList;
    }

    /*
     * identifyCktInputsOutputs() function identifies circuit level inputs and outputs
     * among all the inputs and outputs of all the constituent elements
     */
    public void identifyCktInputsOutputs() {
        /*
         * Clear all the lists that existed till now and reinitialize variables
         */
        circuitInputsList.clear();
        circuitOutputsList.clear();
        inputElementSet.clear();
        outputElementSet.clear();
        numInputs = 0;
        numOutputs = 0;

        /*
         * Iterating through all the inputs, if an input has no source output node (null)
         * i.e. it gets its data value directly from the user and not connected to any previous output,
         * add this input node as a circuit level input node
         */
        for (Iterator it = allInputsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            Input inp = (Input) allInputsList.get((Integer) key);
            if (inp.getSourceOutputNode() == null) {             // Circuit Input Node
                numInputs = numInputs + 1;
                circuitInputsList.add(inp);
                
                inputElementSet.add(inp.getAncestor());

            }
        }

        /*
         * Iterating through all the outputs, if an output has an empty destinationInputs list
         * i.e. it does not pass its data value to any other inputs and directly gives to the user,
         * add this output node as a circuit level output node
         */
        for (Iterator it = allOutputsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            Output out = (Output) allOutputsList.get((Integer) key);
            if (out.getDestinationInputList().isEmpty() || out.getstatevalue()==1) {              // Circuit Input Node
                numOutputs = numOutputs + 1;
                circuitOutputsList.add(out);
                outputElementSet.add(out.getAncestor());
            }
        }
    }

    /*
     * processCircuit(genericFlag) processes this circuit given all the necessary inputs and
     * populates the final values in its circuit level output nodes
     * If genericFlag is set, the inbuilt Circuit in a generic element wil be processed
     */
    public boolean processCircuit(boolean genericFlag) {
             /*
         * If it is not a generic element and is a normal circuit, identify the circuit level
         inputs and outputs to start processing from circuit level input elements
         */
//System.out.println("krishna");
        if (!genericFlag) {
            identifyCktInputsOutputs();
        }
        for (Iterator it = circuitInputsList.iterator(); it.hasNext();) {
            if (((Input) it.next()).getDataValue() == -1) { // Insufficient Inputs to process circuit
                return false;
            }
        }
        if (!genericFlag) {
            identifyCktInputsOutputs();
        for (Iterator it = inputElementSet.iterator(); it.hasNext();) {

        Element ielement=(Element) it.next();
        Queue<Element> processedset = new LinkedList<Element>();
        if(ielement.getgatedelay()<=0)
        {
            ielement.setgatedelay(1);
        }
        Stack<Element> sk=new Stack<Element>();
        sk.push(ielement);
        while(!sk.empty())
        {
         Element currElement = (Element) sk.pop();
         processedset.add(currElement);
         for (int i = 0; i < currElement.getNumOutputs(); i++) {          // Pass on the outputs to next level inputs and add those into the set

                    for (int j = 0; j < currElement.getOutputAt(i).getDestinationInputList().size(); j++) {
                if(!processedset.contains( currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor()) && !sk.contains( currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor()))
                        {
                   


                        sk.add(currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor());
                        if(currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor().getgatedelay()<=currElement.getgatedelay()+1)
                        {
                            currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor().setgatedelay(currElement.getgatedelay()+1);
                        }

                    }
                }}
        }
        }
            
        }

        /*
         * If there are any inptus without data values(-1), then the circuit cannot be processed
         * return failure in processing the circiut
         */
       
//System.out.println("a");
        /* for (Iterator it = circuitOutputsList.iterator(); it.hasNext();) {
            Output temp = (Output) it.next();
             if (temp.getstateValue() == 1) { // Insufficient Inputs to process circuit
               for (int j = 0; j < temp.getDestinationInputList().size(); j++) {
                        temp.getDestinationInputList().elementAt(j).setDataValue(temp.getDataValue());
                       // temp.getDestinationInputList().elementAt(j).setTimeVarying(false);
                       // nextSet.add(currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor());
                    }
            }
        }*/


for (Iterator it = allInputsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            Input inp = (Input) allInputsList.get((Integer) key);
            if (inp.getSourceOutputNode() != null) {
              inp.setNewValue(-1);
            }
            else
            {
                inp.setNewValue(inp.getDataValue());
            }
          }
 HashSet<Element> firstSet = new HashSet<Element>(inputElementSet);
        HashSet<Element> nextSet = new HashSet<Element>();

        /*
         * For each element in currSet,
         * check if all the inputs of the element have proper data values (!=-1)
         * Process the element by calling its processInputs() function
         * Pass on the output values to next level inputs and put them in nextSet
         */
      
        while (!firstSet.isEmpty()) {
            /* The nextSet of elements for this is cleared before processing currSet*/
            nextSet.clear();
            int cycle_count=0;
            for (Iterator it = firstSet.iterator(); it.hasNext();) {
                Element currElement = (Element) it.next();
                int p = 0;
                boolean tempTimeVarying = false;

                /*
                 * Check if all inputs are available for processing this currElement and
                 * set tempTimeVarying flag if it has atleast one input that varies with time
                 * If all inputs are not available, continue with the remaining elements in currSet
                 */
                for (p = 0; p < currElement.getNumInputs(); p++) {
                    if (currElement.getInputAt(p).isTimeVarying()) {        // Atleast one input is time varying
                        tempTimeVarying = true;
                    }
                    if (currElement.getInputAt(p).getNewValue() == -1) {   // All inputs are not available
                        break;
                    }
                }
                if (p != currElement.getNumInputs()) { // Continue as currElement does not have all its inputs available
                    nextSet.add(currElement);
                    cycle_count++;
                    continue;
                }

                /*
                 * Now that all inputs are available for this element, process Inputs and populate its outputs
                 */
                currElement.processInputs();

                /*
                 * Feed forward its output values to next level elements to whose inputs it is connected
                 * Set its timeVarying flag and also of inputs connected to it to denote that its output value varies with time
                 */
                for (int i = 0; i < currElement.getNumOutputs(); i++) {          // Pass on the outputs to next level inputs and add those into the set
                    currElement.getOutputAt(i).setTimeVarying(tempTimeVarying);
                    for (int j = 0; j < currElement.getOutputAt(i).getDestinationInputList().size(); j++) {
                        currElement.getOutputAt(i).getDestinationInputList().elementAt(j).setDataValue(currElement.getOutputAt(i).getDataValue());
                        currElement.getOutputAt(i).getDestinationInputList().elementAt(j).setNewValue(currElement.getOutputAt(i).getDataValue());
                        currElement.getOutputAt(i).getDestinationInputList().elementAt(j).setTimeVarying(tempTimeVarying);
                        nextSet.add(currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor());
                    }
                }
            }

            /*
             * Assign nextSet to currSet so that the next set of elements can be processed
             * This loop goes on till currSet is empty and its empty when nextSet is empty
             * which means all the elements are processed successfully
             */
            if(firstSet.size()==cycle_count)
            {
                break;
            }
            firstSet = new HashSet<Element>(nextSet);
        }







Queue<Element> initial_currSet1 = new  LinkedList<Element>();
          for (Iterator it = inputElementSet.iterator(); it.hasNext();) {
            initial_currSet1.add((Element) it.next());
        }

int ele_count=0,ele_count1=0;
int flag_first=0;
//System.out.println("**************************************");
while(true)
{

    //System.out.println("ele_count:"+ele_count1);
         HashSet<String> dif_state1 = new HashSet<String>();
         HashSet<Element> time_pass = new HashSet<Element>();
         //Queue<Element> processedset1 = new LinkedList<Element>();
         ele_count=0;

         for (Iterator it = allElementsList.keySet().iterator(); it.hasNext();) {
            Element currElement = (Element) allElementsList.get((Integer) it.next());
         int temp_flag=0,temp_flag1=0;
         for (int p_cnt = 0; p_cnt < currElement.getNumInputs(); p_cnt++) {
                
                    if (currElement.getInputAt(p_cnt).getNewValue() != -1) {   // All inputs are not available
                        temp_flag=1;
                    }
                    if (currElement.getInputAt(p_cnt).getNewValue() == -1) {   // All inputs are not available
                        temp_flag1=1;
                    }
                   
                }
          if(temp_flag==0)
          {
              time_pass.add(currElement);
          }

         if(temp_flag1==1)
         {
             if(flag_first==0)
             {
                 currElement.setgatedelay(-1);
             }

         if(temp_flag==1)
         {
             int temp_max=1;
             for (int p_cnt = 0; p_cnt < currElement.getNumInputs(); p_cnt++) {
                 if(currElement.getInputAt(p_cnt).getSourceOutputNode()!=null &&currElement.getInputAt(p_cnt).getNewValue() != -1 && (currElement.getInputAt(p_cnt).getSourceOutputNode().getAncestor().getgatedelay()+1)>=temp_max)
                 {
                     temp_max=currElement.getInputAt(p_cnt).getSourceOutputNode().getAncestor().getgatedelay()+1;
                     System.out.println(currElement.getElementName());
                 }
             }
             currElement.setgatedelay(temp_max);
         }
             }




         if(temp_flag==1)
         {
             for (int p_cnt = 0; p_cnt < currElement.getNumInputs(); p_cnt++) {

                    if (currElement.getInputAt(p_cnt).getNewValue() == -1) {   // All inputs are not available

                    if(currElement.getInputAt(p_cnt).getDataValue()==-1)
                    {
                        currElement.getInputAt(p_cnt).setDataValue(0);
                    }
                    currElement.getInputAt(p_cnt).setNewValue(currElement.getInputAt(p_cnt).getDataValue());
                    
                    }
                }
             ele_count++;
         //    System.out.println(currElement.getElementName());
         }
        
    }
         flag_first=1;
       if(ele_count==ele_count1 || ele_count==allElementsList.size())
    {
        break;
    }
         ele_count1=ele_count;


         int loop_flag=0;

         for(int k=0;k<1000;k++)
        {


        Queue<Element> currSet = new  LinkedList<Element>(initial_currSet1);



    Queue<Element> processedset = new LinkedList<Element>();
    int flag=0;
    String temp_state="";

        /*
         * For each element in currSet,
         * check if all the inputs of the element have proper data values (!=-1)
         * Process the element by calling its processInputs() function
         * Pass on the output values to next level inputs and put them in nextSet
         */
      //   System.out.println("********************");
  
        while (!currSet.isEmpty()) {
        //    System.out.println("b");
            /* The nextSet of elements for this is cleared before processing currSet*/

                Element currElement = (Element) currSet.remove();
                int p = 0;
                boolean tempTimeVarying = false;
     //           System.out.println(currElement.getElementName());
                /*
                 * Check if all inputs are available for processing this currElement and
                 * set tempTimeVarying flag if it has atleast one input that varies with time
                 * If all inputs are not available, continue with the remaining elements in currSet
                 */
                int check=0;
                for (p = 0; p < currElement.getNumInputs(); p++) {
                    if (currElement.getInputAt(p).isTimeVarying()) {        // Atleast one input is time varying
                      //  System.out.println("time varyin");
                        tempTimeVarying = true;
                    }
                    if (currElement.getInputAt(p).getNewValue() == -1) {   // All inputs are not available
                        //currElement.getInputAt(p).setDataValue(0);
                        check=1;
                    }
                   // System.out.println(currElement.getElementName()+" "+p+" "+currElement.getInputAt(p).getDataValue());
                }
                if(check==1)
                {
                    continue;
                }
                if(time_pass.contains(currElement))
                {
                     int temp_max=1;
             for (int p_cnt = 0; p_cnt < currElement.getNumInputs(); p_cnt++) {
                 if(currElement.getInputAt(p_cnt).getSourceOutputNode()!=null &&currElement.getInputAt(p_cnt).getNewValue() != -1 && (currElement.getInputAt(p_cnt).getSourceOutputNode().getAncestor().getgatedelay()+1)>=temp_max)
                 {
                     temp_max=currElement.getInputAt(p_cnt).getSourceOutputNode().getAncestor().getgatedelay()+1;
                     System.out.println(currElement.getElementName());
                 }
             }
             currElement.setgatedelay(temp_max);
                time_pass.remove(currElement);
                }
                Vector<Integer> temp_out = new Vector<Integer>();
                 for (p = 0; p < currElement.getNumOutputs(); p++) {
                     temp_out.add(currElement.getOutputAt(p).getDataValue());
                     temp_state=temp_state+new String(Integer.toString(currElement.getOutputAt(p).getDataValue()));
                 }


                 if(currElement.getElementType().equals("Generic_Element"))
                {
                    currElement.setprocess_state(true);
                }
                    currElement.processInputs();

                     if(currElement.getElementType().equals("Generic_Element") && (!currElement.getprocess_state()))
                {
                    currElement.setprocess_state(true);
                    flag=1;
                    k=1000;

                    break;
                }

                for (p = 0; p < currElement.getNumOutputs(); p++) {
  //                  System.out.println(temp_out.elementAt(p) + " "+currElement.getOutputAt(p).getDataValue() ) ;

                    if(temp_out.elementAt(p) !=(currElement.getOutputAt(p).getDataValue()))

                     {
                         flag=1;
                     }
                 }
                processedset.add(currElement);
                /*
                 * Feed forward its output values to next level elements to whose inputs it is connected
                 * Set its timeVarying flag and also of inputs connected to it to denote that its output value varies with time
                 */
                for (int i = 0; i < currElement.getNumOutputs(); i++) {          // Pass on the outputs to next level inputs and add those into the set
                    currElement.getOutputAt(i).setTimeVarying(tempTimeVarying);
                   //  System.out.println("output:"+k+currElement.getElementName());
                    for (int j = 0; j < currElement.getOutputAt(i).getDestinationInputList().size(); j++) {
                       currElement.getOutputAt(i).getDestinationInputList().elementAt(j).setTimeVarying(tempTimeVarying);

                        currElement.getOutputAt(i).getDestinationInputList().elementAt(j).setDataValue(currElement.getOutputAt(i).getDataValue());
                        currElement.getOutputAt(i).getDestinationInputList().elementAt(j).setNewValue(currElement.getOutputAt(i).getDataValue());

                        if(!processedset.contains( currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor()) && !currSet.contains( currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor()))
                        {
                     //   System.out.println("input:"+k+currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor().getElementName());


                        currSet.add(currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor());

                    }
                }}
            }




//System.out.println(temp_state);
    if(flag==0)
    {
        loop_flag=1;
        break;
    }
    if(dif_state1.contains(temp_state))
    {

        break;
    }
    else
    {
        dif_state1.add(temp_state);
    }
        }

if(loop_flag==0)
{
    break;
}
      

}











        /*
         * currSet is the present set of elements being processed
         * Once each element in currSet is processed, it passes its outputs to next level inputs
         * and their elements are stored in nextSet. After each iteration, currSet is set to nextSet
         * Initially, currSet includes circuit level input elements as they have all their inputs
         */
         Queue<Element> initial_currSet = new  LinkedList<Element>();
          for (Iterator it = inputElementSet.iterator(); it.hasNext();) {
            initial_currSet.add((Element) it.next());
        }
         HashSet<String> dif_state = new HashSet<String>();
         for(int k=0;k<1000;k++)
        {

           
        Queue<Element> currSet = new  LinkedList<Element>(initial_currSet);
      


    Queue<Element> processedset = new LinkedList<Element>();
    int flag=0;
    String temp_state="";

        /*
         * For each element in currSet,
         * check if all the inputs of the element have proper data values (!=-1)
         * Process the element by calling its processInputs() function
         * Pass on the output values to next level inputs and put them in nextSet
         */
      //   System.out.println("********************");
        while (!currSet.isEmpty()) {
        //    System.out.println("b");
            /* The nextSet of elements for this is cleared before processing currSet*/
          
                Element currElement = (Element) currSet.remove();
                int p = 0;
                boolean tempTimeVarying = false;
     //           System.out.println(currElement.getElementName());
                /*
                 * Check if all inputs are available for processing this currElement and
                 * set tempTimeVarying flag if it has atleast one input that varies with time
                 * If all inputs are not available, continue with the remaining elements in currSet
                 */
                for (p = 0; p < currElement.getNumInputs(); p++) {
                    if (currElement.getInputAt(p).isTimeVarying()) {        // Atleast one input is time varying
                      //  System.out.println("time varyin");
                        tempTimeVarying = true;
                    }
                    if (currElement.getInputAt(p).getDataValue() == -1) {   // All inputs are not available
                        currElement.getInputAt(p).setDataValue(0);
                    }
                  //  System.out.println(currElement.getElementName()+" "+p+" "+currElement.getInputAt(p).getDataValue());
                }
                Vector<Integer> temp_out = new Vector<Integer>();
                 for (p = 0; p < currElement.getNumOutputs(); p++) {
                     temp_out.add(currElement.getOutputAt(p).getDataValue());
                     temp_state=temp_state+new String(Integer.toString(currElement.getOutputAt(p).getDataValue()));
                 }
            

                 if(currElement.getElementType().equals("Generic_Element"))
                {
                    currElement.setprocess_state(true);
                }
                    currElement.processInputs();

                     if(currElement.getElementType().equals("Generic_Element") && (!currElement.getprocess_state()))
                {
                    currElement.setprocess_state(true);
                    flag=1;
                    k=1000;
                    break;
                }
          
                for (p = 0; p < currElement.getNumOutputs(); p++) {
  //                  System.out.println(temp_out.elementAt(p) + " "+currElement.getOutputAt(p).getDataValue() ) ;
                    
                    if(temp_out.elementAt(p) !=(currElement.getOutputAt(p).getDataValue()))

                     {
                         flag=1;
                     }
                 }
                processedset.add(currElement);
                /*
                 * Feed forward its output values to next level elements to whose inputs it is connected
                 * Set its timeVarying flag and also of inputs connected to it to denote that its output value varies with time
                 */
                for (int i = 0; i < currElement.getNumOutputs(); i++) {          // Pass on the outputs to next level inputs and add those into the set
                    currElement.getOutputAt(i).setTimeVarying(tempTimeVarying);
                   //  System.out.println("output:"+k+currElement.getElementName());
                    for (int j = 0; j < currElement.getOutputAt(i).getDestinationInputList().size(); j++) {
                       currElement.getOutputAt(i).getDestinationInputList().elementAt(j).setTimeVarying(tempTimeVarying);

                        currElement.getOutputAt(i).getDestinationInputList().elementAt(j).setDataValue(currElement.getOutputAt(i).getDataValue());

                        if(!processedset.contains( currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor()) && !currSet.contains( currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor()))
                        {
                     //   System.out.println("input:"+k+currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor().getElementName());


                        currSet.add(currElement.getOutputAt(i).getDestinationInputList().elementAt(j).getAncestor());

                    }
                }}
            }

       
             

//System.out.println(temp_state);
    if(flag==0)
    {
        return true;
    }
    if(dif_state.contains(temp_state))
    {
        break;
    }
    else
    {
        dif_state.add(temp_state);
    }
        }
        for (Iterator it = allOutputsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            Output out = (Output) allOutputsList.get((Integer) key);
            out.setDataValue(-1);

        }
          for (Iterator it = allInputsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            Input inp = (Input) allInputsList.get((Integer) key);
            if (inp.getSourceOutputNode() != null) {
              inp.setDataValue(-1);
            }
          }
        /*
         * Return success in processing this circuit
         */
        return false;
    }

    /*
     * deleteElement(delID) deletes the element with elementID(delID) from the circuit
     * In the process, it also removes all its connections to other elements and
     * those elements get their inputs from the user or pass on their outputs directly to the user
     * until new connections are defined.
     * The element, its input and output nodes are also removed from corresponding lists.
     */
    public void deleteElement(int delID) {
        /*
         * Retreive the element data structure from the hashamp allElementsList
         */
        Element delElement = allElementsList.get(delID);

        /*
         * Delete all its input nodes and their connections to previous output nodes if they exist
         */
        for (int i = 0; i < delElement.getNumInputs(); i++) {
            if (delElement.getInputAt(i).getSourceOutputNode() != null) {
                delElement.getInputAt(i).getSourceOutputNode().delDestInpNode(delElement.getInputAt(i));
            }
            allInputsList.remove(delElement.getInputAt(i).getInputID());
        }

        /*
         * Delete all its output nodes and their connections to next level input nodes
         */
        for (int i = 0; i < delElement.getNumOutputs(); i++) {       // delete all its output nodes and their connections
            for (int j = 0; j < delElement.getOutputAt(i).getDestinationInputList().size(); j++) {
                delElement.getOutputAt(i).getDestinationInputList().elementAt(j).setSourceOutput(null);
            }
            delElement.getOutputAt(i).getDestInpIdsList().clear();
            allOutputsList.remove(delElement.getOutputAt(i).getOutputID());
        }

        /*
         * Update the meshType and meshID matices of the circiut with respect to this deleted element, so that its not drawn
         */
        int maxExt = delElement.getMaxIO();
        for (int i = -maxExt; i <= maxExt; i++) {
            for (int j = -maxExt; j <= maxExt; j++) {
                meshType[delElement.getLocation().y + j][delElement.getLocation().x + i] = 0;
                meshID[delElement.getLocation().y + j][delElement.getLocation().x + i] = 0;
            }
        }

        /*
         * Remove this element from the list of all the elements in the circuit and
         * re-identify all the circuit level inputs and outputs due to these new modifications
         */
        allElementsList.remove(delID);
        identifyCktInputsOutputs();
    }

    public void drawZigZag(Graphics2D g2d, Point src, Point dest) {
        g2d.drawLine(src.x, src.y, (src.x + dest.x) / 2, src.y);
        g2d.drawLine((src.x + dest.x) / 2, src.y, (src.x + dest.x) / 2, dest.y);
        g2d.drawLine((src.x + dest.x) / 2, dest.y, dest.x, dest.y);
    }

    public void draw(Graphics2D g2d) {
        for (Iterator it = allElementsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            Element ele = (Element) allElementsList.get((Integer) key);
            ele.draw(g2d, mainPanel.upScale(ele.getLocation()));
        }
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        for (Iterator it1 = allElementsList.keySet().iterator(); it1.hasNext();) {
            Object key = it1.next();
            Element ele = (Element) allElementsList.get((Integer) key);
            Vector<Output> ops = ele.getOutputList();
            for (Iterator it2 = ops.iterator(); it2.hasNext();) {
                Output op = (Output) it2.next();
                Point src = new Point(mainPanel.upScale(op.getLocation()));
                Vector<Input> ips = op.getDestinationInputList();
                int tempheight=0;
                if (ips.size() == 1) {                  // no splits needed
                    Input ip = ips.firstElement();
                    Point dest = new Point(mainPanel.upScale(ip.getLocation()));
                    if(dest.x<src.x)
                    {

                            if(src.y >= dest.y)
                            {
                                 tempheight = mainPanel.upScale(ip.getAncestor().getLocation()).y-(60+ip.getAncestor().getNumInputs()*18+ip.getInputIndex()*10);
                            }
                            else
                            {
                                  tempheight = mainPanel.upScale(ip.getAncestor().getLocation()).y+(60+ip.getAncestor().getNumInputs()*18+ip.getInputIndex()*10);
                            }
                            g2d.drawLine(src.x, src.y, (src.x+10*op.getOutputIndex()), src.y);
                            g2d.drawLine((src.x+10*op.getOutputIndex()), src.y, (src.x+10*op.getOutputIndex()), tempheight);
                            g2d.drawLine((src.x+10*op.getOutputIndex()), tempheight,dest.x-(60+ip.getInputIndex()*10),tempheight);
                            g2d.drawLine(dest.x-(60+ip.getInputIndex()*10),tempheight,dest.x-(60+ip.getInputIndex()*10),dest.y);
                            g2d.drawLine(dest.x-(60+ip.getInputIndex()*10),dest.y,dest.x,dest.y);
                            
                        
                    }
                    else
                    {
                    if (src.x == dest.x || src.y == dest.y) {     //straight line
                        g2d.drawLine(src.x, src.y, dest.x, dest.y);
                    } else {                                      // bend required
                        drawZigZag(g2d, src, dest);
                    }
                    }
                } else if (ips.size() > 1) {                                  // splits required
                    for (int i = 0; i < ips.size(); i++) {
                        for (int j = i + 1; j < ips.size(); j++) {
                            Input tempIp1 = ips.elementAt(i);
                            Input tempIp2 = ips.elementAt(j);
                            if (tempIp2.getLocation().y < tempIp1.getLocation().y) {
                                ips.setElementAt(tempIp2, i);
                                ips.setElementAt(tempIp1, j);
                            }
                        }
                    }
                    Point split = new Point(ips.firstElement().getLocation());
                   // split.x=src.x;
                    for (Iterator it3 = ips.iterator(); it3.hasNext();) {    //get the nearest x coordinate of the elements
                        Input temp = (Input) it3.next();
                        if ((split.x > temp.getLocation().x) &&(temp.getLocation().x>src.x) ) {
                            split.x = temp.getLocation().x;
                        }
                    }

                    if (ips.size() % 2 == 0) // get the middlemost y coodinate of the elements
                    {
                        split.y = (ips.elementAt(ips.size() / 2).getLocation().y
                                + ips.elementAt(ips.size() / 2 - 1).getLocation().y) / 2;
                    } else {
                        split.y = ips.elementAt(ips.size() / 2).getLocation().y;
                    }

                    split = mainPanel.upScale(new Point(split));
                    split.x = (src.x + split.x) / 2;
                    if(split.x<src.x)
                    {
                        split.x=src.x+(30+op.getOutputIndex()*10);
                    }
                    drawZigZag(g2d, src, split);

                    g2d.fillOval(split.x - 7, split.y - 7, 14, 14);

          //          g2d.drawLine(split.x, mainPanel.upScale(new Point(ips.firstElement().getLocation())).y,
           //                 split.x, mainPanel.upScale(new Point(ips.lastElement().getLocation())).y);
                   int miny=1000000;
                   int maxy =-1000000;
                    for (Iterator it3 = ips.iterator(); it3.hasNext();) {
                        Input temp = (Input) it3.next();
                        Point tempDest = mainPanel.upScale(new Point(temp.getLocation()));
                        tempheight=0;
                        if(tempDest.x<src.x)
                        {
                            if(split.y >= tempDest.y)
                            {
                                 tempheight = mainPanel.upScale(temp.getAncestor().getLocation()).y-(60+temp.getAncestor().getNumInputs()*18+temp.getInputIndex()*10);
                            }
                            else
                            {
                                  tempheight = mainPanel.upScale(temp.getAncestor().getLocation()).y+(60+temp.getAncestor().getNumInputs()*18+temp.getInputIndex()*10);
                            }
                            if(tempheight>maxy)
                            {
                                maxy=tempheight;
                            }
                            if(tempheight<miny)
                            {
                                miny=tempheight;
                            }
                            g2d.drawLine(split.x, split.y, split.x, tempheight);
                            g2d.drawLine(split.x, tempheight,tempDest.x-(60+temp.getInputIndex()*10),tempheight);
                            g2d.drawLine(tempDest.x-(60+temp.getInputIndex()*10),tempheight,tempDest.x-(60+temp.getInputIndex()*10),tempDest.y);
                            g2d.drawLine(tempDest.x-(60+temp.getInputIndex()*10),tempDest.y,tempDest.x,tempDest.y);
                        }
                        else
                        {
                            if(tempDest.y>maxy)
                            {
                                maxy=tempDest.y;
                            }
                            if(tempDest.y<miny)
                            {
                                miny=tempDest.y;
                            }
                        g2d.drawLine(split.x, tempDest.y, tempDest.x, tempDest.y);
                        }
                        }
                   g2d.drawLine(split.x, maxy, split.x, miny);
                }
            }
        }
    }


    /*
     * loadCircuit(bufReader) loads a circuit from a previously saved file opened by bufReader in read mode
     * It populates all the variables of a circuit, parsing the file and assigning parameters to constituent elements
     */
    public String loadCircuit(BufferedReader bufReader) throws FileNotFoundException, IOException {
        /*
         * Initialize local variables
         */
        String currentLine = "currentLine";          // current line (non-blank, non-comment[# ...]) being read
        String comment = "comment";              // comment line [# ...] being read
        String blankLine = "blankLine";            // blank line being read []

        /*
         * Load metadata about the circuit
         */
        comment = bufReader.readLine();                             // # Circuit ID
        circuitID = Integer.valueOf(bufReader.readLine());
        blankLine = bufReader.readLine();

        comment = bufReader.readLine();                             // # Circuit Name
        circuitName = bufReader.readLine();
        blankLine = bufReader.readLine();

        comment = bufReader.readLine();                             // # Number of Inputs for the whole circuit
        numInputs = Integer.valueOf(bufReader.readLine());
        blankLine = bufReader.readLine();

        comment = bufReader.readLine();                             // # Number of Outputs for the whole circuit
        numOutputs = Integer.valueOf(bufReader.readLine());
        blankLine = bufReader.readLine();


        /*
         * Load the list of all the elements in the circuit
         * The next few comment lines define the format in which the elements were saved
         */
        comment = bufReader.readLine();                             // # Total Number of Elements in the Circuit
        comment = bufReader.readLine();                             // # List of all Elements
        comment = bufReader.readLine();                             // # ID, Name, Location.x, Location.y
        comment = bufReader.readLine();                             // # Num_Inputs_For_Element
        comment = bufReader.readLine();                             // # InputID1, InputIndex, AncestorID, Location.x, Location.y, OutputSrcNode
        comment = bufReader.readLine();                             // # Num_Outputs_For_Element
        comment = bufReader.readLine();                             // # OutputID1, OutputIndex, AncestorID, Location.x, Locaiton.y, NumInpDest, InpId1, InpId2...
        int numElements = Integer.valueOf(bufReader.readLine());        // Number of Elements in the Circuit
        for (int i = 0; i < numElements; i++) {                         // For each such element in the circuit
            Element newElement;
            Scanner scan = new Scanner(bufReader.readLine());                       // Line 1 id, name, x, y
            scan.useDelimiter(", ");
            int id = scan.nextInt();
            String eleName = scan.next();

            /*
             * Based on the type of the element, call the corresponding constructor
             * If its of Generic Element Type, then call the loadCircuit function of the same bufReader
             * to load the inbuilt circuit saved inline to the inbuiltCkt variable of the generic element
             * Then continue with the normal process of loading elements
             */
            if (eleName.startsWith("And_Gate")) {
                newElement = new AndGate();
                newElement.setElementType("And_Gate");
            }else if (eleName.startsWith("3And_Gate")) {
                newElement = new AndGate3();
                newElement.setElementType("3And_Gate");
            }else if (eleName.startsWith("4And_Gate")) {
                newElement = new AndGate4();
                newElement.setElementType("4And_Gate");
            }
            else if (eleName.startsWith("Or_Gate")) {
                newElement = new OrGate();
                newElement.setElementType("Or_Gate");
            }else if (eleName.startsWith("3Or_Gate")) {
                newElement = new OrGate3();
                newElement.setElementType("3Or_Gate");
            }else if (eleName.startsWith("4Or_Gate")) {
                newElement = new OrGate4();
                newElement.setElementType("4Or_Gate");
            }
            else if (eleName.startsWith("Not_Gate")) {
                newElement = new NotGate();
                newElement.setElementType("Not_Gate");
            } else if (eleName.startsWith("Nand_Gate")) {
                newElement = new NandGate();
                newElement.setElementType("Nand_Gate");
            } else if (eleName.startsWith("3Nand_Gate")) {
                newElement = new NandGate3();
                newElement.setElementType("3Nand_Gate");
            }
             else if (eleName.startsWith("4Nand_Gate")) {
                newElement = new NandGate4();
                newElement.setElementType("4Nand_Gate");
            }
            else if (eleName.startsWith("Nor_Gate")) {
                newElement = new NorGate();
                newElement.setElementType("Nor_Gate");
            } else if (eleName.startsWith("3Nor_Gate")) {
                newElement = new NorGate3();
                newElement.setElementType("3Nor_Gate");
            } 
             else if (eleName.startsWith("4Nor_Gate")) {
                newElement = new NorGate4();
                newElement.setElementType("4Nor_Gate");
            }
            else if (eleName.startsWith("Xor_Gate")) {
                newElement = new XorGate();
                newElement.setElementType("Xor_Gate");
            }else if (eleName.startsWith("3Xor_Gate")) {
                newElement = new XorGate3();
                newElement.setElementType("3Xor_Gate");
            }
            else if (eleName.startsWith("4Xor_Gate")) {
                newElement = new XorGate4();
                newElement.setElementType("4Xor_Gate");
            }
            else if (eleName.startsWith("Xnor_Gate")) {
                newElement = new XnorGate();
                newElement.setElementType("Xnor_Gate");
            }else if (eleName.startsWith("3Xnor_Gate")) {
                newElement = new XnorGate3();
                newElement.setElementType("3Xnor_Gate");
            }
            else if (eleName.startsWith("4Xnor_Gate")) {
                newElement = new XnorGate4();
                newElement.setElementType("4Xnor_Gate");
            }
            else if (eleName.startsWith("conn")) {
                newElement = new Connector();
                newElement.setElementType("conn");
            }
            else if (eleName.startsWith("Generic_Element")) {
                newElement = new GenericElement();
                newElement.setElementType("Generic_Element");
                Circuit genEleCkt = new Circuit();
                genEleCkt.loadCircuit(bufReader);
                newElement.setInbuiltCkt(genEleCkt);
            } else {
                newElement = new Element();
            }

            /*
             * Set the basic properties of the element now that its type has been decided
             */
            newElement.setElementID(id);
            newElement.setElementName(eleName);
            newElement.setLocation(new Point(scan.nextInt(), scan.nextInt()));
          //  System.out.println(eleName);
            /*
             * Load all the inputs for this particular element
             */
            int numInp = Integer.valueOf(bufReader.readLine());                     // Number of Inputs for this element
            for (int j = 0; j < numInp; j++) {                                      // Load parameters of each input of this element
                String line = bufReader.readLine();
                scan = scan.reset();
                scan = new Scanner(line);                                           // Line 2 - inputs for that element
                scan.useDelimiter(", ");
                Input newInp = new Input(scan.nextInt(), scan.nextInt(), newElement,scan.next());
                int ancestorID = scan.nextInt();
                newInp.setLocation(new Point(scan.nextInt(), scan.nextInt()));
                int srcOutID = scan.nextInt();
                if (srcOutID == -1) {                                               // if srcOutID is -1 => its a cirucit level input
                    newInp.setSourceOutput(null);
                } else {
                    newInp.setSourceOutput(allOutputsList.get(srcOutID));
                }

                /*
                 * Add this input to the list of inputs that belong to this element and
                 * also to the aggregate list of all input nodes present in the circuit
                 */
                newElement.addInput(newInp);
                allInputsList.put(newElement.getInputAt(j).getInputID(), newElement.getInputAt(j));
            }

            /*
             * Load all the outputs for this particular element
             */
            int numOut = Integer.valueOf(bufReader.readLine());                     // Number of Outputs for this element
            for (int j = 0; j < numOut; j++) {                                      // Load parameters for each output of this element
                String line = bufReader.readLine();
                scan = scan.reset();
                scan = new Scanner(line);                                           // Line 3 - outputs for that element
                scan.useDelimiter(", ");
                Output newOut = new Output(scan.nextInt(), scan.nextInt(), newElement, scan.next());
                int parentID = scan.nextInt();                                      // ancestorID
                newOut.setLocation(new Point(scan.nextInt(), scan.nextInt()));
                int numDestInp = scan.nextInt();                                    // number of inputs to which this output is connected


                /*
                 * First, store a list of the ids of all the inputs to which this output is connected
                 * The destinationInputsList can be populated once all the inputs of all the elements have been read
                 */
                for (int k = 0; k < numDestInp; k++) {
                    int inpid = scan.nextInt();
                    newOut.addDestInpId(inpid);
                }

                /*
                 * Add this output to the list of outputs that belong to this element and
                 * also to the aggregate list of all outputs that are present in the circuit
                 */
                newElement.addOutput(newOut);
                allOutputsList.put(newElement.getOutputAt(j).getOutputID(), newElement.getOutputAt(j));
            }

            /*
             * Finally, add this element (fully populated) to the list of constituent elements present in the circuit
             * Also update the mesh Matrices with respect to this element so that it is drawn
             */
            allElementsList.put(newElement.getElementID(), newElement);
            newElement.updateMatrix(newElement.getLocation(), meshType, meshID, null);
            scan.close();
        }
        blankLine = bufReader.readLine();

        /*
         * Now that all the elements are read and so are their input nodes,
         * for every output of every element, using the destination input ids stored before,
         * add their corresponding input nodes to the destinationInputs list for that particular output node
         */
        for (Iterator it = allElementsList.keySet().iterator(); it.hasNext();) {
            Element ele = (Element) allElementsList.get((Integer) it.next());
            for (int i = 0; i < ele.getNumOutputs(); i++) {
                Output out = ele.getOutputAt(i);
                for (int j = 0; j < out.getDestInpIdsList().size(); j++) {
                    out.addDestinationInput(allInputsList.get(out.getDestInpIdsList().elementAt(j)));
                    allInputsList.get(out.getDestInpIdsList().elementAt(j)).setSourceOutput(out);

                }
                ele.getOutputList().setElementAt(out, i);
                allOutputsList.put(out.getOutputID(), out);
            }
        }


        /*
         * Load a list of inputs that act as inputs for the entire circuit
         * i.e. circuit level inputs, those inputs to which the user needs to feed values
         */
        comment = bufReader.readLine();                                     // # Num_Inputs_For_Whole_Circuit, InpID1, InpID2, InpID3...
        Scanner scanIO = new Scanner(bufReader.readLine());
        scanIO.useDelimiter(", ");
        numInputs = scanIO.nextInt();
        while (scanIO.hasNextInt()) {
            this.circuitInputsList.add(allInputsList.get(scanIO.nextInt()));
        }
        blankLine = bufReader.readLine();


        /*
         * Load a list of outputs that act as inputs for the entire circuit
         * i.e. circuit level outputs, those outputs which directly give their values to the user
         */
        comment = bufReader.readLine();                                     // # Num_Outputs_For_Whole_Circuit, OutID1, OutID2, OutID3...
        scanIO.reset();
        scanIO = new Scanner(bufReader.readLine());
        scanIO.useDelimiter(", ");
        numOutputs = scanIO.nextInt();
        while (scanIO.hasNextInt()) {
            Output op1 = allOutputsList.get(scanIO.nextInt());
            op1.setstatevalue(1);
            this.circuitOutputsList.add(op1);
        }
        blankLine = bufReader.readLine();


        /*
         * Load a list of elements that act as inputs for the entire circuit
         * i.e. those elements that have atleast one circuit level input
         */
        comment = bufReader.readLine();                                     // # Num_Input_Elements_For_Whole_Circuit, ElementID1, ElementID2, ElementID3...
        scanIO.reset();
        scanIO = new Scanner(bufReader.readLine());
        scanIO.useDelimiter(", ");
        int temp = scanIO.nextInt();
        while (scanIO.hasNextInt()) {
            this.inputElementSet.add(allElementsList.get(scanIO.nextInt()));
        }
        blankLine = bufReader.readLine();


        /*
         * Load a list of outputs that act as inputs for the entire circuit
         * i.e. those elements that have atleast one circuit level output
         */
        comment = bufReader.readLine();                                     // # Num_Output_Elements_For_Whole_Circuit, ElementID1, ElementID2, ElementID3...
        scanIO.reset();
        scanIO = new Scanner(bufReader.readLine());
        scanIO.useDelimiter(", ");
        temp = scanIO.nextInt();
        while (scanIO.hasNextInt()) {
            outputElementSet.add(allElementsList.get(scanIO.nextInt()));
        }

        /*
         * Return a success statement to the parent function
         */
        return ("circuit loaded successfully!");
    }

    /*
     * saveCircuit(filename, append) function saves the current active circuit being modified
     * into a file denoted by the string filename.
     * If append is true, the file is appended - this is used to save the inbuilt circuit in generic elements
     * If false, the file is opened in write mode and used to save the entire circuit as a whole
     */
    public String saveCircuit(String filename, boolean append) throws FileNotFoundException, IOException {
        /*
         * Open the file represented by the string filename in append mode if append is set.
         * Create a printstream that's used to write output strings to the file
         */
        BufferedOutputStream bufStream = new BufferedOutputStream(new FileOutputStream(filename, append));
        PrintStream p = new PrintStream(bufStream);

        /*
         * Identify circuit level inputs and outputs before saving to the file
         * This would be the last saved state of the circuit
         */
        identifyCktInputsOutputs();
       // System.out.println("Saving current circiut to file : " + filename);

        /* Store metadata about the circuit - ID, Name, NumInputs and NumOutputs */
        p.println("# Circuit ID\n" + this.circuitID);
        p.println("\n# Circuit Name\n" + this.circuitName);
        p.println("\n# Number of Inputs for the whole circuit\n" + this.numInputs);
        p.println("\n# Number of Outputs for the whole circuit\n" + this.numOutputs);


        /* Explain the format in which all the elements are stored */
        p.println("\n# Total Number of Elements in the Circuit");
        p.println("# List of all Elements");
        p.println("# ID, Name, Location.x, Location.y");
        p.println("# Num_Inputs_For_Element");
        p.println("# InputID1, InputIndex, AncestorID, Location.x, Location.y, OutputSrcNode");
        p.println("# Num_Outputs_For_Element");
        p.println("# OutputID1, OutputIndex, AncestorID, Location.x, Locaiton.y, NumInpDest, InpId1, InpId2... ");
        p.println(this.allElementsList.size());

        /*
         * For every element, store all its member variables in the above format line-wise
         */
        for (Iterator it = allElementsList.keySet().iterator(); it.hasNext();) {
            Element currElement = (Element) allElementsList.get((Integer) it.next());
            p.print(currElement.getElementID() + ", " + currElement.getElementName() + ", ");
            p.println(currElement.getLocation().x + ", " + currElement.getLocation().y);

            /*
             * If the element is a generic element, it has an inbuilt circuit that also needs to be saved
             * So, close the print and buffer streams and call save circuit with the inbuiltCkt variable
             * Once the inbuilt circuit is saved inline, reopen the print and buffer streams in append mode
             * and continue saving the details of this element just as every other element
             */
            if (currElement.getElementType().equals("Generic_Element")) {
                p.close();
                bufStream.close();
                currElement.getInbuiltCircuit().saveCircuit(filename, Boolean.TRUE);
                bufStream = new BufferedOutputStream(new FileOutputStream(filename, Boolean.TRUE));
                p = new PrintStream(bufStream);
            }

            /*
             * Save all the inputs for this particular element, each in a single line with all its properties
             */
            p.println(currElement.getNumInputs());
            for (int i = 0; i < currElement.getInputList().size(); i++) {
                Input currInp = currElement.getInputAt(i);
                p.print(currInp.getInputID() + ", " + currInp.getInputIndex() + ", " +currInp.getname()+", "+ currInp.getAncestor().getElementID());
                p.print(", " + currInp.getLocation().x + ", " + currInp.getLocation().y);
                if (currInp.getSourceOutputNode() == null) {
                    p.print(", -1");
                } else {
                    p.print(", " + currInp.getSourceOutputNode().getOutputID());
                }
                p.print("\n");
            }

            /*
             * Save all the outputs for this particular element, each in a single line with all its properties
             * With respect to destinationInputsList, first store the size and then a list of all their ids, -1 if null
             */
            p.println(currElement.getNumOutputs());
            for (int i = 0; i < currElement.getOutputList().size(); i++) {
                Output currOut = currElement.getOutputAt(i);
                p.print(currOut.getOutputID() + ", " + currOut.getOutputIndex() + ", " +currOut.getname()+", "+ currOut.getAncestor().getElementID());
                p.print(", " + currOut.getLocation().x + ", " + currOut.getLocation().y);
                p.print(", " + currOut.getDestinationInputList().size());
                for (int j = 0; j < currOut.getDestinationInputList().size(); j++) {
                    if (currOut.getDestinationInputList().elementAt(j) == null) {
                        p.print(", -1");
                    } else {
                        p.print(", " + currOut.getDestinationInputList().elementAt(j).getInputID());
                    }
                }
                p.print("\n");
            }
        }


        /*
         * Store a list of inputs that act as inputs for the entire circuit
         */
        p.println("\n# Num_Inputs_For_Whole_Circuit, InpID1, InpID2, InpID3...");
        p.print(this.circuitInputsList.size());
        for (int i = 0; i < circuitInputsList.size(); i++) {
            p.print(", " + circuitInputsList.elementAt(i).getInputID());
        }
        p.print("\n");

        /*
         * Store a list of outputs that act as outputs for the entire circuit
         */
        p.println("\n# Num_Outputs_For_Whole_Circuit, OutID1, OutID2, OutID3...");
        p.print(this.circuitOutputsList.size());
        for (int i = 0; i < circuitOutputsList.size(); i++) {
            p.print(", " + circuitOutputsList.elementAt(i).getOutputID());
        }
        p.print("\n");

        /*
         * Store a list of elements that act as input elements - first level - for the entire circuit
         */
        p.println("\n# Num_Input_Elements_For_Whole_Circuit, ElementID1, ElementID2, ElementID3...");
        p.print(this.inputElementSet.size());
        for (Iterator it = inputElementSet.iterator(); it.hasNext();) {
            p.print(", " + ((Element) it.next()).getElementID());
        }
        p.print("\n");

        /*
         * Store a list of elements that act as output elements - final level - for the entire circuit
         */
        p.println("\n# Num_Output_Elements_For_Whole_Circuit, ElementID1, ElementID2, ElementID3...");
        p.print(this.outputElementSet.size());
        for (Iterator it = outputElementSet.iterator(); it.hasNext();) {
            p.print(", " + ((Element) it.next()).getElementID());
        }
        p.print("\n");

        /*
         * Close the print and buffer streams and return a success statement to the parent function
         */
        p.close();
        bufStream.close();
        return ("Done Saving the Circuit to " + filename);
    }
}
