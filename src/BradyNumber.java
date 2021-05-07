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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;

@SuppressWarnings({ "serial", "unused" })
public class BradyNumber extends JFrame implements Runnable {
    
    private JLabel statusBar = new JLabel();
    static String username = "";
    private JTextField userInput1 = new JTextField("",20);
    private JTextField userInput2 = new JTextField("", 20);
    private boolean running;
    
    public void run() {
    	//Parses from the web, creates a playerList, and creates an Adjacency List
    	//Please allow 3-5 minutes for the parsing to finish
    	PlayerParser parser = new PlayerParser();
    	ArrayList<Player> playerList = parser.getPlayerList();
    	AdjacencyList aList = new AdjacencyList(playerList);
        
        running = true;
        
        statusBar = new JLabel("Brady Number: "); 
        
        //Frame Label
        final JFrame frame = new JFrame("Brady Number");
        //Attempted ASCII art
        //String asciiArt = new String(""
//        		+ "        _.-=\"\"=-._ \r\n"
//        		+ "      .'\\\\-++++-//'.\r\n"
//        		+ "     (  ||      ||  )\r\n"
//        		+ "      './/      \\\\.'\r\n"
//        		+ "        `'-=..=-'`");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 3, screenSize.height / 3);
        
//        JLabel ascii1 = new JLabel("        _.-=\\\"\\\"=-._");
//        JLabel ascii2 = new JLabel("      .'\\\\\\\\-++++-//'.");
//        JLabel ascii3 = new JLabel("     (_    ||        ||    _)");
//        JLabel ascii4 = new JLabel("      './/      \\\\\\\\.'");
//        JLabel ascii5 = new JLabel("        -.-=\\\"\\\"=-.-");
        
        
        //Opening window
        JLabel intro1 = new JLabel("  Welcome to Brady number!");
        JLabel introWhitespace1 = new JLabel("");
        JLabel intro2 = new JLabel("  Please click the instructions button if you are unfamiliar with the input format  ");
        JLabel intro3 = new JLabel("  If you are unfamilar with the names and positions of players, check "
        		+  "out the following link:  ");
        JLabel introLink = new JLabel("  Pro Football Focus");
        JLabel introWhitespace2 = new JLabel(" ");
        JLabel introWhitespace3 = new JLabel(" ");
        JLabel intro4 = new JLabel("  Please enter the legal first and last name of any 2 active NFL players (title-case capitalization)  ");
        JLabel intro5 = new JLabel("  Optionally, enter their position abbreviation at the end in parentheses (ex. (DB), (RB))"  );
        JLabel intro6 = new JLabel("  This is required if your input shares a name with another player (ex. Josh Allen (QB), Josh Allen (EDGE))  ");
        JLabel introWhitespace4 = new JLabel(" ");
        JLabel intro7 = new JLabel("  We can calculate the degrees of seperation between the players!  ");
        JLabel intro8 = new JLabel("  (1 degree of seperation = playing on the same team at the same time)  ");
        JLabel introWhitespace5 = new JLabel(" ");
        
        
        JPanel introPanel = new JPanel();
        introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));
      
        //Setting up the hyperlink
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
        
        //Add all elements to the intro panel, formatted as a vertically-oriented box
        introPanel.add(intro1);
//        introPanel.add(ascii1);
//        //introPanel.add(ascii2);
//        introPanel.add(ascii3);
//        //introPanel.add(ascii4);
//        introPanel.add(ascii5);

        introPanel.add(introWhitespace1);
        introPanel.add(intro2);
        introPanel.add(intro3);
        introPanel.add(introLink);
        introPanel.add(introWhitespace2);
        introPanel.add(introWhitespace3);
        introPanel.add(intro4);
        introPanel.add(intro5);
        introPanel.add(intro6);
        introPanel.add(introWhitespace4);
        introPanel.add(intro7);
        introPanel.add(intro8);
        introPanel.add(introWhitespace5);
        
        
        
        //Status Panel
        JPanel statusPanel = new JPanel();
        frame.add(statusPanel, BorderLayout.SOUTH);
        statusBar = new JLabel("Brady Number: ");
        statusPanel.add(statusBar);
        

        //Panels to hold the user input areas and button
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
        

        //Directions Button
        final JButton menu = new JButton("Directions");
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e3) {
                JOptionPane.showMessageDialog(null,
                        "Enter 2 current (active) NFL players in the spaces provided.\n"
                        + "Please enter their full first and last name (no nicknames) with title-case capitalization. \n"
                        + "This typically is first and last name, but can include suffixes. Check Pro Football Focus to clarify name and position. \n"
                        + "\nIf the inputted player shares a name with another player, input their 1-, 2-, 3-, or 4-letter position abbreviation."
                        + "\nPlease do so in parentheses at the end of the name, like Josh Allen (QB)."
                        + "\nThe position input is optional for other players and the program will function with and without this information.\n\n"
                        + "Examples of valid inputs withouts suffixes: Tom Brady, Dak Prescott, Jalen Hurts, Ezekiel Elliot, Josh Allen."
                        + "\nExamples of valid inputs with suffixes: Odell Beckham Jr., Robert Griffen III"
                        + "\nExamples of valid inputs with same names: Josh Allen (QB), Josh Allen (EDGE), Lamar Jackson (QB), Lamar Jackson (DB) "
                        + "\nExamples of invalid inputs: Zeke, Thomas Brady, AB (Antonio Brown), Captain Kirk",
                        "Instructions", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        //Enter Button
        final JButton enter = new JButton("Enter");
//        BufferedWriter bw = null;
//        FileWriter f = null;
        
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e4) {
            	//Check to make sure strings are long enough
            	if (userInput1.getText().length() < 2 || userInput2.getText().length() < 2) {
            		JOptionPane.showMessageDialog(null,
                            "You entered an invalid input.\n"
                            + "Please re-enter and follow the syntax rules specified in the directions tab."
                            + "\nCheck out Pro Football Focus to ensure you are inputting a correctly-spelled active player",
                            "Invalid input!", JOptionPane.INFORMATION_MESSAGE);
            	} else {
            	//Parse the input for the name and check if the position was provided
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
                    	
//            	System.out.println(name1);
//            	System.out.println(position1);
            	
            	//Parse the second input for name/pos
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
            	
//            	System.out.println(name2);
//            	System.out.println(position2);
            	
            	//Assign players to the string inputs
            	Player player1 = new Player("");
            	Player player2 = new Player("");
            	boolean assigned1 = false;
            	boolean assigned2 = false;
            	//Remove duplicates just in case
            	Set<Player> playerSet = new HashSet<Player>(playerList);
            	//Keep track of the count for each player in case of duplicates
            	HashMap<String,Integer> playerCount = new HashMap<String,Integer>();
            	for (Player p : playerSet) {
            	    if (playerCount.get(p.getName()) == null) {
            	        playerCount.put(p.getName(), 1);
            	    } else {
            	        playerCount.put(p.getName(), playerCount.get(p.getName()) + 1);
            	    }
            	    
            	}
            	//Handling duplicate name inputs
            	
            	//Keep track of whether or not input player has duplicate name
            	boolean duplicateName = false;
            	
            	//Pattern matcher to check if input does not of the form "Name (Position)"
            	// If the name of the player is a duplicate
            	Pattern pa = Pattern.compile(".*\\((.*)\\)");
            	Matcher m;
            	
            	for (Player p : playerSet){
            		//System.out.println(p.getName() + ":" + p.getPosition());
            		
            	  if (p.getName().equals(name1) && (p.getPosition().equals(position1) || position1.equals("")) && !assigned1){
            	  	  if (playerCount.get(p.getName()) > 1) {
            	  	      m = pa.matcher(entry1);
            	  	      if (!m.find()) {
            	  	        duplicateName = true;
                            continue;
            	  	      }
            	  	  }
            	      player1 = p;
                      assigned1 = true;
                      //System.out.println("here1");
                      continue;
            	  }     
            	  if (p.getName().equals(name2) && (p.getPosition().equals(position2) || position2.equals("")) && !assigned2){
            		  if (playerCount.get(p.getName()) > 1) {
            		      m = pa.matcher(entry2);
            		      if (!m.find()) {
            		          duplicateName = true;
                              continue;
            		      }
            		  }
            	      player2 = p;
                      assigned2 = true;
                      //System.out.println("here2");
                      continue;
            	  }   
            	 
            	 }
            	
            	//Duplicate name provided without a position
            	if (duplicateName) {
            	    JOptionPane.showMessageDialog(null,
                            "You entered a player who shares a name with at least 1 other another active player.\n"
                            + "Please re-enter and follow the syntax rules specified in the directions tab. \n"
                            + "\nIn this case, you must enter their 1-, 2-, 3-, or 4-letter position abbreviation to specify the player.\n"
                            + "Please do so by placing the position in parentheses at the end of their name. \n"
                            + "While this is optional in other players, it must be specified in cases of shared names.\n"
                            + "\nExamples with position: Josh Allen (QB), Josh Allen (EDGE), Josh Jones (S), Josh Jones (OL)\n",
                            "Invalid input!", JOptionPane.INFORMATION_MESSAGE);
            	} else if (player1.getName().equals("") || player2.getName().equals("")) {
            		JOptionPane.showMessageDialog(null,
                            "You entered an invalid input.\n"
                            + "Please re-enter and follow the syntax rules specified in the directions tab."
                            + "\nCheck out Pro Football Focus to ensure you are inputting a correctly-spelled active player",
                            "Invalid input!", JOptionPane.INFORMATION_MESSAGE);
            	} else {
            		
            		//BFS is ran on the 2 players 
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
            		
            		//The Brady Number is displayed
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
        
        //Add buttons and userInput areas to the panels  
        controlPanel1.setLayout(new BoxLayout(controlPanel1, BoxLayout.Y_AXIS));
        JLabel player1Text = new JLabel("Player 1: ");
        userInput1.setText("");
        JPanel entry1 = new JPanel();
        entry1.add(player1Text);
        entry1.add(userInput1);
        
        JLabel player2Text = new JLabel("Player 2: ");
        userInput2.setText("");
        JPanel entry2 = new JPanel();
        entry2.add(player2Text);
        entry2.add(userInput2);
        
        controlPanel1.add(entry1);
        controlPanel1.add(entry2);
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
        //frame.setSize(500,100);
        
        
    }
    
    public JLabel getStatusBar() {
        return statusBar;
    }
    
    public static void main(String[] args) {
        //BradyNumber b = new BradyNumber();
        //b.setLocationRelativeTo(null);
        //b.setVisible(true);
        SwingUtilities.invokeLater(new BradyNumber());
    }
}

 
