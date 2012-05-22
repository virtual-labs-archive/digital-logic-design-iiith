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
 * A Generic Element is a freely existing processing element that is a child class of Element Class
 * It can have any number of inputs and outputs and has its own logic of simulation.
 * The logic of the element is defined by the inbuilt circuit present in the element
 * A Generic element is like a black-box representation of its inbuilt Circuit.
 * It is created by importing an existing circuit file as an element.
 * It inherits protected variables from the Element class and 
 * implements the functions in ElementInterface
 * 
 */

public class GenericElement extends Element implements ElementInterface {
  
    /*
     * Null Constructor creates a null definition of a Generic Element.
     * with width = 20*maxIO pixels, height = 20*maxIO pixels, type = "Generic_Element"
     * This constructor is called while loading a circuit.
     */
    GenericElement() {
        elementID = 0;
        elementType = new String();
        elementName = "Generic_Element";
        numInputs = 0;
        numOutputs = 0;
        maxIO = 0;
        width = 0;
        height = 0;
        delay=-1;
        inputList = new Vector<Input>();
        outputList = new Vector<Output>();
        location = new Point();
        inbuiltCkt = new Circuit();
        process_state=true;
    }

    /*
     * Constructor with arguments - called when a new GenericElement is added to current circuit
     * Creating a Generic Element with elementID(id), elementType(type) at location(coord)
     * Creates a Generic Element with 2 inputs with ids starting with "inpID", incrementally
     * and outputs with ids starting with "outID" incrementally.
     */
    GenericElement(int id, String type, int inpID, int outID, Point loc, Circuit genCkt){
        elementID = id;
        elementType = type;
        elementName = type + String.valueOf(id);
        numInputs = genCkt.getNumInputs();
        numOutputs = genCkt.getNumOutputs();
        maxIO = Math.max(numInputs, numOutputs) + 1;
        width = 2*(20*maxIO);
        height = 2*(20*maxIO);
        process_state=true;
        delay=-1;
        inputList = new Vector<Input>();
        outputList = new Vector<Output>();
        location = new Point(loc);
        inbuiltCkt = new Circuit(genCkt);
    //    System.out.println("maxIO : " + maxIO + "\nnumInputs : " + numInputs + "\nnumOutputs : " + numOutputs);
        inbuiltCkt.identifyCktInputsOutputs();
        int cnt_i=0;
        int cnt_j=0;
        for(cnt_i=0;cnt_i<inbuiltCkt.numInputs;cnt_i++)
        {
            for(cnt_j=cnt_i+1;cnt_j<numInputs;cnt_j++)
            {
                Input temp;
                if(inbuiltCkt.circuitInputsList.elementAt(cnt_j).getLocation().y < inbuiltCkt.circuitInputsList.elementAt(cnt_i).getLocation().y )
                {
                    temp=inbuiltCkt.circuitInputsList.elementAt(cnt_j);
                    inbuiltCkt.circuitInputsList.set(cnt_j, inbuiltCkt.circuitInputsList.elementAt(cnt_i));
                    inbuiltCkt.circuitInputsList.set(cnt_i, temp);


                }
            }
        }



        int starty  = loc.y - (numInputs-1);
        for(int i=0; i<numInputs; i++) {
            inputList.add(new Input(inpID, i, this, new Point(loc.x - maxIO, starty),inbuiltCkt.circuitInputsList.elementAt(i).getname()));
            inpID = inpID + 1;
            starty = starty + 2;
        }
        
 for(cnt_i=0;cnt_i<inbuiltCkt.numOutputs;cnt_i++)
    {
    for(cnt_j=cnt_i+1;cnt_j<numOutputs;cnt_j++)
    {
        Output temp;
        if(inbuiltCkt.circuitOutputsList.elementAt(cnt_j).getLocation().y < inbuiltCkt.circuitOutputsList.elementAt(cnt_i).getLocation().y )
        {
            temp=inbuiltCkt.circuitOutputsList.elementAt(cnt_j);
            inbuiltCkt.circuitOutputsList.set(cnt_j, inbuiltCkt.circuitOutputsList.elementAt(cnt_i));
            inbuiltCkt.circuitOutputsList.set(cnt_i, temp);


        }
    }
    }

    
        starty = loc.y - ((numOutputs-1));
        for(int i=0; i<numOutputs; i++) {
            outputList.add(new Output(outID, i, this, new Point(loc.x + maxIO, starty),inbuiltCkt.circuitOutputsList.elementAt(i).getname()));
            outID = outID + 1;
            starty = starty + 2;
        }
    }
    
    /*
     * Overridden function - updates the location of the generic element to Point "p"
     * input and output locations are also updated.
     */
    @Override
    public void updateLocation(Point p) {
        // Update this.location to (p.x, p.y) for the element
        location.x = p.x;
        location.y = p.y;
        
        // Update the locations of the input nodes to the element
        int starty  = p.y - (numInputs-1);
        for(int i=0; i<numInputs; i++) {
            inputList.elementAt(i).setLocation(new Point(p.x - maxIO, starty));
            starty = starty + 2;
        }
        
        // Update the locations of the output nodes to the element
        starty = p.y - (numOutputs-1);
        for(int i=0; i<numOutputs; i++) {
            outputList.elementAt(i).setLocation(new Point(p.x + maxIO, starty));
            starty = starty + 2;
        }
    }

    /*
     * Overridden function updateMatrix updates the circuit matrices 
     * shifting the generic element from location "prev" to "p"
     */
    @Override
    public void updateMatrix(Point p, int[][] matrixType, int[][] matrixID, Point prev) {
        /*
         * For a new generic element, prev is null
         * For an existing generic element, it is moved from "prev" to "p"
         * Update the matrix values at prev to 0 => no generic element exists there
         */
        //System.out.println("update:"+prev);
        //System.out.println(maxIO);
        if(prev != null) {
            for (int i = -maxIO; i <= maxIO; i++) {                               // update element type and ID
                for (int j = -maxIO; j <= maxIO; j++) {
                    matrixID[prev.y + j][prev.x + i] = 0;
                    matrixType[prev.y + j][prev.x + i] = 0;
                }
            }
            
            for(int i=0; i<numInputs; i++) {
                Point currInp = inputList.elementAt(i).getLocation();
                matrixID[currInp.y][currInp.x] = 0;
                matrixType[currInp.y][currInp.x] = 0;
            }
            
            for(int i=0; i<numOutputs; i++) {
                Point currOut = outputList.elementAt(i).getLocation();
                matrixID[currOut.y][currOut.x] = 0;
                matrixType[currOut.y][currOut.x] = 0;
            }
        }
        
        /*
         * Update the matrix values at location "p" to elementID and elementType
         */
        for (int i = -maxIO; i <= maxIO; i++) {                               // update element type and ID
            for (int j = -maxIO; j <= maxIO; j++) {
                matrixType[p.y + j][p.x + i] = 3;
                matrixID[p.y + j][p.x + i] = elementID;
            }
        }
        
        for (int i = 0; i < numInputs; i++) {
            Point currInp = this.getInputAt(i).getLocation();
            matrixID[currInp.y][currInp.x] = this.getInputAt(i).getInputID();
            matrixType[currInp.y][currInp.x] = 1;
        }

        for (int i = 0; i < numOutputs; i++) {
            Point currOut = this.getOutputAt(i).getLocation();
            matrixID[currOut.y][currOut.x] = this.getOutputAt(i).getOutputID();
            matrixType[currOut.y][currOut.x] = 2;
        }
    }

    /*
     * Overridden function processInputs()
     * processInputs() does the processing on all its inputs and fills the output nodes
     * It is done step wise 
     * - passing on the element inputs to the inbuilt circuit
     * - processing the inbuilt circuit
     * - passing on the inbuitl circuit output results to the element outputs
     */
    @Override
    public void processInputs() {
        process_state=true;
    //    System.out.println("Processing Generic Element");
        // Step 1: Identify The Inputs and Outputs for the inbuilt circuit of the generic element
        inbuiltCkt.identifyCktInputsOutputs();
        int cnt_i=0;
int cnt_j=0;
for(cnt_i=0;cnt_i<inbuiltCkt.numInputs;cnt_i++)
{
    for(cnt_j=cnt_i+1;cnt_j<numInputs;cnt_j++)
    {
        Input temp;
        if(inbuiltCkt.circuitInputsList.elementAt(cnt_j).getLocation().y < inbuiltCkt.circuitInputsList.elementAt(cnt_i).getLocation().y )
        {
            temp=inbuiltCkt.circuitInputsList.elementAt(cnt_j);
            inbuiltCkt.circuitInputsList.set(cnt_j, inbuiltCkt.circuitInputsList.elementAt(cnt_i));
            inbuiltCkt.circuitInputsList.set(cnt_i, temp);


        }
    }
}
        // Step 2: Feed the element Inputs to Circuit Inputs
        for(int i=0; i<inputList.size(); i++) {
            inbuiltCkt.getCircuitInputAt(i).setDataValue(inputList.elementAt(i).getDataValue());
            inbuiltCkt.getCircuitInputAt(i).setNewValue(inputList.elementAt(i).getDataValue());
        }
        
        // Step 3: Process the inbuilt Ckt
        boolean retStatus = inbuiltCkt.processCircuit(Boolean.TRUE);
        if(!retStatus)
        {
          //  System.out.println("Unable to Process Generic Element - Unsufficient Inputs");
            process_state=false;
        }
        // Step 4: Pass on the inbuiltCkt outputs to element Outputs
     for(cnt_i=0;cnt_i<inbuiltCkt.numOutputs;cnt_i++)
    {
    for(cnt_j=cnt_i+1;cnt_j<numOutputs;cnt_j++)
    {
        Output temp;
        if(inbuiltCkt.circuitOutputsList.elementAt(cnt_j).getLocation().y < inbuiltCkt.circuitOutputsList.elementAt(cnt_i).getLocation().y )
        {
            temp=inbuiltCkt.circuitOutputsList.elementAt(cnt_j);
            inbuiltCkt.circuitOutputsList.set(cnt_j, inbuiltCkt.circuitOutputsList.elementAt(cnt_i));
            inbuiltCkt.circuitOutputsList.set(cnt_i, temp);


        }
    }
}
        for(int i=0; i<outputList.size(); i++) {
            outputList.elementAt(i).setDataValue(inbuiltCkt.getCircuitOutputAt(i).getDataValue());
        }
    }

    /*
     * Overridden function draw
     * Draws an And Gate at point "p"
     */
    @Override
    public void draw(Graphics g, Point p){
        Graphics2D g2d = (Graphics2D)g;
                
        // Draw the filled polygon - black box
        g2d.setColor(Color.GRAY);                               // fill Gray Color
        Point center = mainPanel.downScale(p);
        int[] xPoints = {(center.x-(maxIO-2))*20+10, (center.x+(maxIO-2))*20+10, (center.x+(maxIO-2))*20+10, (center.x-(maxIO-2))*20+10};
        int[] yPoints = {(center.y-(maxIO-1))*20+10, (center.y-(maxIO-1))*20+10, (center.y+(maxIO-1))*20+10, (center.y+(maxIO-1))*20+10};
        g2d.fillPolygon(xPoints, yPoints, 4);
        
        g2d.setStroke(new BasicStroke(3));                      // draw the bounding rectangle
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPoints, yPoints, 4);
        String tmp_name1[]=elementName.split("_");
        String tmp_name=tmp_name1[2];
        if(tmp_name.length() > 3)
        {
            tmp_name=tmp_name.substring(0,3);
        }
        Font font = new Font("Times New Roman", Font.BOLD, 24);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(tmp_name, p.x-20, p.y);
        // Draw the inputs for the generic element - to the black box
        int starty = (center.y - (numInputs-1))*20+10;
        for(int i=0; i<numInputs; i++) {
            inputList.elementAt(i).draw(g, new Point((center.x-(maxIO-2))*20+10, starty));
            starty = starty + 40;
        }
        
        // Draw the outputs for the generic element - from the black box
        starty = (center.y - (numOutputs-1))*20+10;
        for(int i=0; i<numOutputs; i++) {
            outputList.elementAt(i).draw(g, new Point((center.x+(maxIO-2))*20+10, starty));
            starty = starty + 40;
        }
    }
}
