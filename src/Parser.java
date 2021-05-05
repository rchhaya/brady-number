import java.io.IOException;
//These methods are from lecture in order to print out the HTML contents of a page

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class Parser {

	private String baseURL;
	public Document currentDoc;
	Map<String, String> articleMap;

	/*
	 * Constructor that initializes the base URL and loads the document produced
	 * from that URL
	 */
	public Parser() {
		this.baseURL = "https://www.pro-football-reference.com/players/";
		try {
			this.currentDoc = Jsoup.connect(this.baseURL).get();
			// System.out.println(this.currentDoc);
		} catch (IOException e) {
			// System.out.println("Could not get the corgis :(");
		}
	}

	public Parser(String url) {
		this.baseURL = url;
		try {
			this.currentDoc = Jsoup.connect(this.baseURL).get();
			// System.out.println(this.currentDoc);
		} catch (IOException e) {
			// System.out.println("Could not get the corgis :(");
		}

	}

	/*
	 * Creates article map to be a mapping of article titles to url from our current
	 * doc
	 */
	public void getArticles() {
		this.articleMap = new HashMap<String, String>();
		Elements articleElements = this.currentDoc.select("a"); // gets all elements of type article
		for (Element article : articleElements) {
			Elements aTag = article.select("a"); // links come in <a> tags typically

			// Element a = aTag.get(0);

			// System.out.println("aTag: " + aTag);
			for (Element a : aTag) {
				// System.out.println("a: " + a);

				String articleURL = a.attr("href");
				String articleTitle = a.text();
				this.articleMap.put(articleTitle, articleURL);
				// System.out.println(articleTitle);
				// System.out.println();

			}
		}

	}

	/*
	 * Gets the document for a URL on our currentDoc
	 * 
	 * @param articleTitle This should be the name of the article we want to get the
	 * url for
	 * 
	 * @return the document for the articleTitle input
	 */
	public String getArticlePage(String articleTitle) {
		String url = this.articleMap.get(articleTitle);

		// quick mod here just appends the start on in case we need it
		if (url != null && !url.contains("https:")) {
			url = "https://www.pro-football-reference.com" + url;
		}

		 //System.out.println(url); // check we have the right URL
		try {
			this.currentDoc = Jsoup.connect(url).get();
			// System.out.println(currentDoc);
		} catch (IOException e) {
			System.out.println("error!!");
		}
		Element head = this.currentDoc.selectFirst("head");
		Element title = head.selectFirst("title");

		// Element description =
		// this.currentDoc.select("p.description__1bzzdbaw8q").first();
		// System.out.println(description.text());
		return title.text();
	}

	/*
	 * Gets the image url from our page for the associated keyword
	 * 
	 * @param keyword This should be the word in the image caption for the image we
	 * want to get
	 * 
	 * @return the image url for the associated keyword
	 */


}
