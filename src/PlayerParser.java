import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PlayerParser {
	// Parser parse = new Parser("https://www.pro-football-reference.com/players/");
	private ArrayList<Player> playerList;

	public PlayerParser() {
		setPlayerList(new ArrayList<Player>());
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String load = "Loading status: [ ";
		System.out.println(load);
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
		Parser parse1 = new Parser("https://www.pro-football-reference.com/players/" + currentLetter + "/");
		// parse1.getArticlePage("Ken Anderson");

		// we are handling current players
		// these are bolded so we select on element - "b"
		Elements articleElements = parse1.currentDoc.select("b");
		ArrayList<String> playersBold = new ArrayList<String>();
		for (Element e : articleElements) {
			// bold is mentioned on all pages at top we omit this it's not a player
			// also some assorted bold text we don't want parse that out
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
			// System.out.println(e);

			// set name
			Player person = new Player(e);
			// reset base doc to the letter's page
			Parser newParse = new Parser(url);
			newParse.getArticles();
			// navigate to players page

			newParse.getArticlePage(e);

			// String newUrl = newParse.currentDoc.location();
			// System.out.println(newUrl);

			// we need to get the position since we have repeat names
			// the positions are text of a bold marker
			// position is always the third strong element
			Elements pEls = newParse.currentDoc.select("p");
			try {
				String position = pEls.get(1).text();
				// this gives us Position : _
				// we just want the position

				// need this because some player don't have a position
				if (position.contains("Position")) {

					int spacePos = position.indexOf(" ");
					String justPos = position.substring(spacePos + 1, position.length());
					if (justPos.length() >= 1 && justPos.length() <= 4 && justPos.matches("[a-zA-Z]+")) {
						person.setPosition(justPos);
						// System.out.println(justPos);
					}

				} else {
					person.setPosition("NA");
					// System.out.println("NA");

				}
			} catch (IllegalArgumentException error) {
				// some player dont

			}

			// now we load the teams played into the player object@
			// we will break at the yr 2020 bc 2021 has not been added to the page
			// still need to handle cases where someone was on multiple teams in a single

			Elements articleElements1 = newParse.currentDoc.select("tr");

			// remove first tr element so no for-each
			for (int i = 0; i < articleElements1.size(); i++) {

				if (articleElements1.get(i).text().contains("Year")) {
					continue;
				}
				
				
				// child(0) = year & child(2) = team
				// break on career so that we dont get extra info we do not want
				if (articleElements1.get(i).child(0).text().contains("Career")) {
					break;
				}
				//break if we're past career
				if (articleElements1.get(i).child(0).text().contains("yr")) {
					break;
				}

				String year = articleElements1.get(i).child(0).text();
				int errorCounter = 0;
				try {
					if (year.length() >= 4) {
						String mod = year.substring(0, 4);
						// System.out.println(mod);
						// System.out.println(articleElements1.get(i).child(2).text());
						int yr = Integer.parseInt(mod);
						person.setTeamAndYear(articleElements1.get(i).child(2).text(), yr);
					} else if (year.length() == 0) {
						// System.out.println("zer otest ");

						try {
//							if (articleElements1.get(i+2).child(0).text().contains("Career")) {
//								break;
//							}
							// do try cause this wont work if this is at the first year
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
								System.out.println(year);
								

								
								//had an error previously when 2TM was at the end of the table.
//								if (yearRetained.contains("2020")) {
//									//System.out.println("breaking!!");
//									break;
//								}
								
								i = i + 2;
								
								//now skip 2 so we dont get repeats and we move onto the next category
								
								
							}
						} catch (NumberFormatException exp) {

						}
					}

				} catch (NumberFormatException err) {
					errorCounter++;
//					System.out.println("-------------");
					System.out.println("error with parsing year to int:" + errorCounter);
					System.out.println(person.getName());
//					System.out.println(year);
//					System.out.println("-------------");

					
					// err.printStackTrace();
				}

			}
			// add the player to the list
			// still some glitches with certain -players
			if (person.getPosition() == null) {
				person.setPosition("NA");
//				System.out.println(person.getName());
//				System.out.println(person.getPosition());
			}
			playerList.add(person);

		}
//			Player ab = playerList.get(0);
//			ab.getTeams();
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

}