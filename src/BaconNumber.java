import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    private JTextField userInput1 = new JTextField("",20);
    private JTextField userInput2 = new JTextField("", 20);
    private boolean running;
    
    public void run() {
    	//PlayerParser parser = new PlayerParser();
    	//ArrayList<Player> playerList = parser.playerList();
    	//Parser instance 
    	//access the master list 
    	
    	AdjacencyList aList = new AdjacencyList(new ArrayList<Player>());
        
        running = true;
        
        statusBar = new JLabel("Brady Number: "); 
        
        //Frame Label
        final JFrame frame = new JFrame("Bacon Number");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 3, screenSize.height / 3);
        
        
        
        //Status Panel
        JPanel statusPanel = new JPanel();
        frame.add(statusPanel, BorderLayout.SOUTH);
        statusBar = new JLabel("Bacon Number: ");
        statusPanel.add(statusBar);
        

        JPanel controlPanel1 = new JPanel();
        frame.add(controlPanel1, BorderLayout.NORTH);
        
        JPanel controlPanel2 = new JPanel();
        frame.add(controlPanel2, BorderLayout.CENTER);
        
        JPanel controlPanel3 = new JPanel();
        frame.add(controlPanel3, BorderLayout.SOUTH);
        
        //Reset Button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	userInput1.setText("");
            	userInput2.setText("");
                
            }
        });
        
        //Start Button
        final JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e2) {
            }
        });
        
        //Menu Button
        final JButton menu = new JButton("Directions");
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e3) {
                JOptionPane.showMessageDialog(null,
                        "Enter 2 current NFL players in the spaces provided.\n"
                        + "Please enter their full first and last name (no middle"
                        + " name or suffixes) with title-case capitalization \n"
                        + " as well as their 2-letter position abbreviation \n(ex. Tom"
                        + " Brady QB)",
                        "Instructions", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        //Enter Button
        final JButton enter = new JButton("Enter");
        BufferedWriter bw = null;
        FileWriter f = null;
        
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e4) {
            	//Check to make sure strings are long enough
            	String entry1 = userInput1.getText();
            	String name1 = entry1.substring(0, entry1.length()-2);
            	String position1 = entry1.substring(entry1.length()-2, entry1.length());
            	System.out.println(name1);
            	System.out.println(position1);
            	String entry2 = userInput2.getText();
            	String name2 = entry2.substring(0, entry2.length()-2);
            	String position2 = entry2.substring(entry2.length()-2, entry2.length());
            	System.out.println(name2);
            	System.out.println(position2);
            	/*Player player1 = new Player();
            	 * Player player2 = new Player();
            	 * boolean assigned1 = false;
            	 * boolean assigned2 = false;
            	 * for (Player p : playerList){
            	 *   if (p.name.equals(name1) && p.position.equals(position1) && !assigned1){
            	 *   player1 = p;
            	 *   assigned1 = true;
            	 *   continue;
            	 *   }     
            	 *   if (p.name.equals(name2) && p.position.equals(position2) && !assigned2){
            	 *   player2 = p;
            	 *   assigned2 = true
            	 *   }       	 
            	 * }
            	 * 
            	 * BFS obj = new BFS(player1, player2 aList)
            	 * 
            	 * obj.getPath(); to give the path of players 
            	 * 
            	 *make a dialog box to show the path 
            	 * 
            	 */
            	
            	JOptionPane.showMessageDialog(null,
                        "Enter 2 current NFL players in the spaces provided.\n"
                        + "Please enter their full first and last name (no middle"
                        + " name or suffixes) with title-case capitalization \n"
                        + " as well as their 2-letter position abbreviation \n(ex. Tom"
                        + " Brady QB)",
                        "Instructions", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        //Add buttons to the panels  
        controlPanel1.add(userInput1);

        controlPanel2.add(userInput2);
        
        controlPanel3.add(enter); 
        controlPanel3.add(reset);
        controlPanel3.add(menu);
        
        //Make visible on frame
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
       // frame.setSize(500,100);
        
        
    }
    
    public JLabel getStatusBar() {
        return statusBar;
    }
    
    public static void main(String[] args) {
        BaconNumber b = new BaconNumber();
        //b.setLocationRelativeTo(null);
        b.setVisible(true);
        SwingUtilities.invokeLater(new BaconNumber());
    }
}

 
