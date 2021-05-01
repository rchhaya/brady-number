import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;

public class PlayerParser {
	// Parser parse = new Parser("https://www.pro-football-reference.com/players/");
	private ArrayList<Player> playerList = new ArrayList<Player>();

	public void playersOfA() {
		Parser parse1 = new Parser("https://www.pro-football-reference.com/players/A/");
		// parse1.getArticlePage("Ken Anderson");

		// we are handling current players
		// these are bolded so we select on element - "b"
		Elements articleElements = parse1.currentDoc.select("b");
		ArrayList<Element> playersBold = new ArrayList<Element>();
		for (Element e : articleElements) {
			// bold is mentioned on all pages at top we omit this it's not a player
			if (e.text() != "bold") {

				playersBold.add(e);
			}
		}

		String url = parse1.currentDoc.location();
		for (Element e : playersBold) {
			// System.out.println(e.text());
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
					// time save@
					break;
				}
			}

			// now we load the teams played into the player object@
			// we will break at the yr 2020 bc 2021 has not been added to the page
			Elements articleElements1 = newParse.currentDoc.select("tr");
			for (Element y : articleElements1) {
				// child(0) = year & child(2) = team
				if (y.child(0).text().contains("2020")) {
					break;
				}

				String year = y.child(0).text();
				try {
					int yr = Integer.parseInt(year);
					person.setTeamAndYear(y.child(2).text(), yr);
				} catch (NumberFormatException err) {
					System.out.println("error with parsing year to int");
					err.printStackTrace();
				}

			}

		}

	}

}
