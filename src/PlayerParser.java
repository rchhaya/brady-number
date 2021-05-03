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
		//playersOfB(), etc.
	}

	public void playersOfA() {
		Parser parse1 = new Parser("https://www.pro-football-reference.com/players/A/");
		// parse1.getArticlePage("Ken Anderson");

		// we are handling current players
		// these are bolded so we select on element - "b"
		Elements articleElements = parse1.currentDoc.select("b");
		ArrayList<Element> playersBold = new ArrayList<Element>();
		for (Element e : articleElements) {
			// bold is mentioned on all pages at top we omit this it's not a player
			if (!(e.text().equals("bold"))) {
				System.out.println(e.text());
				playersBold.add(e);
			}
		}

		String url = parse1.currentDoc.location();
		for (Element e : playersBold) {
			//System.out.println(e.text());
			Player person = new Player(e.text());
			Parser newParse = new Parser(url);
			newParse.getArticles();
			newParse.getArticlePage(e.text());

			// we need to get the position since we have repeat names
			// the positions are text of a bold marker
			Elements pEls = newParse.currentDoc.select("strong");
			for (Element x : pEls) {
				if (x.text().length() <= 2) {
					person.setPosition(x.text());
					System.out.println(x.text());
					// time save@
					break;
				}
			}
			

			// now we load the teams played into the player object@
			// we will break at the yr 2020 bc 2021 has not been added to the page
			//still need to handle cases where someone was on multiple teams in a single year
			Elements articleElements1 = newParse.currentDoc.select("tr");
			for (Element y : articleElements1) {
				// child(0) = year & child(2) = team
				//break on career so that we dont get extra info we do not want
				if (y.child(0).text().contains("Career")) {
					break;
				}

				String year = y.child(0).text();
				try {
					int yr = Integer.parseInt(year);
					person.setTeamAndYear(y.child(2).text(), yr);
					
					//add the player to the list
					playerList.add(person);
					
				} catch (NumberFormatException err) {
					System.out.println("error with parsing year to int");
					err.printStackTrace();
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