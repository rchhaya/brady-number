import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PlayerParser {
	// Parser parse = new Parser("https://www.pro-football-reference.com/players/");
	private ArrayList<Player> playerList;

	public PlayerParser() {
		setPlayerList(new ArrayList<Player>());
		playersOfA();
		// playersOfB(), etc.
	}

	public void playersOfA() {
		Parser parse1 = new Parser("https://www.pro-football-reference.com/players/A/");
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
			 System.out.println(e);

			// set name
			Player person = new Player(e);
			// reset base doc to the letter's page
			Parser newParse = new Parser(url);
			newParse.getArticles();
			// navigate to players page

			newParse.getArticlePage(e);

			String newUrl = newParse.currentDoc.location();
			// System.out.println(newUrl);

			// we need to get the position since we have repeat names
			// the positions are text of a bold marker
			//position is always the third strong element
			Elements pEls = newParse.currentDoc.select("p");
			String position = pEls.get(1).text();
			//this gives us Position : _
			//we just want the position 
			
			int spacePos = position.indexOf(" ");
			String justPos = position.substring(spacePos + 1, position.length());
			person.setPosition(justPos);
			System.out.println(justPos);

			// now we load the teams played into the player object@
			// we will break at the yr 2020 bc 2021 has not been added to the page
			// still need to handle cases where someone was on multiple teams in a single
			// year
			Elements articleElements1 = newParse.currentDoc.select("tr");
			
			//remove first tr element so no for-each
			for (int i = 0; i < articleElements1.size(); i++) {
				
				if(articleElements1.get(i).text().contains("Year")) {
					continue;
				}
				// child(0) = year & child(2) = team
				// break on career so that we dont get extra info we do not want
				if (articleElements1.get(i).child(0).text().contains("Career")) {
					break;
				}

				String year = articleElements1.get(i).child(0).text();
				try {
					if (year.length() >= 4) {
						String mod = year.substring(0, 4);
						System.out.println(mod);
						System.out.println(articleElements1.get(i).child(2).text());
						int yr = Integer.parseInt(mod);
						person.setTeamAndYear(articleElements1.get(i).child(2).text(), yr);
					}
					// add the player to the list
					playerList.add(person);

				} catch (NumberFormatException err) {
					System.out.println("error with parsing year to int");
					// err.printStackTrace();
				}

			}

		}

	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

}