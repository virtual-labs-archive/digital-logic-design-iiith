/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dldvirtuallabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JApplet;
import javax.swing.UIManager;

/**
 *
 * @author rajesh, buddy
 * This is a basic JApplet class, that creates a java based browser applet.
 * It contains a mainPanel as its content,
 * and is basically responsible for the instantiation of the GUI.
 */
public class simulationApplet extends JApplet {

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {
        // TODO start asynchronous download of heavy resources

        // setting the browser applet size to 1024x650
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width= screenSize.width -5;
        int height= screenSize.height -150;
        if(height < 660)
        {
            height=660;
        }
        if(width<1020)
        {
            width=1020;
        }
        this.setSize(width, height);
        this.setLayout(new BorderLayout());
        
        this.setMinimumSize(new Dimension(512, 128));
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
               String content1 =getParameter("content1");
               String param2="comparator";
               // String param2=getParameter("file_list");
                String file_list[]=param2.split(",");
                System.out.println(getParameter("content1"));
                createAndShowGUI(content1,file_list);
            }
        });
    }

    private void createAndShowGUI(String content,String file_list[] ){
        // creating an instance of the mainPanel JPanel
        
        mainPanel workPane = new mainPanel(content,file_list);
        workPane.setOpaque(true);
        this.setContentPane(workPane);
    }
 
}