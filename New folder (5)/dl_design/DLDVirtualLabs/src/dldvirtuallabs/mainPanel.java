/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * mainPanel.java
 *
 * Created on 28 Apr, 2011, 3:23:48 PM
 */
package dldvirtuallabs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;

/**
 *
 * @author rajesh, buddy
 * mainPanel is the content holder for the entire applet, and it consists of the following sub-panels:
 *      - topPanel      : which contains the Save, Load, Clear, Import, Simulate buttons
 *      - leftPanel     : which contains all the buttons corresponding to the gates
 *      - midPanel      : the panel where the circuit is drawn
 *      - rightPanel    : the panel where the time pulse graphs are drawn
 *      - inputPanel    : the panel containing the inputs
 */
public final class mainPanel extends javax.swing.JPanel  {

    /*
     * An enumeration of all buttons, so as to toggle the corresponding state
     * when a button is clicked, and then doing the corresponding action when a mouse event happens.
     */
    private enum buttons {
        NONE, OR, OR3, OR4, AND, AND3, AND4, NOR,NOR3,NOR4, NAND,NAND3,NAND4, XOR,XOR3,XOR4, XNOR,XNOR3,XNOR4, NOT,CONNECTOR, WIRE,WIRE1, BINARY_ONE, BINARY_ZERO, DELETE, PROBE, DISCONNECT,DISCONNECT1, GENERIC_ELEMENT, TIME_PULSE,output,Naming
    }

    /*
     * activeButton         :   Stores the current active button.
     *                          This is set to one of the above buttons, when a button is clicked,
     *                          and reset to NONE, when the task is finished.
     * workPanelSize        :   Stores the size of the midPanel,
     *                          so as to have a corresponding meshID and meshType matrix of the same size
     * currCircuit          :   the instance of the Circuit class, that is being built now
     * genericElementCkt    :   an instance of Circuit class, that gets instantiated when a circuit containing a genericElement is loaded
     * elementCount         :   the count of the total number of elements existing in the circuit. This count is used to assign IDs to the elements
     * inputCount           :   the count of the total number of input nodes existing in the circuit. This count is used to assign IDs to the nodes
     * outputCount          :   the count of the total number of output nodes existing in the circuit. This count is used to assign IDs to the nodes
     * ip                   :   an instance of the class Input, that is used while connecting an input to output, and vice-versa
     * op                   :   an instance of the class Output, that is used while connecting an input to output, and vice-versa
     * dragElement          :   is set to the element that is being dragged
     * draggingElement      :   a boolean state that is set to true - if a drag action is being performed, false - otherwise.
     * dragAction           :   a boolean state that is set to true - if the mouse is being dragged now, false - otherwise.
     * temp1, temp2         :   temporary points
     * srcDragPoint         :   the source of the drag event - i.e. the point where the drag event begins
     * timePulses           :   is a HashMap with keys as the labels of the time-pulses,
     *                          and the values as a vector of the time-pulse's uptime and downtimes.
     *                          It stores all the time-pulses, a user creates.
     * timePulses_EXT       :   same as timePulses, except that the vector now contains time values,
     *                          where there is a change in state (from 0 to 1, and vice-versa) - instead of uptime and downtime
     * activeTimePulses     :   a HashMap with keys as the Input nodes,
     *                          and values as the labels of the time-pulse associated with that Input node.
     *                          It basically stores all the time-pulses, which are being used in a circuit.
     * timePulseOutputs     :   a HashMap with keys as the Output nodes,
     *                          and values as a vector of the outputs corresponding to the time values of the time-pulse.
     *                          It basically stores the list of output value at the Output node, for every state change in the time-pulse.
     * timePulse_InputProbes:   Similar to timePulseOutputs, but -
     *                          It basically stores the list of values at the Inpout node - which is being probed.
     * timePulse_OutputProbes:  Similar to timePulseOutputs, but -
     *                          It basically stores the list of values at the Output node - which is being probed.
     * timePulseChanges     :   a list of the time values, when there is a change of state in any of the time pulses.
     *                          Necessarily the time values, where the entire circuit should be processed again.
     * timePulseChanges_sorted: same as timePulseChanges, except that this is its sorted version
     * drawn                :   a list of the time-pulses, which have been drawn already -
     *                          so as to avoid repetition when the same time=pulse is used more than once
     * simulated            :   a boolean state which is set to true if simulation was the recent event, and false otherwise
     */

    private buttons activeButton;
    private Dimension workPanelSize;
    private Circuit currCircuit;
    private Circuit genericElementCkt;
    private int elementCount;
    private int inputCount;
    private int outputCount;
    public Input ip;
    public Output op;
    public Element dragElement;
    public Point dragloc;
    private boolean draggingElement;
    private boolean dragAction;
    private Point temp1;
    private Point temp2;
    private String gen_name;
    private Point srcDragPoint;
    private HashMap<String, Vector<Integer>> timePulses;
    private HashMap<String, Vector<Integer>> timePulses_EXT;
    private HashMap<Input, String> activeTimePulses;
    private HashMap<Output, Vector<Integer>> timePulseOutputs;
    private HashMap<Input, Vector<Integer>> timePulse_InputProbes;
    private HashMap<Output, Vector<Integer>> timePulse_OutputProbes;
    private HashSet<Integer> timePulseChanges;
    ArrayList timePulseChanges_sorted;
    private HashSet<String> drawn;
    private boolean simulated;

    /** Creates new form mainPanel */
    public mainPanel(String content, String file_list[]) {
        super(new BorderLayout());
        initComponents();
        initializeData();
        timePulses = new HashMap<String, Vector<Integer>>();
      timePulses_EXT = new HashMap<String, Vector<Integer>>();
      jScrollPane3.repaint();
      pulseParams.setText("a:0,10,10,10,10,10,10,10,10,10,10,10");
      timePulseActionPerformed(null);
      pulseParams.setText("b:10,10,10,10,10,10,10,10,10,10,10");
      timePulseActionPerformed(null);
      pulseParams.setText("c:0,10,20,10,20,10,20,10,20,10");
      timePulseActionPerformed(null);
      pulseParams.setText("d:0,20,20,20,20,20,20,20");
      timePulseActionPerformed(null);
      content1.setText(content);
       
      for (int i=0; i<file_list.length;i++)
      {
       load_combo.addItem(file_list[i]);
      // import_combo.addItem(file_list[i]);
        }
      repaint();
    }

    /* Initializing all the variables corresponding to the current class */
    public void initializeData() {
      // timePulseList.empty();
        activeButton = buttons.NONE;
       //  timePulseList = new javax.swing.JList();
        // a new instance of the Circuit class, which would now be used
        currCircuit = new Circuit(1, "Circuit-1");
        genericElementCkt = new Circuit();
        elementCount = 0;
        inputCount = 0;
        outputCount = 0;
        workPanelSize = midPanel.getSize();
        draggingElement = false;
        dragAction = false;
        temp1 = temp2 = null;
        gen_name="Generic_Element";
        srcDragPoint = new Point();
      // timePulses = new HashMap<String, Vector<Integer>>();
      // timePulses_EXT = new HashMap<String, Vector<Integer>>();
        activeTimePulses = new HashMap<Input, String>();
        simulated = false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftPanel = new javax.swing.JPanel();
        selectedGate = new javax.swing.JLabel();
        wire = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();
        deleteElementButton = new javax.swing.JButton();
        probeButton = new javax.swing.JButton();
        output = new javax.swing.JButton();
        Naming = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        select_gate = new javax.swing.JButton();
        topPanel = new javax.swing.JPanel();
        simulateButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        genericElement = new javax.swing.JButton();
        load_combo = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        logField = new javax.swing.JTextField();
        inputPanel = new javax.swing.JPanel();
        zeroButton = new javax.swing.JButton();
        oneButton = new javax.swing.JButton();
        timePulse = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        timePulseList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        currentTimePulse = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane(pulseParams);
        pulseParams = new javax.swing.JTextPane();
        rightPanel1 = new javax.swing.JScrollPane(rightPanel);
        rightPanel1.getHorizontalScrollBar().addAdjustmentListener(new MyAction());
        rightPanel1.getVerticalScrollBar().addAdjustmentListener(new MyAction());
        rightPanel = new OutputPanel();
        jScrollPane3 = new javax.swing.JScrollPane(midPanel);
        jScrollPane3.getHorizontalScrollBar().addAdjustmentListener(new MyAction1());
        jScrollPane3.getVerticalScrollBar().addAdjustmentListener(new MyAction1());
        midPanel = new WorkPanel();
        content1 = new javax.swing.JTextField();

        setMinimumSize(new java.awt.Dimension(512, 128));
        setPreferredSize(new java.awt.Dimension(1000, 660));

        leftPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic Elements"));
        leftPanel.setPreferredSize(new java.awt.Dimension(179, 445));

        selectedGate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedGate.setLabelFor(leftPanel);
        selectedGate.setText("Selected Icon");
        selectedGate.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        selectedGate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        selectedGate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectedGate.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        wire.setText("CONNECT");
        wire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wireActionPerformed(evt);
            }
        });

        disconnectButton.setText("DISCONNECT");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        deleteElementButton.setText("Delete Element");
        deleteElementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteElementButtonActionPerformed(evt);
            }
        });

        probeButton.setText("Probe");
        probeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                probeButtonActionPerformed(evt);
            }
        });

        output.setText("output");
        output.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputActionPerformed(evt);
            }
        });

        Naming.setText("Naming");
        Naming.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NamingActionPerformed(evt);
            }
        });

        jButton1.setText("None");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AND", "OR", "NAND", "NOR", "XOR", "XNOR", "NOT", "CONN" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "3", "4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        select_gate.setText("Select");
        select_gate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                select_gateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(select_gate, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(deleteElementButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(disconnectButton, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(wire, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(Naming, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(selectedGate, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                        .addComponent(probeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(select_gate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(output)
                    .addComponent(probeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteElementButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(disconnectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wire)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Naming)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectedGate, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        topPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topPanel.setPreferredSize(new java.awt.Dimension(878, 42));

        simulateButton.setText("Simulate");
        simulateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulateButtonActionPerformed(evt);
            }
        });

        loadButton.setText("Load");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        genericElement.setText("Import");
        genericElement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                genericElementMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                genericElementMouseExited(evt);
            }
        });
        genericElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genericElementActionPerformed(evt);
            }
        });

        jButton2.setText("Load_it");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Import_it");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addComponent(loadButton)
                .addGap(2, 2, 2)
                .addComponent(genericElement)
                .addGap(2, 2, 2)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(load_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(simulateButton))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadButton)
                    .addComponent(genericElement)
                    .addComponent(simulateButton)
                    .addComponent(jButton3)
                    .addComponent(saveButton)
                    .addComponent(clearButton)
                    .addComponent(load_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        logField.setEditable(false);
        logField.setFont(new java.awt.Font("Tahoma", 1, 18));
        logField.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        logField.setPreferredSize(new java.awt.Dimension(1000, 26));
        logField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logFieldActionPerformed(evt);
            }
        });

        inputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Inputs"));
        inputPanel.setPreferredSize(new java.awt.Dimension(179, 302));

        zeroButton.setText("0");
        zeroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zeroButtonActionPerformed(evt);
            }
        });

        oneButton.setText("1");
        oneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oneButtonActionPerformed(evt);
            }
        });

        timePulse.setText("New Time Pulse");
        timePulse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timePulseActionPerformed(evt);
            }
        });

        timePulseList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        timePulseList.setValueIsAdjusting(true);
        timePulseList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                timePulseListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(timePulseList);

        jLabel1.setText("Available Time Pulses :");

        jLabel2.setText("Selected Time Pulse :");

        currentTimePulse.setText("- - NONE - -");
        currentTimePulse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentTimePulseActionPerformed(evt);
            }
        });

        pulseParams.setPreferredSize(new java.awt.Dimension(6, 64));
        pulseParams.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pulseParamsMouseClicked(evt);
            }
        });
        pulseParams.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pulseParamsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pulseParamsKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(pulseParams);

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(currentTimePulse, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(14, Short.MAX_VALUE))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(17, Short.MAX_VALUE))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inputPanelLayout.createSequentialGroup()
                                .addComponent(zeroButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(oneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(timePulse, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                        .addGap(298, 298, 298))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        inputPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {oneButton, zeroButton});

        inputPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {currentTimePulse, jScrollPane1, timePulse});

        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oneButton)
                    .addComponent(zeroButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timePulse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(currentTimePulse))
        );

        rightPanel1.setPreferredSize(new java.awt.Dimension(160, 719));

        rightPanel.setPreferredSize(new java.awt.Dimension(160, 719));
        rightPanel.setRequestFocusEnabled(false);
        rightPanel.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );

        rightPanel1.setViewportView(rightPanel);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(650, 719));

        midPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Workspace"));
        midPanel.setAutoscrolls(true);
        midPanel.setPreferredSize(new java.awt.Dimension(2400, 1200));
        midPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                midPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                midPanelMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                midPanelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                midPanelMouseReleased(evt);
            }
        });
        midPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                midPanelComponentResized(evt);
            }
        });
        midPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                midPanelMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                midPanelMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout midPanelLayout = new javax.swing.GroupLayout(midPanel);
        midPanel.setLayout(midPanelLayout);
        midPanelLayout.setHorizontalGroup(
            midPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2388, Short.MAX_VALUE)
        );
        midPanelLayout.setVerticalGroup(
            midPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1171, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(midPanel);

        content1.setEditable(false);
        content1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        content1.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                            .addComponent(content1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                            .addComponent(topPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)))
                    .addComponent(logField, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(content1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rightPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        content1.getAccessibleContext().setAccessibleName("none");
    }// </editor-fold>//GEN-END:initComponents


   public class MyAction implements AdjustmentListener{
  public void adjustmentValueChanged(AdjustmentEvent ae){
  rightPanel1.repaint();
  }
  }

   public class MyAction1 implements AdjustmentListener{
  public void adjustmentValueChanged(AdjustmentEvent ae){
  jScrollPane3.repaint();
  }
  }

    /*
     * This function handles mouse-click event corresponding to the midPanel (i.e. where the circuit is being drawn)
     * The click event is responsible for :
     *      - putting new gates
     *      - putting input-sources (static as well as time-pulses) at Input nodes
     *      - setting probe points
     */
    private void midPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_midPanelMouseClicked
        // TODO add your handling code here:
        
        selectedGate.setIcon(null);
       // midPanel.setCursor(Cursor.getDefaultCursor());
        Point loc = downScale(clip(evt.getPoint()));                // get the location and downScale it to the dimensions of the matrices - meshType and meshID

        // if the click is at the periphery of the panel => invalid action
       // System.out.println("mouse_clicked");
        if ((loc.x - 5 < 0 || loc.y - 3 < 0 ||loc.x+5>117 || loc.y+4>61) && (activeButton == buttons.AND || activeButton == buttons.NAND || activeButton == buttons.NOR || activeButton == buttons.NOT || activeButton == buttons.OR || activeButton == buttons.XNOR || activeButton == buttons.XOR )) {
            logField.setText("Can't put anything there, as its too close to the boundary");
          //  activeButton = buttons.NONE;
            return;
        }
        if((activeButton == buttons.AND || activeButton == buttons.NAND || activeButton == buttons.NOR || activeButton == buttons.NOT || activeButton == buttons.OR || activeButton == buttons.XNOR || activeButton == buttons.XOR ))
        {
             if(activeButton==buttons.NOT)
           {
               for (int i = -3; i <= 4; i++) {                               // update element type and ID
            for (int j = -1; j <= 2; j++) {
                if( (currCircuit.meshType[loc.y + j][loc.x + i]!=0))
                {
                     logField.setText("Can't put a gate here, too close to other gates");
                    return;

                }

            }
        }

           }
           else
           {
                 for (int i = -3; i <= 4; i++) {                               // update element type and ID
            for (int j = -2; j <= 3; j++) {
                if( (currCircuit.meshType[loc.y + j][loc.x + i]!=0))
                {
                    logField.setText("Can't put a gate here, too close to other gates");
                    return;

                }

            }
        }
           }
        }

        // set the data-value corresponding to the input node to 1
        if (activeButton == buttons.BINARY_ONE) {
            if (currCircuit.meshType[loc.y][loc.x] != 1) {
                logField.setText("Cannot add input source here");
            } else {
                Input inp = currCircuit.allInputsList.get(currCircuit.meshID[loc.y][loc.x]);
                // if the input node was previously associated with a time-pulse, then remove it from activeTimePulses
                if (inp.getSourceOutputNode() == null) {
                    if (inp.getTimePulseLabel() != null) {
                        activeTimePulses.remove(inp);
                        inp.setTimePulseLabel(null);
                    }
                    inp.setDataValue(1);
                    inp.setNewValue(1);
                } else {
                    logField.setText("Cannot add input source here");
                }
            }
        } else if (activeButton == buttons.BINARY_ZERO) {   // set the data-value corresponding to the input node to 0
            if (currCircuit.meshType[loc.y][loc.x] != 1) {
                logField.setText("Cannot add input source here");
            } else {
                Input inp = currCircuit.allInputsList.get(currCircuit.meshID[loc.y][loc.x]);
                // if the input node was previously associated with a time-pulse, then remove it from activeTimePulses
                if (inp.getSourceOutputNode() == null) {
                    if (inp.getTimePulseLabel() != null) {
                        activeTimePulses.remove(inp);
                        inp.setTimePulseLabel(null);
                    }
                    inp.setDataValue(0);
                    inp.setNewValue(0);
                } else {
                    logField.setText("Cannot add input source here");
                }
            }
        } else if (activeButton == buttons.TIME_PULSE) {    // link the time-pulse with the input node, by putting them in the activeTimePulses
            if (currCircuit.meshType[loc.y][loc.x] != 1) {
                logField.setText("Cannot add input source here");
            } else {
                Input inp = currCircuit.allInputsList.get(currCircuit.meshID[loc.y][loc.x]);
                // if the input node was previously associated with a time-pulse, then remove it from activeTimePulses
                if (inp.getSourceOutputNode() == null) {
                    if (inp.getTimePulseLabel() != null) {
                        activeTimePulses.remove(inp);
                    }
                    inp.setTimePulseLabel(currentTimePulse.getText());
                    activeTimePulses.put(inp, currentTimePulse.getText());
                } else {
                    logField.setText("Cannot add input source here");
                }
            }
        }
        else if (activeButton == buttons.output) {         // set the corresponding node's 'probed' property to true
            if (currCircuit.meshType[loc.y][loc.x] == 2) {
                Output out = currCircuit.allOutputsList.get(currCircuit.meshID[loc.y][loc.x]);
             JOptionPane.showMessageDialog(null, "output node is set" );
                out.setstatevalue(1);
               // simulateButtonActionPerformed(null);
            } else {
                logField.setText("Cannot make output terminal here");
            }
        }
        else if (activeButton == buttons.Naming) {         // set the corresponding node's 'probed' property to true
            if (currCircuit.meshType[loc.y][loc.x] == 2) {
                Output out = currCircuit.allOutputsList.get(currCircuit.meshID[loc.y][loc.x]);
                String resp = JOptionPane.showInputDialog(null, "Enter the output name");
                out.setname(resp);
                //System.out.println("output here");
                //out.setstatevalue(1);
               // simulateButtonActionPerformed(null);
            }
            else if (currCircuit.meshType[loc.y][loc.x] == 1) {
                Input in = currCircuit.allInputsList.get(currCircuit.meshID[loc.y][loc.x]);
                String resp = JOptionPane.showInputDialog(null, "Enter the input name");
                in.setname(resp);
            }
            else {
                logField.setText("Cannot make output terminal here");
            }
        }
        else if (activeButton == buttons.PROBE) {         // set the corresponding node's 'probed' property to true
            if (currCircuit.meshType[loc.y][loc.x] == 1) {
                Input inp = currCircuit.allInputsList.get(currCircuit.meshID[loc.y][loc.x]);
                inp.setProbed(true);
               // simulateButtonActionPerformed(null);
            } else if (currCircuit.meshType[loc.y][loc.x] == 2) {
                Output out = currCircuit.allOutputsList.get(currCircuit.meshID[loc.y][loc.x]);
                out.setProbed(true);
               // simulateButtonActionPerformed(null);
            } else {
                logField.setText("Cannot probe here");
            }
        }
        else if(activeButton == buttons.WIRE) {
            temp1 = new Point(evt.getPoint());
            if (currCircuit.meshType[loc.y][loc.x] == 2) {
                logField.setText("Identified output node");
                op = (Output) currCircuit.allOutputsList.get(currCircuit.meshID[loc.y][loc.x]);
                activeButton=buttons.WIRE1;
            } else if (currCircuit.meshType[loc.y][loc.x] == 1) {
                logField.setText("Identified input node");
                ip = (Input) currCircuit.allInputsList.get(currCircuit.meshID[loc.y][loc.x]);
                activeButton=buttons.WIRE1;
            }
        } else if (activeButton == buttons.DISCONNECT) {
            temp1 = new Point(evt.getPoint());
            if (currCircuit.meshType[loc.y][loc.x] == 2) {
                logField.setText("Identified output node");
                op = (Output) currCircuit.allOutputsList.get(currCircuit.meshID[loc.y][loc.x]);
                activeButton=buttons.DISCONNECT1;
            } else if (currCircuit.meshType[loc.y][loc.x] == 1) {
                logField.setText("Identified input node");
                ip = (Input) currCircuit.allInputsList.get(currCircuit.meshID[loc.y][loc.x]);
                activeButton=buttons.DISCONNECT1;
            }}
        else if(activeButton == buttons.WIRE1) {
            //activeButton=buttons.NONE;
            //midPanel.setCursor(Cursor.getDefaultCursor());
            Point srcLoc = downScale(clip(new Point(temp1)));
            if (currCircuit.meshType[loc.y][loc.x] == 2 && currCircuit.meshType[srcLoc.y][srcLoc.x] != 2) {
                activeButton=buttons.WIRE;
                op = (Output) currCircuit.allOutputsList.get(currCircuit.meshID[loc.y][loc.x]);
                boolean status = false;
                if (ip.getSourceOutputNode() == null) {
                    status = op.addDestInpId(ip.getInputID());
                } else {
                    logField.setText("Invalid action");
                }
                if (status) {
                    if (ip.getTimePulseLabel() != null) {
                        activeTimePulses.remove(ip);
                        ip.setTimePulseLabel(null);
                    }
                    ip.setSourceOutput(op);
                    op.addDestinationInput(ip);
                    logField.setText("Connected " + ip.getAncestor().getElementName()
                            + " with " + op.getAncestor().getElementName());
                } else {
                    logField.setText("Connection between " + ip.getAncestor().getElementName()
                            + " and " + op.getAncestor().getElementName() + " already exists");
                }
            } else if (currCircuit.meshType[loc.y][loc.x] == 1 && currCircuit.meshType[srcLoc.y][srcLoc.x] != 1) {
                activeButton=buttons.WIRE;
                ip = (Input) currCircuit.allInputsList.get(currCircuit.meshID[loc.y][loc.x]);
                boolean status = false;
                if (ip.getSourceOutputNode() == null) {
                    status = op.addDestInpId(ip.getInputID());
                } else {
                    logField.setText("Invalid action");
                }
                if (status) {
                    if (ip.getTimePulseLabel() != null) {
                        activeTimePulses.remove(ip);
                        ip.setTimePulseLabel(null);
                    }
                    ip.setSourceOutput(op);
                    op.addDestinationInput(ip);
                    logField.setText("Connected " + ip.getAncestor().getElementName()
                            + " with " + op.getAncestor().getElementName());
                } else {
                    logField.setText("Connection between " + ip.getAncestor().getElementName()
                            + " and " + op.getAncestor().getElementName() + " already exists");
                }
            } else {
                logField.setText("Undefined point");
              //  activeButton = buttons.NONE;
            }
        }
        else if(activeButton == buttons.DISCONNECT1) {
            //activeButton=buttons.NONE;
            //Point p = downScale(clip(evt.getPoint()));
           // midPanel.setCursor(Cursor.getDefaultCursor());
            if (currCircuit.meshType[loc.y][loc.x] == 2) {
                activeButton=buttons.DISCONNECT;
                op = (Output) currCircuit.allOutputsList.get(currCircuit.meshID[loc.y][loc.x]);

                boolean status = op.delDestInpNode(ip);
                if (status) {
                    ip.setSourceOutput(null);
                    logField.setText("Disconnected " + ip.getAncestor().getElementName()
                            + " from " + op.getAncestor().getElementName());
                } else {
                    logField.setText("No Connection between " + ip.getAncestor().getElementName()
                            + " and " + op.getAncestor().getElementName() + " to disconnect");
                }
            } else if (currCircuit.meshType[loc.y][loc.x] == 1) {
                ip = (Input) currCircuit.allInputsList.get(currCircuit.meshID[loc.y][loc.x]);
                boolean status = op.delDestInpNode(ip);
                if (status) {
                    ip.setSourceOutput(null);
                    logField.setText("Disconnected " + ip.getAncestor().getElementName()
                            + " from " + op.getAncestor().getElementName());
                } else {
                    logField.setText("No Connection between " + ip.getAncestor().getElementName()
                            + " and " + op.getAncestor().getElementName() + " to disconnect");
                }
            } else {
                logField.setText("Undefined point");
              //  activeButton = buttons.NONE;
            }
        }
        else if (currCircuit.meshType[loc.y][loc.x] != 0    // the conditions for which putting a gate at the location is disallowed
                && (activeButton != buttons.BINARY_ONE
                && activeButton != buttons.BINARY_ZERO
                && activeButton != buttons.DELETE
                && activeButton != buttons.DISCONNECT
                && activeButton != buttons.PROBE
                && activeButton != buttons.TIME_PULSE)) {
            logField.setText("Can't put a gate here, as another one already exists");
            activeButton = buttons.NONE;
             midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } else if (activeButton == buttons.AND) {           // put a new AND gate
            AndGate newAndGate = new AndGate(elementCount, "And_Gate", inputCount, outputCount, loc);
            newAndGate.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newAndGate);
            elementCount = elementCount + 1;
            inputCount = inputCount + 2;
            outputCount = outputCount + 1;
            logField.setText(newAndGate.getElementName() + " added to circuit");
        }else if (activeButton == buttons.AND3) {           // put a new AND gate
            AndGate3 newAndGate3 = new AndGate3(elementCount, "3And_Gate", inputCount, outputCount, loc);
            newAndGate3.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newAndGate3);
            elementCount = elementCount + 1;
            inputCount = inputCount + 3;
            outputCount = outputCount + 1;
            logField.setText(newAndGate3.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.AND4) {           // put a new AND gate
            AndGate4 newAndGate4 = new AndGate4(elementCount, "4And_Gate", inputCount, outputCount, loc);
            newAndGate4.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newAndGate4);
            elementCount = elementCount + 1;
            inputCount = inputCount + 4;
            outputCount = outputCount + 1;
            logField.setText(newAndGate4.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.OR) {            // put a new OR gate
            OrGate newOrGate = new OrGate(elementCount, "Or_Gate", inputCount, outputCount, loc);
            newOrGate.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newOrGate);
            elementCount = elementCount + 1;
            inputCount = inputCount + 2;
            outputCount = outputCount + 1;
            logField.setText(newOrGate.getElementName() + " added to circuit");
        }
         else if (activeButton == buttons.OR3) {            // put a new OR gate
            OrGate3 newOrGate3 = new OrGate3(elementCount, "3Or_Gate", inputCount, outputCount, loc);
            newOrGate3.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newOrGate3);
            elementCount = elementCount + 1;
            inputCount = inputCount + 3;
            outputCount = outputCount + 1;
            logField.setText(newOrGate3.getElementName() + " added to circuit");
        }
         else if (activeButton == buttons.OR4) {            // put a new OR gate
            OrGate4 newOrGate4 = new OrGate4(elementCount, "4Or_Gate", inputCount, outputCount, loc);
            newOrGate4.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newOrGate4);
            elementCount = elementCount + 1;
            inputCount = inputCount + 4;
            outputCount = outputCount + 1;
            logField.setText(newOrGate4.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.NAND) {          // put a new NAND gate
            NandGate newNandGate = new NandGate(elementCount, "Nand_Gate", inputCount, outputCount, loc);
            newNandGate.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newNandGate);
            elementCount = elementCount + 1;
            inputCount = inputCount + 2;
            outputCount = outputCount + 1;
            logField.setText(newNandGate.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.NAND3) {          // put a new NAND gate
            NandGate3 newNandGate3 = new NandGate3(elementCount, "3Nand_Gate", inputCount, outputCount, loc);
            newNandGate3.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newNandGate3);
            elementCount = elementCount + 1;
            inputCount = inputCount + 3;
            outputCount = outputCount + 1;
            logField.setText(newNandGate3.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.NAND4) {          // put a new NAND gate
            NandGate4 newNandGate4 = new NandGate4(elementCount, "4Nand_Gate", inputCount, outputCount, loc);
            newNandGate4.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newNandGate4);
            elementCount = elementCount + 1;
            inputCount = inputCount + 4;
            outputCount = outputCount + 1;
            logField.setText(newNandGate4.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.NOR) {           // put a new NOR gate
            NorGate newNorGate = new NorGate(elementCount, "Nor_Gate", inputCount, outputCount, loc);
            newNorGate.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newNorGate);
            elementCount = elementCount + 1;
            inputCount = inputCount + 2;
            outputCount = outputCount + 1;
            logField.setText(newNorGate.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.NOR3) {           // put a new NOR gate
            NorGate3 newNorGate3 = new NorGate3(elementCount, "3Nor_Gate", inputCount, outputCount, loc);
            newNorGate3.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newNorGate3);
            elementCount = elementCount + 1;
            inputCount = inputCount + 3;
            outputCount = outputCount + 1;
            logField.setText(newNorGate3.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.NOR4) {           // put a new NOR gate
            NorGate4 newNorGate4 = new NorGate4(elementCount, "4Nor_Gate", inputCount, outputCount, loc);
            newNorGate4.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newNorGate4);
            elementCount = elementCount + 1;
            inputCount = inputCount + 4;
            outputCount = outputCount + 1;
            logField.setText(newNorGate4.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.XOR) {           // put a new XOR gate
            XorGate newXorGate = new XorGate(elementCount, "Xor_Gate", inputCount, outputCount, loc);
            newXorGate.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newXorGate);
            elementCount = elementCount + 1;
            inputCount = inputCount + 2;
            outputCount = outputCount + 1;
            logField.setText(newXorGate.getElementName() + " added to circuit");
        }
         else if (activeButton == buttons.XOR3) {           // put a new XOR gate
            XorGate3 newXorGate3 = new XorGate3(elementCount, "3Xor_Gate", inputCount, outputCount, loc);
            newXorGate3.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newXorGate3);
            elementCount = elementCount + 1;
            inputCount = inputCount + 3;
            outputCount = outputCount + 1;
            logField.setText(newXorGate3.getElementName() + " added to circuit");
        }
         else if (activeButton == buttons.XOR4) {           // put a new XOR gate
            XorGate4 newXorGate4 = new XorGate4(elementCount, "4Xor_Gate", inputCount, outputCount, loc);
            newXorGate4.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newXorGate4);
            elementCount = elementCount + 1;
            inputCount = inputCount + 4;
            outputCount = outputCount + 1;
            logField.setText(newXorGate4.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.XNOR) {          // put a new XNOR gate
            XnorGate newXnorGate = new XnorGate(elementCount, "Xnor_Gate", inputCount, outputCount, loc);
            newXnorGate.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newXnorGate);
            elementCount = elementCount + 1;
            inputCount = inputCount + 2;
            outputCount = outputCount + 1;
            logField.setText(newXnorGate.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.XNOR3) {          // put a new XNOR gate
            XnorGate3 newXnorGate3 = new XnorGate3(elementCount, "3Xnor_Gate", inputCount, outputCount, loc);
            newXnorGate3.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newXnorGate3);
            elementCount = elementCount + 1;
            inputCount = inputCount + 3;
            outputCount = outputCount + 1;
            logField.setText(newXnorGate3.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.XNOR4) {          // put a new XNOR gate
            XnorGate4 newXnorGate4 = new XnorGate4(elementCount, "4Xnor_Gate", inputCount, outputCount, loc);
            newXnorGate4.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newXnorGate4);
            elementCount = elementCount + 1;
            inputCount = inputCount + 4;
            outputCount = outputCount + 1;
            logField.setText(newXnorGate4.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.NOT) {           // put a new NOT gate
            NotGate newNotGate = new NotGate(elementCount, "Not_Gate", inputCount, outputCount, loc);
            newNotGate.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newNotGate);
            elementCount = elementCount + 1;
            inputCount = inputCount + 1;
            outputCount = outputCount + 1;
            logField.setText(newNotGate.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.CONNECTOR) {           // put a new NOT gate
            Connector newConnector = new Connector(elementCount, "conn", inputCount, outputCount, loc);
            newConnector.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newConnector);
            elementCount = elementCount + 1;
            inputCount = inputCount + 1;
            outputCount = outputCount + 1;
            logField.setText(newConnector.getElementName() + " added to circuit");
        }
        else if (activeButton == buttons.GENERIC_ELEMENT) {   // put a new generic element
            int max_io = Math.max(genericElementCkt.numInputs, genericElementCkt.numOutputs)+1;
            int flagu=0;
            for (int i = -max_io; i <= max_io; i++) {                               // update element type and ID
            for (int j = -max_io; j <= max_io; j++) {
                if((loc.y + j>=0 && loc.x + i>=0 )&& (currCircuit.meshType[loc.y + j][loc.x + i]!=0))
                {
                    flagu=1;
                    break;
                }

            }
        }
            if((loc.x>(1+max_io) && loc.x<(117-max_io)) && (loc.y>=(max_io) && loc.y<(61-max_io)) && flagu==0)
            {
            GenericElement newGenElement = new GenericElement(elementCount, "Generic_Element", inputCount, outputCount, loc, genericElementCkt);
            newGenElement.setElementName("Generic_Element_"+gen_name+String.valueOf(elementCount));
            newGenElement.updateMatrix(loc, currCircuit.meshType, currCircuit.meshID, null);
            currCircuit.addElement(newGenElement);
            elementCount = elementCount + 1;
            inputCount = inputCount + newGenElement.getNumInputs();
            outputCount = outputCount + newGenElement.getNumOutputs();
            logField.setText(newGenElement.getElementName() + " added to circuit");
            genericElementCkt = null;
            gen_name="Generic_Element";
             activeButton = buttons.NONE;
             midPanel.setCursor(Cursor.getDefaultCursor());
            }
            else
            {
                if(flagu==1)
                {
                    logField.setText("Can't put a gate here, too close to other gates");
                }
                else
                {
                logField.setText("Can't put anything there, as its too close to the boundary");
                }
                System.out.println("can't make");
            activeButton = buttons.NONE;
            genericElementCkt = null;
            gen_name="Generic_Element";
             midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            return;
            }
        } else if (activeButton == buttons.DELETE) {            // delete the corresponding element
            logField.setText("Deleting " + currCircuit.allElementsList.get(currCircuit.meshID[loc.y][loc.x]).getElementName());
            currCircuit.deleteElement(currCircuit.meshID[loc.y][loc.x]);
        } else if (activeButton == buttons.DISCONNECT) {        // disconnect two nodes
            logField.setText("Disconnecting ");
        }
        if(activeButton!=buttons.WIRE1 && activeButton!=buttons.DISCONNECT1)
        {
        //activeButton = buttons.NONE;
        //midPanel.setCursor(Cursor.getDefaultCursor());
        }
        repaint();
    }//GEN-LAST:event_midPanelMouseClicked

    /*
     * this function updates the workPanelSize to the current midPanel's size.
     * it is called whenever, the midPanel gets resized.
     */
    private void midPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_midPanelComponentResized
        // TODO add your handling code here:
        workPanelSize = midPanel.getSize();
        repaint();
    }//GEN-LAST:event_midPanelComponentResized

    /*
     * clips a point to the nearest grid-point within a 10x10 box
     */
    public static Point clip(Point p) {
        return new Point(((p.x + 5) / 10) * 10, ((p.y + 5) / 10) * 10);
     //   return new Point(((p.x + 1) / 2) * 2, ((p.y + 1) / 2) * 2);
    }

    /*
     * downScales a point in the midPanel, to a location in the meshID/meshType matrices
     */
    public static Point downScale(Point p) {
        return new Point((p.x - 10) / 20, (p.y - 10) / 20);
       // return new Point((p.x - 2) / 4, (p.y - 2) / 4);
    }

    /*
     * does the revers of downScale - i.e. upScales a location in the meshID/meshType matrices to a point in the midPanel
     */
    public static Point upScale(Point p) {
        return new Point(p.x * 20 + 10, p.y * 20 + 10);
       // return new Point(p.x * 4 + 2, p.y * 4 + 2);
    }

    /*
     * This function handles the mouse-press event corresponding to the midPanel (i.e. where the circuit is being drawn)
     * The press event is responsible for :
     *          - the node where the connection/disconnection starts
     *          - beginning of a drag action, i.e. dragging an element
     */
    private void midPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_midPanelMousePressed
        // TODO add your handling code here:

        // the following buttons are not related to this event, so return
        if (activeButton != buttons.NONE) {
            return;
        }
        Point p = downScale(clip(evt.getPoint()));

        // to connect or disconnect an input/output node to another node, store the node where the press event happens
       /* if (activeButton == buttons.WIRE) {
            temp1 = new Point(evt.getPoint());
            if (currCircuit.meshType[p.y][p.x] == 2) {
                logField.setText("Identified output node");
                op = (Output) currCircuit.allOutputsList.get(currCircuit.meshID[p.y][p.x]);
            } else if (currCircuit.meshType[p.y][p.x] == 1) {
                logField.setText("Identified input node");
                ip = (Input) currCircuit.allInputsList.get(currCircuit.meshID[p.y][p.x]);
            }
        } else if (activeButton == buttons.DISCONNECT) {
            temp1 = new Point(evt.getPoint());
            if (currCircuit.meshType[p.y][p.x] == 2) {
                logField.setText("Identified output node");
                op = (Output) currCircuit.allOutputsList.get(currCircuit.meshID[p.y][p.x]);
            } else if (currCircuit.meshType[p.y][p.x] == 1) {
                logField.setText("Identified input node");
                ip = (Input) currCircuit.allInputsList.get(currCircuit.meshID[p.y][p.x]);
            }
        } else */if (activeButton == buttons.NONE && currCircuit.meshType[p.y][p.x] == 3) {   // set the dragging related variables
            midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            dragElement = currCircuit.allElementsList.get(currCircuit.meshID[p.y][p.x]);
           
                srcDragPoint=new Point(dragElement.getLocation());

               // System.out.println("***"+srcDragPoint);
           
            draggingElement = true;
           
            
            dragloc=dragElement.getLocation();
        } else {
            logField.setText("Undefined Point");
            activeButton = buttons.NONE;
        }
    }//GEN-LAST:event_midPanelMousePressed

    /*
     * This function handles the mouse-release event corresponding to the midPanel (i.e. where the circuit is being drawn)
     * The press event is responsible for :
     *          - the node where the connection/disconnection ends
     *          - end of a drag action, i.e. dragging an element
     */
    private void midPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_midPanelMouseReleased
        // TODO add your handling code here:

        // the following buttons are not related to this event, so return
        
        if (activeButton != buttons.NONE) {
            return;
        }
        Point p = downScale(clip(evt.getPoint()));

        midPanel.setCursor(Cursor.getDefaultCursor());

        // connect the node at the release event with the one stored at the press event
       /* if (activeButton == buttons.WIRE) {
            Point srcLoc = downScale(clip(new Point(temp1)));
            if (currCircuit.meshType[p.y][p.x] == 2 && currCircuit.meshType[srcLoc.y][srcLoc.x] != 2) {
                op = (Output) currCircuit.allOutputsList.get(currCircuit.meshID[p.y][p.x]);
                boolean status = false;
                if (ip.getSourceOutputNode() == null) {
                    status = op.addDestInpId(ip.getInputID());
                } else {
                    logField.setText("Invalid action");
                }
                if (status) {
                    if (ip.getTimePulseLabel() != null) {
                        activeTimePulses.remove(ip);
                        ip.setTimePulseLabel(null);
                    }
                    ip.setSourceOutput(op);
                    op.addDestinationInput(ip);
                    logField.setText("Connected " + ip.getAncestor().getElementName()
                            + " with " + op.getAncestor().getElementName());
                } else {
                    logField.setText("Connection between " + ip.getAncestor().getElementName()
                            + " and " + op.getAncestor().getElementName() + " already exists");
                }
            } else if (currCircuit.meshType[p.y][p.x] == 1 && currCircuit.meshType[srcLoc.y][srcLoc.x] != 1) {
                ip = (Input) currCircuit.allInputsList.get(currCircuit.meshID[p.y][p.x]);
                boolean status = false;
                if (ip.getSourceOutputNode() == null) {
                    status = op.addDestInpId(ip.getInputID());
                } else {
                    logField.setText("Invalid action");
                }
                if (status) {
                    if (ip.getTimePulseLabel() != null) {
                        activeTimePulses.remove(ip);
                        ip.setTimePulseLabel(null);
                    }
                    ip.setSourceOutput(op);
                    op.addDestinationInput(ip);
                    logField.setText("Connected " + ip.getAncestor().getElementName()
                            + " with " + op.getAncestor().getElementName());
                } else {
                    logField.setText("Connection between " + ip.getAncestor().getElementName()
                            + " and " + op.getAncestor().getElementName() + " already exists");
                }
            } else {
                logField.setText("Undefined point");
                activeButton = buttons.NONE;
            }
        } else if (activeButton == buttons.DISCONNECT) {
            if (currCircuit.meshType[p.y][p.x] == 2) {
                op = (Output) currCircuit.allOutputsList.get(currCircuit.meshID[p.y][p.x]);

                boolean status = op.delDestInpNode(ip);
                if (status) {
                    ip.setSourceOutput(null);
                    logField.setText("Disconnected " + ip.getAncestor().getElementName()
                            + " from " + op.getAncestor().getElementName());
                } else {
                    logField.setText("No Connection between " + ip.getAncestor().getElementName()
                            + " and " + op.getAncestor().getElementName() + " to disconnect");
                }
            } else if (currCircuit.meshType[p.y][p.x] == 1) {
                ip = (Input) currCircuit.allInputsList.get(currCircuit.meshID[p.y][p.x]);
                boolean status = op.delDestInpNode(ip);
                if (status) {
                    ip.setSourceOutput(null);
                    logField.setText("Disconnected " + ip.getAncestor().getElementName()
                            + " from " + op.getAncestor().getElementName());
                } else {
                    logField.setText("No Connection between " + ip.getAncestor().getElementName()
                            + " and " + op.getAncestor().getElementName() + " to disconnect");
                }
            } else {
                logField.setText("Undefined point");
                activeButton = buttons.NONE;
            }
        } else*/ if (draggingElement && dragAction) {         // upon finishing the drag event, update the matrix and the location of the element

            
            if((p.x>(1+dragElement.maxIO) && p.x<(117-dragElement.maxIO)) && (p.y>=(dragElement.maxIO) && p.y<(61-dragElement.maxIO)))
            {
               //  System.out.println(p);
           //     System.out.println("&&&&"+srcDragPoint);
                 int flag=0;
               Vector  inpindex= new Vector<Integer>();
                Vector outindex= new Vector<Integer>();
                for (int ii=0;ii<dragElement.getNumInputs();ii++)
                {
                    inpindex.add(ii, dragElement.getInputAt(ii).getInputID());
                }
                for (int ii=0;ii<dragElement.getNumOutputs();ii++)
                {
                    outindex.add(ii, dragElement.getOutputAt(ii).getOutputID());
                }
          if(dragElement.getElementType().equals("Generic_Element"))
           {
               for (int i = -dragElement.maxIO; i <= dragElement.maxIO; i++) {                               // update element type and ID
            for (int j = -dragElement.maxIO; j <= dragElement.maxIO; j++) {
                if( (currCircuit.meshType[p.y + j][p.x + i] ==3 && currCircuit.meshID[p.y + j][p.x + i] != dragElement.getElementID()) ||  (currCircuit.meshType[p.y + j][p.x + i] ==1 && !inpindex.contains(currCircuit.meshID[p.y + j][p.x + i]) ) ||  (currCircuit.meshType[p.y + j][p.x + i] ==2 && !outindex.contains(currCircuit.meshID[p.y + j][p.x + i]) ) )
                {
                    flag=1;
                    break;
                }

            }
        }
           }
           else  if(dragElement.getElementType().equals("Not_Gate"))
           {
               for (int i = -3; i <= 4; i++) {                               // update element type and ID
            for (int j = -1; j <= 2; j++) {
                if( (currCircuit.meshType[p.y + j][p.x + i] ==3 && currCircuit.meshID[p.y + j][p.x + i] != dragElement.getElementID()) ||  (currCircuit.meshType[p.y + j][p.x + i] ==1 && !inpindex.contains(currCircuit.meshID[p.y + j][p.x + i]) ) ||  (currCircuit.meshType[p.y + j][p.x + i] ==2 && !outindex.contains(currCircuit.meshID[p.y + j][p.x + i]) ) )
                {
                    flag=1;
                    break;
                }

            }
        }

           }
           else
           {
                 for (int i = -3; i <= 4; i++) {                               // update element type and ID
            for (int j = -2; j <= 3; j++) {
                if( (currCircuit.meshType[p.y + j][p.x + i] ==3 && currCircuit.meshID[p.y + j][p.x + i] != dragElement.getElementID()) ||  (currCircuit.meshType[p.y + j][p.x + i] ==1 && !inpindex.contains(currCircuit.meshID[p.y + j][p.x + i]) ) ||  (currCircuit.meshType[p.y + j][p.x + i] ==2 && !outindex.contains(currCircuit.meshID[p.y + j][p.x + i]) ) )
                {
                    flag=1;
                    break;
                }

            }
        }
           } 
                if(flag==0)
                {
                dragElement.updateLocation(p);
                dragElement.updateMatrix(p, currCircuit.meshType, currCircuit.meshID, srcDragPoint);
                }
                else
                {
                    dragElement.updateLocation(srcDragPoint);
                    dragElement.updateMatrix(srcDragPoint, currCircuit.meshType, currCircuit.meshID, srcDragPoint);
                }
            
            }
            else
            {
           // System.out.println(dragloc);
                dragElement.updateLocation(dragloc);
                dragElement.updateMatrix(dragloc, currCircuit.meshType, currCircuit.meshID, srcDragPoint);
            
            
            }
            dragElement = null;
            dragloc=null;
            draggingElement = false;
            dragAction = false;
        }
        temp1 = null;
        srcDragPoint = null;
        activeButton = buttons.NONE;
        repaint();
    }//GEN-LAST:event_midPanelMouseReleased

    /*
     * This function handles the mouse-drag event corresponding to the midPanel (i.e. where the circuit is being drawn)
     * The press event is responsible for :
     *          - updating the location of the element being dragged
     */
    private void midPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_midPanelMouseDragged
        // TODO add your handling code here:
        
        Point p = downScale(evt.getPoint());
        /*if (activeButton == buttons.WIRE) {
            logField.setText("Connecting elements");
            temp2 = evt.getPoint();
        } else if (activeButton == buttons.DISCONNECT) {
            logField.setText("Disconnecting elements");
            temp2 = evt.getPoint();
        }*/
        if (draggingElement) {
            dragAction = true;
            //dragElement.updateMatrix(p, currCircuit.meshType, currCircuit.meshID, dragElement.getLocation());

            if((p.x>(1+dragElement.maxIO) && p.x<(117-dragElement.maxIO)) && (p.y>=(dragElement.maxIO) && p.y<(61-dragElement.maxIO)))
            {
               
                
            dragElement.updateLocation(p);
            dragloc=p;
                
            }
            }
        repaint();
    }//GEN-LAST:event_midPanelMouseDragged

    /*
     * the function that is called when Simulate button is pressed.
     * if there are no time-pulses, and all the values corresponding to the open input nodes are set,
     *      then the function calles the processCircuit function and puts the resultant output values at the Ouput nodes
     * if there are time-pulses, then the procesCircuit is called iteratively, for every change of state in the time-pulse.
     *      for each time value when the state changes,
     *          the values at the input nodes are set corresponding to the pulse,
     *          and then the process circuit is called.
     *          The corresponding output values are also stored for each state-change in timePulseOutputs.
     */
    private void simulateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulateButtonActionPerformed
        // TODO add your handling code here:
//System.out.println("new simulation");
        timePulseOutputs = new HashMap<Output, Vector<Integer>>();
        timePulse_InputProbes = new HashMap<Input, Vector<Integer>>();
        timePulse_OutputProbes = new HashMap<Output, Vector<Integer>>();
        timePulseChanges = new HashSet<Integer>();

        simulated = true;
        logField.setText("Simulating " + currCircuit.getCircuitName());

        // if time-pulses exist, then do iterative processing of the circuit, by resetting the values at the nodes at each state-change time
        if (!activeTimePulses.isEmpty()) {
            for (Iterator it = activeTimePulses.keySet().iterator(); it.hasNext();) {
                Input inp = (Input) it.next();
                timePulseChanges.addAll(timePulses_EXT.get(inp.getTimePulseLabel()));
            }
            timePulseChanges_sorted = new ArrayList(timePulseChanges);
            timePulseChanges_sorted.add(0);
            Collections.sort(timePulseChanges_sorted);

            // for every time value at which the state-changes
            for (Iterator it1 = timePulseChanges_sorted.iterator(); it1.hasNext();) {
                int t = (Integer) it1.next();
                // locate this time value in the time-pulse, to see if it corresponds to uptime or downtime
                for (Iterator it2 = activeTimePulses.keySet().iterator(); it2.hasNext();) {
                    Input inp = (Input) it2.next();
                    Vector<Integer> currPulse = timePulses_EXT.get(inp.getTimePulseLabel());
                    boolean found = false;
                    for (int i = 0; i < currPulse.size(); i++) {
                        if (currPulse.elementAt(i) == t) {
                            found = true;
                            if (i % 2 == 0) {           // even position => downtime => 0
                                inp.setDataValue(0);
                                inp.setNewValue(0);
                            } else {                    // odd position => uptime => 1
                                inp.setDataValue(1);
                                inp.setNewValue(1);
                            }
                            break;
                        } else if (t < currPulse.elementAt(i)) {
                            // t lies in between the time values at i-1 and i => t belongs to i-1
                            found = true;
                            if (i != 0) {
                                if ((i - 1) % 2 == 0) {           // even position => downtime => 0
                                    inp.setDataValue(0);
                                    inp.setNewValue(0);
                                } else {                    // odd position => uptime => 1
                                    inp.setDataValue(1);
                                    inp.setNewValue(1);
                                }
                            } else {
                                inp.setDataValue(1);
                                inp.setNewValue(1);
                            }
                            break;
                        }
                    }
                    if (!found) {
                        if (currPulse.size() % 2 == 0) /* even number of time values => pulse ended with a downtime
                        => for any time value greater than the last one, it was up
                         */ {
                            inp.setDataValue(1);
                            inp.setNewValue(1);
                        } else /* odd number of time values => pulse ended with a uptime
                        => for any time value greater than the last one, it was down
                         */ {
                            inp.setDataValue(0);
                            inp.setNewValue(0);
                        }
                    }
                }
                 boolean stat;
                // System.out.println("new simulation "+t);
                stat=currCircuit.processCircuit(Boolean.FALSE);
                if(!stat)
                {
                    logField.setText("Unable to process " + currCircuit.getCircuitName() );
                    repaint();
                    return;
                }
                // store the values for each state-change corresponding to the output nodes
                for (Iterator it2 = currCircuit.getCircuitOutputsList().iterator(); it2.hasNext();) {
                    Output op = (Output) it2.next();
                    if (timePulseOutputs.containsKey(op)) {
                        timePulseOutputs.get(op).add(op.getDataValue());
                    } else {
                        Vector<Integer> outputValues = new Vector<Integer>();
                        outputValues.add(op.getDataValue());
                        timePulseOutputs.put(op, outputValues);
                    }
                }
                // store the values for each state-change corresponding to the input nodes, which are being probed
                for (Iterator it2 = currCircuit.allInputsList.keySet().iterator(); it2.hasNext();) {
                    int key = (Integer) it2.next();
                    Input ip = currCircuit.allInputsList.get(key);
                    if (ip.isProbed()) {
                        if (timePulse_InputProbes.containsKey(ip)) {
                            timePulse_InputProbes.get(ip).add(ip.getDataValue());
                        } else {
                            Vector<Integer> outputValues = new Vector<Integer>();
                            outputValues.add(ip.getDataValue());
                            timePulse_InputProbes.put(ip, outputValues);
                        }
                    }
                }
                // store the values for each state-change corresponding to the output nodes, which are being probed
                for (Iterator it2 = currCircuit.allOutputsList.keySet().iterator(); it2.hasNext();) {
                    int key = (Integer) it2.next();
                    Output op = currCircuit.allOutputsList.get(key);
                    if (op.isProbed()) {
                        if (timePulse_OutputProbes.containsKey(op)) {
                            timePulse_OutputProbes.get(op).add(op.getDataValue());
                        } else {
                            Vector<Integer> outputValues = new Vector<Integer>();
                            outputValues.add(op.getDataValue());
                            timePulse_OutputProbes.put(op, outputValues);
                        }
                    }
                }
            }

        } else {
            boolean processed = currCircuit.processCircuit(Boolean.FALSE);
            if (processed) {
                logField.setText(currCircuit.getCircuitName() + " processed successfully!");
            } else {
                logField.setText("Unable to process " + currCircuit.getCircuitName() );
            }
        }
        repaint();
    }//GEN-LAST:event_simulateButtonActionPerformed

    /*
     * called when the user presses the OR gate button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to OR, so that the necessary happens in the mouseClick event function
     */
    /*
     * called when the user presses the NOR gate button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to NOR, so that the necessary happens in the mouseClick event function
     */
    /*
     * called when the user presses the AND gate button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to AND, so that the necessary happens in the mouseClick event function
     */
    /*
     * called when the user presses the NAND gate button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to NAND, so that the necessary happens in the mouseClick event function
     */
    /*
     * called when the user presses the XOR gate button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to XOR, so that the necessary happens in the mouseClick event function
     */
    /*
     * called when the user presses the XNOR gate button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to XNOR, so that the necessary happens in the mouseClick event function
     */
    /*
     * called when the user presses the NOT gate button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to NOT, so that the necessary happens in the mouseClick event function
     */
    /*
     * called when the user presses the CONNECT button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to WIRE, so that the necessary happens in the mouseClick event function
     */
    private void wireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wireActionPerformed
        // TODO add your handling code here:
        activeButton = buttons.WIRE;
        //midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
         midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_wireActionPerformed

    /*
     * The following mouseEntered functions are called when the user's mouse enters any of the buttons corresponding to GATES
     * It is responsible for setting the icon in the selectedGate label to the gate upon which the mouse lies now.
     */
    /*
     * The following mouseExited functions are called when the user's mouse exits the button corresponding to GATES
     * It is responsible for re-setting the icon in the selectedGate label to 'null'
     */
    /*
     * This function clears the current circuit, and re-initializes everything to a fresh new state
     */
    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        // TODO add your handling code here:
        currCircuit = new Circuit();
        initializeData();
        repaint();
    }//GEN-LAST:event_clearButtonActionPerformed

    /*
     * called when the user presses the 0 input button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to BINARY_ZERO, so that the necessary happens in the mouseClick event function
     */
    private void zeroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zeroButtonActionPerformed
        // TODO add your handling code here:
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        activeButton = buttons.BINARY_ZERO;
    }//GEN-LAST:event_zeroButtonActionPerformed

    /*
     * called when the user presses the 1 input button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to BINARY_ONE, so that the necessary happens in the mouseClick event function
     */
    private void oneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oneButtonActionPerformed
        // TODO add your handling code here:
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        activeButton = buttons.BINARY_ONE;
    }//GEN-LAST:event_oneButtonActionPerformed

    /*
     * called when the user presses the DELETE button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to DELETE, so that the necessary happens in the mouseClick event function
     */
    private void deleteElementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteElementButtonActionPerformed
        // TODO add your handling code here:
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        activeButton = buttons.DELETE;
    }//GEN-LAST:event_deleteElementButtonActionPerformed

    /*
     * called when the user presses the PROBE button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to PROBE, so that the necessary happens in the mouseClick event function
     */
    private void probeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_probeButtonActionPerformed
        // TODO add your handling code here:
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        activeButton = buttons.PROBE;
    }//GEN-LAST:event_probeButtonActionPerformed

    /*
     * called when the user presses the DISCONNECT button, and then clicks on the midPanel.
     * It is responsible for setting the activeButton to DISCONNECT, so that the necessary happens in the mouseClick event function
     */
    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed
        // TODO add your handling code here:
        //midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
         midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        activeButton = buttons.DISCONNECT;
    }//GEN-LAST:event_disconnectButtonActionPerformed

    /*
     * called when the user presses the Save button
     * It is responsible for saving the currentCircuit to a file, so as to load it later.
     * Invokes a file-chooser dialog, to choose the file where the circuit is to be saved, and saves the circuit to it.
     */
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File pwd = new File(".");
        try {
            pwd = new File(new File(".").getCanonicalPath());
        } catch (IOException ex) {
            Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        fc.setCurrentDirectory(pwd);
       
       int returnVal = fc.showSaveDialog(mainPanel.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            logField.setText("Saving circuit to: " + file.getName());
            try {
                // write or call the save function here
                String retStatus = currCircuit.saveCircuit(file.getPath(), Boolean.FALSE);
                logField.setText(retStatus);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            logField.setText("Save command cancelled by user");
        }

    }//GEN-LAST:event_saveButtonActionPerformed

    /*
     * called when the user presses the Load button
     * It is responsible for loading the circuit from a file.
     * Invokes a file-chooser dialog, to choose the file from which the circuit is to be loaded, and creates the corresponding circuit.
     */
    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File pwd = new File(".");
        try {
            pwd = new File(new File(".").getCanonicalPath());
        } catch (IOException ex) {
            Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        fc.setCurrentDirectory(pwd);
        int returnVal = fc.showOpenDialog(mainPanel.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            logField.setText("Opening circuit from file: " + file.getName());
            // write or call the load function here
            Circuit newCkt = new Circuit();
            try {
               BufferedReader bufReader = new BufferedReader(new FileReader(file.getPath()));
               // BufferedReader bufReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dldvirtuallabs/images/3nand1")));
                String retStatus = newCkt.loadCircuit(bufReader);
                bufReader.close();
                currCircuit = newCkt;
                logField.setText(file.getPath() + retStatus);
                int maxele=-1;
                int maxin=-1;
                int maxout=-1;
                for (Iterator it = currCircuit.allElementsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            int temp = (Integer)key;
            if(temp>maxele)
            {
                maxele=temp;
            }
                }
                for (Iterator it = currCircuit.allInputsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            int temp = (Integer)key;
            if(temp>maxin)
            {
                maxin=temp;
            }
                }
                for (Iterator it = currCircuit.allOutputsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            int temp = (Integer)key;
            if(temp>maxout)
            {
                maxout=temp;
            }
                }
                elementCount = maxele+1;
                inputCount = maxin+1;
                outputCount = maxout+1;
                repaint();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            logField.setText("Open command cancelled by user");
        }
    }//GEN-LAST:event_loadButtonActionPerformed

    private void genericElementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_genericElementMouseEntered
        // TODO add your handling code here:
        if (activeButton == buttons.NONE) {
            selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/genericElement.gif")));
        }
    }//GEN-LAST:event_genericElementMouseEntered

    private void genericElementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_genericElementMouseExited
        // TODO add your handling code here:
        if (activeButton == buttons.NONE) {
            selectedGate.setIcon(null);
        }
    }//GEN-LAST:event_genericElementMouseExited

    /*
     * called when the user presses the Import button
     * It is responsible for loading the circuit saved to a file as a generic-element.
     * Invokes a file-chooser dialog, to choose the file from which the element has to be loaded.
     */
    private void genericElementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genericElementActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File pwd = new File(".");
        try {
            pwd = new File(new File(".").getCanonicalPath());
        } catch (IOException ex) {
            Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        fc.setCurrentDirectory(pwd);
        int returnVal = fc.showOpenDialog(mainPanel.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            logField.setText("Importing circuit from file: " + file.getName());
            gen_name=file.getName();
            // write or call the load function here
            genericElementCkt = new Circuit();
            try {
                BufferedReader bufReader = new BufferedReader(new FileReader(file.getPath()));
                String retStatus = genericElementCkt.loadCircuit(bufReader);
                bufReader.close();
                genericElementCkt.identifyCktInputsOutputs();
                logField.setText(retStatus);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            activeButton = buttons.GENERIC_ELEMENT;
            selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/genericElement.gif")));
            midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            repaint();
        } else {
            logField.setText("Import command cancelled by user");
        }
    }//GEN-LAST:event_genericElementActionPerformed

    /*
     * called when a new time-pulse is chosen from the list of available time-pulses
     * It is responsible for setting the time-pulse button to the corresponding chosen label,
     * and for drawing the chosen time-pulse in the rightPanel.
     */
    private void timePulseListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_timePulseListValueChanged
        // TODO add your handling code here:
        simulated = false;
        String val = (String) timePulseList.getSelectedValue();
        if (val == null) {
            return;
        }
        currentTimePulse.setText(val);
        String str = val + " : ";
        Vector<Integer> tempTimes = timePulses.get(val);
        boolean first = true;
        for (Iterator it = tempTimes.iterator(); it.hasNext();) {
            if (first) {
                str = str + " " + it.next();
                first = false;
            } else {
                str = str + " , " + it.next();
            }
        }
        pulseParams.setText(str);
        repaint();
    }//GEN-LAST:event_timePulseListValueChanged

    /*
     * called when the user presses the New Time Pulse button
     * It is responsible for parsing the parameters entered in the textField and create a new time-pulse.
     */
    private void timePulseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timePulseActionPerformed
        // TODO add your handling code here:
        String params = pulseParams.getText();
        
            params.replace('\n', ' ');
           params= params.trim();
           
          //  System.out.println(params+"pqr");
        
        if (params.equals("")) {
            logField.setText("Couldn't parse the parameters");
            return;
        } else {
            String[] parsed = params.split(":");
            if (parsed.length != 2 || parsed[0].equals("") || parsed[1].equals("")) {
                logField.setText("Couldn't parse the parameters");
                return;
            } else {
                String label = parsed[0].replace(" ", "");
                String[] times = parsed[1].split(",");
                if (times.length < 2) {
                    logField.setText("Couldn't parse the parameters");
                    return;
                } else {
                    Vector<Integer> timeList = new Vector<Integer>();
                    Vector<Integer> timeList_EXT = new Vector<Integer>();
                    int currentTime = 0;
                    for (int i = 0; i < times.length; i++) {
                        times[i] = times[i].replace(" ", "");
                        if (times[i].equals("")) {
                            logField.setText("Couldn't parse the parameters");
                            return;
                        }
                        int t = Integer.parseInt(times[i]);
                        timeList.add(t);
                        currentTime = currentTime + t;
                        timeList_EXT.add(currentTime);
                    }
                    if (!timePulses.containsKey(label)) {
                        ListModel prev = timePulseList.getModel();
                        DefaultListModel vals = new DefaultListModel();
                        for (int i = 0; i < prev.getSize(); i++) {
                            vals.addElement(prev.getElementAt(i));
                        }
                        vals.addElement(label);
                        timePulseList.setModel(vals);
                    }
                    timePulses.put(label, timeList);
                    timePulses_EXT.put(label, timeList_EXT);
                    logField.setText("Loaded time pulse : " + label + " " + timeList);
                }
            }
        }
        repaint();
    }//GEN-LAST:event_timePulseActionPerformed

    /*
     * for setting the default text of the textField where the parameters of a time-pulse are to be entered,
     * and also resetting it to null, when user clicks for the first time
     */
    private void pulseParamsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pulseParamsMouseClicked
        // TODO add your handling code here:
        if (pulseParams.getText().equals("Enter the pulse paramaeters here")) {
            pulseParams.setText(null);
        }
    }//GEN-LAST:event_pulseParamsMouseClicked

    /*
     * called when the user presses the Time Pulse button
     * It is responsible for setting the activeButton to TIME_PULSE, so that the necessary happens in the mouseClick event function
     */
    private void currentTimePulseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentTimePulseActionPerformed
        // TODO add your handling code here:
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        activeButton = buttons.TIME_PULSE;
    }//GEN-LAST:event_currentTimePulseActionPerformed

    /*
     * called when the user's mouse enters the midPanel
     * the logField's text is set to the element's name upon which the mouse lies right now
     */
    private void midPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_midPanelMouseEntered
        // TODO add your handling code here:
        Point p = downScale(clip(evt.getPoint()));
        if (currCircuit.meshType[p.y][p.x] == 3) {
            logField.setText(currCircuit.allElementsList.get(currCircuit.meshID[p.y][p.x]).getElementName());
        } else {
            logField.setText("");
        }
    }//GEN-LAST:event_midPanelMouseEntered

    /*
     * called when the user's mouse moves over the midPanel
     * the logField's text is set to the element's name upon which the mouse lies right now
     */
    private void midPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_midPanelMouseMoved
        // TODO add your handling code here:
        Point p = downScale(clip(evt.getPoint()));
        if (currCircuit.meshType[p.y][p.x] == 3) {
            logField.setText(currCircuit.allElementsList.get(currCircuit.meshID[p.y][p.x]).getElementName()+" Gate-Delay:"+currCircuit.allElementsList.get(currCircuit.meshID[p.y][p.x]).getgatedelay());
        } else {
            logField.setText("");
        }
    }//GEN-LAST:event_midPanelMouseMoved

    private void outputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputActionPerformed
        // TODO add your handling code here:
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        activeButton = buttons.output;
    }//GEN-LAST:event_outputActionPerformed

    private void NamingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NamingActionPerformed
        // TODO add your handling code here:
         midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        activeButton = buttons.Naming;
    }//GEN-LAST:event_NamingActionPerformed

    private void logFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logFieldActionPerformed

    private void pulseParamsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pulseParamsKeyPressed

        if(evt.getKeyCode()==10)
        {
          //  pulseParams.setText(pulseParams.getText().substring(0, pulseParams.getText().length()-1));
            //System.out.println(pulseParams.getText()+"abc");

            timePulseActionPerformed(null);
            
        }
    }//GEN-LAST:event_pulseParamsKeyPressed

    private void pulseParamsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pulseParamsKeyReleased
        if(evt.getKeyCode()==10)
        {
            pulseParams.setText(pulseParams.getText().substring(0, pulseParams.getText().length()-1));
           // System.out.println(pulseParams.getText()+"abc");

            //timePulseActionPerformed(null);
        }
    }//GEN-LAST:event_pulseParamsKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        activeButton = buttons.NONE;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

            // write or call the load function here
            Circuit newCkt = new Circuit();
            try {
               //BufferedReader bufReader = new BufferedReader(new FileReader(file.getPath()));

                String file_path = "/dldvirtuallabs/circuits/"+load_combo.getSelectedItem();
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(file_path)));
                String retStatus = newCkt.loadCircuit(bufReader);
                bufReader.close();
                currCircuit = newCkt;
               // logField.setText(file.getPath() + retStatus);
                int maxele=-1;
                int maxin=-1;
                int maxout=-1;
                for (Iterator it = currCircuit.allElementsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            int temp = (Integer)key;
            if(temp>maxele)
            {
                maxele=temp;
            }
                }
                for (Iterator it = currCircuit.allInputsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            int temp = (Integer)key;
            if(temp>maxin)
            {
                maxin=temp;
            }
                }
                for (Iterator it = currCircuit.allOutputsList.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            int temp = (Integer)key;
            if(temp>maxout)
            {
                maxout=temp;
            }
                }
                elementCount = maxele+1;
                inputCount = maxin+1;
                outputCount = maxout+1;
                repaint();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        genericElementCkt = new Circuit();
            try {
                gen_name=load_combo.getSelectedItem().toString();
                String file_path = "/dldvirtuallabs/circuits/"+load_combo.getSelectedItem();
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(file_path)));
                String retStatus = genericElementCkt.loadCircuit(bufReader);
                bufReader.close();
                genericElementCkt.identifyCktInputsOutputs();
                logField.setText(retStatus);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            activeButton = buttons.GENERIC_ELEMENT;
            selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/genericElement.gif")));
            midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            repaint();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void select_gateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_select_gateActionPerformed
        // TODO add your handling code here:
        if(jComboBox1.getSelectedItem().equals("AND") && jComboBox2.getSelectedItem().equals("2") )
        {
        activeButton = buttons.AND;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/and.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        else if(jComboBox1.getSelectedItem().equals("AND") && jComboBox2.getSelectedItem().equals("3") )
        {
        activeButton = buttons.AND3;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/and.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("AND") && jComboBox2.getSelectedItem().equals("4") )
        {
        activeButton = buttons.AND4;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/and.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("OR") && jComboBox2.getSelectedItem().equals("2") )
        {
        activeButton = buttons.OR;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/or.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("OR") && jComboBox2.getSelectedItem().equals("3") )
        {
        activeButton = buttons.OR3;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/or.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("OR") && jComboBox2.getSelectedItem().equals("4") )
        {
        activeButton = buttons.OR4;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/or.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("NAND") && jComboBox2.getSelectedItem().equals("2") )
        {
        activeButton = buttons.NAND;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/nand.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("NAND") && jComboBox2.getSelectedItem().equals("3") )
        {
        activeButton = buttons.NAND3;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/nand.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("NAND") && jComboBox2.getSelectedItem().equals("4") )
        {
        activeButton = buttons.NAND4;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/nand.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("NOR") && jComboBox2.getSelectedItem().equals("2") )
        {
        activeButton = buttons.NOR;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/nor.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("NOR") && jComboBox2.getSelectedItem().equals("3") )
        {
        activeButton = buttons.NOR3;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/nor.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("NOR") && jComboBox2.getSelectedItem().equals("4") )
        {
        activeButton = buttons.NOR4;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/nor.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("XOR") && jComboBox2.getSelectedItem().equals("2") )
        {
        activeButton = buttons.XOR;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/xor.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("XOR") && jComboBox2.getSelectedItem().equals("3") )
        {
        activeButton = buttons.XOR3;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/xor.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("XOR") && jComboBox2.getSelectedItem().equals("4") )
        {
        activeButton = buttons.XOR4;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/xor.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("XNOR") && jComboBox2.getSelectedItem().equals("2") )
        {
        activeButton = buttons.XNOR;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/xnor.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("XNOR") && jComboBox2.getSelectedItem().equals("3") )
        {
        activeButton = buttons.XNOR3;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/xnor.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("XNOR") && jComboBox2.getSelectedItem().equals("4") )
        {
        activeButton = buttons.XNOR4;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/xnor.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("NOT") && jComboBox2.getSelectedItem().equals("1") )
        {
        activeButton = buttons.NOT;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/not.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
         else if(jComboBox1.getSelectedItem().equals("CONN") && jComboBox2.getSelectedItem().equals("1") )
        {
        activeButton = buttons.CONNECTOR;
        selectedGate.setIcon(new ImageIcon(getClass().getResource("/dldvirtuallabs/images/not.gif")));
        midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }//GEN-LAST:event_select_gateActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if(jComboBox1.getSelectedItem().equals("NOT") || jComboBox1.getSelectedItem().equals("CONN"))
        {
            jComboBox2.removeAllItems();
            jComboBox2.addItem("1");
        }
        else
        {
            jComboBox2.removeAllItems();
            jComboBox2.addItem("2");
             jComboBox2.addItem("3");
              jComboBox2.addItem("4");

        }
         midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        activeButton = buttons.NONE;
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
         midPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        activeButton = buttons.NONE;
    }//GEN-LAST:event_jComboBox2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Naming;
    private javax.swing.JButton clearButton;
    private javax.swing.JTextField content1;
    private javax.swing.JButton currentTimePulse;
    private javax.swing.JButton deleteElementButton;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JButton genericElement;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton loadButton;
    private javax.swing.JComboBox load_combo;
    private javax.swing.JTextField logField;
    private javax.swing.JPanel midPanel;
    private javax.swing.JButton oneButton;
    private javax.swing.JButton output;
    private javax.swing.JButton probeButton;
    private javax.swing.JTextPane pulseParams;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JScrollPane rightPanel1;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton select_gate;
    private javax.swing.JLabel selectedGate;
    private javax.swing.JButton simulateButton;
    private javax.swing.JButton timePulse;
    private javax.swing.JList timePulseList;
    private javax.swing.JPanel topPanel;
    private javax.swing.JButton wire;
    private javax.swing.JButton zeroButton;
    // End of variables declaration//GEN-END:variables

    /*
     * midPanel is an instance of the following class - WorkPanel
     * The paint() function is overriden, so as to handle the circuit drawing functionality
     */
    public class WorkPanel extends JPanel {

        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            // draw the grid
            setBackground(Color.WHITE);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3));
            for (int i = 10; i < workPanelSize.width; i += 20) {
                for (int j = 10; j < workPanelSize.height; j += 20) {
                    g2d.drawOval(i - 1, j - 1, 2, 2);
                }
            }

            //draw temporary connnections - i.e. for connect and disconnect
         /*   if (temp1 != null) {
                float dash[] = {10.0f};
                g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                        10.0f, dash, 0.0f));
                if (activeButton == buttons.WIRE) {
                    g2d.setColor(Color.GREEN);
                } else if (activeButton == buttons.DISCONNECT) {
                    g2d.setColor(Color.RED);
                }
                Line2D line = new Line2D.Double(temp1, temp2);
                g2d.draw(line);
            }*/

            //draw the circuit
            currCircuit.draw(g2d);
        }
    }

    /*
     * this function draws the time-pulse graph, corresponding to a series of uptime and downtime
     */
    public void drawGraph(Graphics2D g2d, Vector<Integer> tempTimes, Point start, Dimension box, String label, Color c) {
        int totalTime = 0;
        g2d.setColor(c);
        g2d.fillRect(start.x, start.y, 1590, box.height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(start.x, start.y, 1590, box.height);

        Font font2 = new Font("Times New Roman", Font.BOLD, 16);
        g2d.setFont(font2);
        g2d.drawString(label, 20, start.y + 15);

        Point prev = new Point(start.x + 10, start.y + 20);
        Point prePrev = null;
        int count = 0;
        int currTime = 0;

        Font font3 = new Font("Times New Roman", Font.PLAIN, 9);
        g2d.setFont(font3);
        g2d.setColor(Color.BLACK);
        g2d.drawString(Integer.toString(currTime), prev.x - 5, prev.y + 40);

        g2d.setColor(Color.RED);
        for (Iterator it = tempTimes.iterator(); it.hasNext();) {
            int t = (Integer) it.next();
            int w = (t * (box.width - 15)) / 100;
            if (prePrev != null && prePrev.x != prev.x) {
                g2d.drawLine(prev.x, prev.y, prev.x, prev.y + 30);
                g2d.setFont(font3);
                g2d.setColor(Color.BLACK);
                g2d.drawString(Integer.toString(currTime), prev.x - 5, prev.y + 40);
            }
            g2d.setColor(Color.RED);
            if (count % 2 == 0) {
                g2d.drawLine(prev.x, prev.y, prev.x + w, prev.y);
            } else {
                g2d.drawLine(prev.x, prev.y + 30, prev.x + w, prev.y + 30);
            }
            prePrev = prev;
            prev = new Point(prev.x + w, prev.y);
            count = count + 1;
            currTime = currTime + t;
        }

        g2d.setFont(font3);
        g2d.setColor(Color.BLACK);
        g2d.drawString(Integer.toString(currTime), prev.x - 5, prev.y + 40);
    }

    /*
     * rightPanel is an instance of the following class - OutputPanel
     * The paint() function is overriden, so as to handle the time-pulse's drawing functionality
     */
    //public Dimension dim=new Dimension(160,719);
    public class OutputPanel extends JPanel {

        @Override
            public Dimension getPreferredSize()
    {
          //return this.getSize();
      //      return dim;
            return new Dimension(1600, 1200);
    }

        @Override
        public void paint(Graphics g) {
        //    System.out.println(dim);
            drawn = new HashSet<String>();
            Graphics2D g2d = (Graphics2D) g;
            Dimension rpanelSize = rightPanel1.getSize();
            String val = (String) timePulseList.getSelectedValue();
             int count = 0;
            if (simulated) {
               count=0;
                // draw all the active time pulses
                for (Iterator it = activeTimePulses.keySet().iterator(); it.hasNext();) {
                    val = ((Input) it.next()).getTimePulseLabel();
                    if (!drawn.contains(val)) {
                        drawn.add(val);
                        Vector<Integer> tempTimes = timePulses.get(val);
                        Point start = new Point(0, count * 70);
                        Dimension box = new Dimension(rpanelSize.width - 10, 65);
                        drawGraph(g2d, timePulses.get(val), start, box, val, Color.GREEN);
                        count = count + 1;
                    }
                }
                // draw all the active output pulses
                for (Iterator it = timePulseOutputs.keySet().iterator(); it.hasNext();) {
                    Output op = (Output) it.next();
                    Point start = new Point(0, count * 70);
                    Dimension box = new Dimension(rpanelSize.width - 10, 65);
                    String label = op.getAncestor().getElementName()+"-"+op.getOutputIndex();
                    Vector<Integer> tempTimes = new Vector<Integer>();
                    int init_time = 0, elapsed_time = 0;
                    int key = 1;
                    Vector<Integer> tempOutputs = new Vector<Integer>(timePulseOutputs.get(op));
                    for (int i = 0; i < tempOutputs.size(); i++) {
                        int dataVal = tempOutputs.elementAt(i);
                        elapsed_time = (Integer) timePulseChanges_sorted.get(i);
                        if (dataVal != key) {
                            tempTimes.add(elapsed_time - init_time);
                            key = dataVal;
                            init_time = elapsed_time;
                        }
                    }
                    if (init_time != elapsed_time) {
                        tempTimes.add(elapsed_time - init_time);
                    }
                    drawGraph(g2d, tempTimes, start, box, label, Color.YELLOW);
                    count = count + 1;
                }
                // draw all the pulses at input probe points
                for (Iterator it = timePulse_InputProbes.keySet().iterator(); it.hasNext();) {
                    Input ip = (Input) it.next();
                    Point start = new Point(0, count * 70);
                    Dimension box = new Dimension(rpanelSize.width - 10, 65);
                    String label = ip.getAncestor().getElementName() + "-" + ip.getInputIndex();
                    Vector<Integer> tempTimes = new Vector<Integer>();
                    int init_time = 0, elapsed_time = 0;
                    int key = 1;
                    Vector<Integer> tempOutputs = new Vector<Integer>(timePulse_InputProbes.get(ip));
                    for (int i = 0; i < tempOutputs.size(); i++) {
                        int dataVal = tempOutputs.elementAt(i);
                        elapsed_time = (Integer) timePulseChanges_sorted.get(i);
                        if (dataVal != key) {
                            tempTimes.add(elapsed_time - init_time);
                            key = dataVal;
                            init_time = elapsed_time;
                        }
                    }
                    if (init_time != elapsed_time) {
                        tempTimes.add(elapsed_time - init_time);
                    }
                    drawGraph(g2d, tempTimes, start, box, label, Color.ORANGE);
                    count = count + 1;
                }
                // draw all the pulses at output probe points
                for (Iterator it = timePulse_OutputProbes.keySet().iterator(); it.hasNext();) {
                    Output op = (Output) it.next();
                    Point start = new Point(0, count * 70);
                    Dimension box = new Dimension(rpanelSize.width - 10, 65);
                    String label = op.getAncestor().getElementName()+"-"+op.getOutputIndex();
                    Vector<Integer> tempTimes = new Vector<Integer>();
                    int init_time = 0, elapsed_time = 0;
                    int key = 1;
                    Vector<Integer> tempOutputs = new Vector<Integer>(timePulse_OutputProbes.get(op));
                    for (int i = 0; i < tempOutputs.size(); i++) {
                        int dataVal = tempOutputs.elementAt(i);
                        elapsed_time = (Integer) timePulseChanges_sorted.get(i);
                        if (dataVal != key) {
                            tempTimes.add(elapsed_time - init_time);
                            key = dataVal;
                            init_time = elapsed_time;
                        }
                    }
                    if (init_time != elapsed_time) {
                        tempTimes.add(elapsed_time - init_time);
                    }
                    drawGraph(g2d, tempTimes, start, box, label, Color.ORANGE);
                    count = count + 1;
                     
                }
            //dim=new Dimension(((Integer)timePulseChanges_sorted.get(timePulseChanges_sorted.size()-1)*16)/10,(count) * 70);
            } else if (val != null) {   // only draw the selected time-pulse
             //   dim=new Dimension((timePulses_EXT.get(val).lastElement()*16)/10,(count+1) * 70);
                Point start = new Point(0, 0);
                Dimension box = new Dimension(rpanelSize.width - 10, 65);
                drawGraph(g2d, timePulses.get(val), start, box, val, Color.GREEN);

            }
           //rightPanel1.repaint();
        }
    }
}

