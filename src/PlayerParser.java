
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PlayerParser {
	// Parser parse = new Parser("https://www.pro-football-reference.com/players/");
	private ArrayList<Player> playerList;

	public PlayerParser() {
		setPlayerList(new ArrayList<Player>());
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		//String for loading animation
		String load = "Loading status: [";
		System.out.println(load);
		
		//Iterate through the alphabet to get all players
		//Please allow 3-5 minutes for this operation to finish.
		//Enjoy the loading animation in the meanwhile
		for (int i = 0; i < alphabet.length(); i++) {
			playersOfChar(Character.toString(alphabet.charAt(i)));
			load += "|";
			if (i == alphabet.length() - 1) {
				load += "] :)";
			}
			System.out.println(load); 

		}

		// playersOfB(), etc.
	}

	public void playersOfChar(String currentLetter) {
	    //HashMap that maps a player name key to the links to player pages with that particular name
	    HashMap<String,ArrayList<String>> playerToLink = new HashMap<String,ArrayList<String>>();
	    
	    //Pattern matcher that will be used to properly extract the name and link for each player
	    Pattern p = Pattern.compile(".*\"(.*)\">(.*)<.*\\((.*)\\).*");
	    Matcher m;
	    
		Parser parse1 = new Parser("https://www.pro-football-reference.com/players/" + currentLetter + "/");
		
		// parse1.getArticlePage("Ken Anderson");
		
		
		// we are handling current players
		// these are bolded so we select on element - "b"
		Elements articleElements = parse1.currentDoc.select("b");
		ArrayList<String> playersBold = new ArrayList<String>();
		for (Element e : articleElements) {
		    //Pattern matching to get the following:
		    //m.group(1) = link for player
		    //m.group(2) = name of player
		    m = p.matcher(e.toString());
		    if (m.find()) {
		        String name = m.group(2).trim();
		        if (!playerToLink.containsKey(name)) {
		            playerToLink.put(name, new ArrayList<String>());
		        }
		        
		        //Add the links for some player by name
		        //This keeps track of all links for players under the same name
		        playerToLink.get(name).add(m.group(1));
		    }
			// Ensure that "bold" is not selected, since it is present in headers
			if (!(e.text().equals("bold")) && e.text().contains("(") && e.text().contains(")")) {
				// System.out.println(e.text());

				// we need to parse out the positions
				int indexOpenPar = e.text().indexOf("(");
				// we know there is a white space before the "(" so go -1 to eliminate it
				String modified = e.text().substring(0, indexOpenPar - 1);
				// System.out.println(modified);
				playersBold.add(modified);
			}
		}
		

		String url = parse1.currentDoc.location();
		// System.out.println(url);
		for (String e : playersBold) {
			//System.out.println(e);

			// set name
			Player person = new Player(e);
			// reset base doc to the letter's page
			Parser newParse = new Parser(url);
			newParse.getArticles();
			// navigate to players page

			//newParse.getArticlePage(e);
			
			//Use the link corresponding to the player with the same name
			
			newParse.getArticlePage(playerToLink.get(e).get(0));
			
			//Make sure to delete the link that was visited for some particular player name
			//If there are multiple players with the same name, we properly delete visited links
			//So that we can visit the ones that have not yet been visited
            playerToLink.get(e).remove(0);

			// we need to get the position since we have repeat names
			// the positions are text of a bold marker
			// position is always the third strong element
			Elements pEls = newParse.currentDoc.select("p");
			try {
				String position = pEls.get(1).text();
				//System.out.println("position: " + position);
				// this gives us Position : _
				// we just want the position

				// need this because some player don't have a position
				if (position.contains("Position")) {
				    //System.out.println("entered");
					int spacePos = position.indexOf(" ");
					String justPos = position.substring(spacePos + 1, position.length());
					
					//Handles cases such as "QB Throws: Right" and takes the substring of just QB
					if (justPos.length() > 4) {
					    justPos = justPos.substring(0,3).trim();
					}
					
					if (justPos.length() >= 1 && justPos.length() <= 4 && justPos.matches("[a-zA-Z]+")) {
						person.setPosition(justPos);
					} 
					//System.out.println(justPos);
				} else {
					person.setPosition("NA");
					// System.out.println("NA");

				}
			} catch (IllegalArgumentException error) {
				//System.out.println("ILLEGAL ARGUMENT");

			}

			// Now we load the teams played into the player object@

			Elements articleElements1 = newParse.currentDoc.select("tr");

			// Iterate through the list, need to keep track of i so a for-each loop is not used
			for (int i = 0; i < articleElements1.size(); i++) {
				// child(0) = year & child(2) = team
				if (articleElements1.get(i).text().contains("Year")) {
					continue;
				}
			
				
				// break on career so that we dont get extra info we do not want
				if (articleElements1.get(i).child(0).text().contains("Career")) {
					break;
				}
				//break if we're past career
				if (articleElements1.get(i).child(0).text().contains("yr")) {
					break;
				}

				String year = articleElements1.get(i).child(0).text();
				try {
					if (year.length() >= 4) {
						String mod = year.substring(0, 4);
						// System.out.println(mod);
						// System.out.println(articleElements1.get(i).child(2).text());
						int yr = Integer.parseInt(mod);
						person.setTeamAndYear(articleElements1.get(i).child(2).text(), yr);
					} else if (year.length() == 0) {
						// System.out.println("zero test ");
						
						/*****This indicates a mid-season TRADE and needs to be handled*****/

						try {
//							if (articleElements1.get(i+2).child(0).text().contains("Career")) {
//								break;
//							}
							// Skip over the first entry
							if (i > 0) {
								String yearRetained = articleElements1.get(i - 1).child(0).text();
								String modded = yearRetained.substring(0, 4);
								int yearInt = Integer.parseInt(modded);
								
								person.setTeamAndYear(articleElements1.get(i).child(2).text(), yearInt);
								person.setTeamAndYear(articleElements1.get(i+1).child(2).text(), yearInt);
								
								//now lets delete the 2TM entry
								HashMap<String,ArrayList<Integer>> checker = new HashMap<String, ArrayList<Integer>>();
								checker = person.getPlayerMap();
								try {
									
									checker.remove("2TM");
									
								}catch (IllegalArgumentException excep) {
									
								}
								//System.out.println(year);

								i = i + 2;
								
								// Skip 2 and move on to the next year 
								
								
							}
						} catch (NumberFormatException exp) {

						}
					}

				} catch (NumberFormatException err) {
					System.out.println("error with parsing year to int:");
					System.out.println(person.getName());
				}

			}
			//If a position is not listed
			if (person.getPosition() == null) {
				person.setPosition("NA");
//				System.out.println(person.getName());
//				System.out.println(person.getPosition());
			}
			playerList.add(person);

		}
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

}