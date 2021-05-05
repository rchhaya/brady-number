import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
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
    	PlayerParser parser = new PlayerParser();
    	ArrayList<Player> playerList = parser.getPlayerList();
    	AdjacencyList aList = new AdjacencyList(playerList);
        
        running = true;
        
        statusBar = new JLabel("Brady Number: "); 
        
        //Frame Label
        final JFrame frame = new JFrame("Brady Number");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 3, screenSize.height / 3);
        
        JLabel intro1 = new JLabel("Welcome to Brady number!");
        JLabel intro2 = new JLabel("Click the instructions button if you are unfamiliar with what the input should be");
        JLabel intro3 = new JLabel("If you are unfamilar with the names and positions of players, check \n"
        		+  "out the following link:\n");
        JLabel introLink = new JLabel("Pro Football Focus");
        JLabel introWhitespace = new JLabel(" ");
        JPanel introPanel = new JPanel();
        introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));
      
        
        introLink.setForeground(Color.blue.darker());
        introLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        introLink.addMouseListener(new MouseAdapter() {
        	@Override 
        	public void mouseClicked(MouseEvent e) {
        		try {
        	        Desktop.getDesktop().browse(new URI("https://www.pro-football-reference.com/players/")); 
        	    } catch (IOException e1) {
        	    	JOptionPane.showMessageDialog(null,
                            "Whoops! The site is down at the moment."
                            + "\nPlease try again at a later time.",
                            "Instructions", JOptionPane.INFORMATION_MESSAGE);
        	    } catch ( URISyntaxException e2) {
        	    	JOptionPane.showMessageDialog(null,
                            "Whoops! The site is down at the moment."
                            + "\nPlease try again at a later time.",
                            "Instructions", JOptionPane.INFORMATION_MESSAGE);
        	    	
        	    }
        	}
        });
        introPanel.add(intro1);
        introPanel.add(intro2);
        introPanel.add(intro3);
        introPanel.add(introLink);
        introPanel.add(introWhitespace);
        
        
        //Status Panel
        JPanel statusPanel = new JPanel();
        frame.add(statusPanel, BorderLayout.SOUTH);
        statusBar = new JLabel("Bacon Number: ");
        statusPanel.add(statusBar);
        

        JPanel controlPanel1 = new JPanel();
        
        JPanel controlPanel2 = new JPanel();

        
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
                        + " as well as their 1-, 2-, 3-, or 4-letter position abbreviation \n(ex. Tom"
                        + " Brady (QB), Rodney Harrison (S), Josh Allen (EDGE))",
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
            	if (userInput1.getText().length() < 2 || userInput2.getText().length() < 2) {
            		JOptionPane.showMessageDialog(null,
                            "SYou entered an invalid input.\n"
                            + "Please re-enter with following the syntax rules: \n"
                            + "1. Full first + last name in titlecase, no middle name nor suffixes)\n"
                            + "2. If 2 players have the same name, 1-,2-, 3-letter position abbreviation (ex. S, WR, QB, RB) \n(ex. Tom"
                            + " Brady (QB), Ezekiel Elliot (RB))",
                            "Invalid input!", JOptionPane.INFORMATION_MESSAGE);
            	} else {
            	String entry1 = userInput1.getText();
            	String name1 = new String();
            	String position1 = new String();
            	if (entry1.contains("(")) {
            		//Contains position
            		name1 = entry1.substring(0, entry1.indexOf("(")).trim();
            		position1 = entry1.substring(entry1.indexOf("(")+1, entry1.length()-1).trim();
            	} else {
            		name1 = entry1.trim();
            		position1 = "";
            	}
                    	
            	System.out.println(name1);
            	System.out.println(position1);
            	
            	String entry2 = userInput2.getText();
            	String name2 = new String();
            	String position2 = new String();
            	if (entry2.contains("(")) {
            		//Contains position
            		name2 = entry2.substring(0, entry2.indexOf("(")).trim();
            		position2 = entry2.substring(entry2.indexOf("(") + 1, entry2.length()-1).trim();
            	} else {
            		name2 = entry2.trim();
            		position2 = "";
            	}
            	
            	System.out.println(name2);
            	System.out.println(position2);
            	
            	Player player1 = new Player("");
            	Player player2 = new Player("");
            	boolean assigned1 = false;
            	boolean assigned2 = false;
            	//Remove duplicates
            	Set<Player> playerSet = new HashSet<Player>(playerList);
            	for (Player p : playerSet){
            		System.out.println(p.getName() + ":" + p.getPosition());
            	  if (p.getName().equals(name1) && (p.getPosition().equals(position1) || position1.equals("")) && !assigned1){
            	  	player1 = p;
            	  	assigned1 = true;
            	  	System.out.println("here1");
            	   	continue;
            	  }     
            	  if (p.getName().equals(name2) && (p.getPosition().equals(position2) || position2.equals("")) && !assigned2){
            		  player2 = p;
            		  assigned2 = true;
            		  System.out.println("here2");
            		  continue;
            	  }       	 
            	 }
            	if (player1.getName().equals("") || player2.getName().equals("")) {
            		JOptionPane.showMessageDialog(null,
                            "You entered an invalid input.\n"
                            + "Please re-enter with following the syntax rules: \n"
                            + "1. Full first + last name in titlecase, no middle name nor suffixes)\n"
                            + "2. 1- or 2-letter position abbreviation (ex. S, WR, QB, RB) \n(ex. Tom"
                            + " Brady QB, Ezekiel Elliot RB)",
                            "Invalid input!", JOptionPane.INFORMATION_MESSAGE);
            		
            		
            	} else {
            		BFS searcher = new BFS(aList.getAdjList(), player1, player2);
            		ArrayList<Player> bfsOutput = searcher.bfsTraversal();
            		int bradyNumber = bfsOutput.size() - 1;
            		String distance = "";
            		for (int i = 0; i < bfsOutput.size(); i++) {
            			if (i != bfsOutput.size()-1) {
            				String adder = bfsOutput.get(i).getName() + " -> ";
            				distance += adder;
            			} else {
            				String adder = bfsOutput.get(i).getName();
            				distance += adder;
            			}
            		}
            		JOptionPane.showMessageDialog(null,
                            "The Brady number between " + player1.getName() + " and " + player2.getName() + 
                            " is " + bradyNumber + "!\n"
                            + "Here is the path between the players: \n"
                            + distance + " \nReturn back to home to find a different Brady number!",
                            "Brady Number", JOptionPane.INFORMATION_MESSAGE);
            	}
            }
            }
        });
        
        //Add buttons to the panels  
        controlPanel1.setLayout(new BoxLayout(controlPanel1, BoxLayout.Y_AXIS));
        userInput1.setText("Player 1");
        userInput2.setText("Player 2");
        controlPanel1.add(userInput1);
        controlPanel1.add(userInput2);
        frame.add(controlPanel1, BorderLayout.CENTER);
        
       // controlPanel2.setLayout(new BoxLayout(controlPanel2, BoxLayout.Y_AXIS));
        controlPanel2.add(enter); 
        controlPanel2.add(reset);
        controlPanel2.add(menu);
        frame.add(controlPanel2, BorderLayout.SOUTH);
        
        frame.add(introPanel, BorderLayout.PAGE_START);
        
        
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

 
