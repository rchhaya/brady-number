import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

@SuppressWarnings({ "serial", "unused" })
public class BaconNumber extends JFrame implements Runnable {
    
    private JLabel statusBar = new JLabel();
    static String username = "";
    private JTextField userInput = new JTextField("",20);
    private boolean running;
    
    public void run() {
        
        running = true;
        
        statusBar = new JLabel("Bacon Number: "); 
        
        //Frame Label
        final JFrame frame = new JFrame("Bacon Number");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 3, screenSize.height / 3);
        
        //Status Panel
        JPanel statusPanel = new JPanel();
        frame.add(statusPanel, BorderLayout.SOUTH);
        statusBar = new JLabel("Bacon Number: ");
        statusPanel.add(statusBar);
        

        JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.NORTH);
        
        //Reset Button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        //Start Button
        final JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e2) {
            }
        });
        
        //Menu Button
        final JButton menu = new JButton("Menu");
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e3) {
                JOptionPane.showMessageDialog(null,
                        "Instructions Here",
                        "Instructions", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        //Enter Button
        final JButton enter = new JButton("Enter");
        BufferedWriter bw = null;
        FileWriter f = null;
        
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e4) {
    
            }
        });
        
        //Add buttons to the panels  
        controlPanel.add(userInput);
        controlPanel.add(enter); 
        controlPanel.add(reset);
        controlPanel.add(menu);
        
        //Make visible on frame
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(500,100);
        
        
    }
    
    public JLabel getStatusBar() {
        return statusBar;
    }
    
    public static void main(String[] args) {
        BaconNumber b = new BaconNumber();
        b.setLocationRelativeTo(null);
        b.setVisible(true);
        SwingUtilities.invokeLater(new BaconNumber());
    }
}

 
