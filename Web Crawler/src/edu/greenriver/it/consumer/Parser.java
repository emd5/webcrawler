/*Liz Mahoney Jacob Langham
 * Nov 16th, 2016
 * Parser.java
 */

package edu.greenriver.it.consumer;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.greenriver.it.threadsafe.LinkQueue;
import edu.greenriver.it.threadsafe.PageQueue;

/**
 * consumer thread (Parser) should do the following repeatedly (ie. in an
 * infinite loop):
 * 
 * pull a page from the page queue search the page for all links in anchor (
 * <a href="">) elements add each link found to the link queue search the page
 * for any keywords specified by the user of the web crawler (more on this
 * later) keep track of how many keywords are encountered Parsing a web document
 * for links can be a difficult task. Regular expressions are a pattern
 * recognition language that is used match complex patterns in text. A good
 * tutorial on the subject can be found at:
 * http://www.regular-expressions.info/tutorial.html (Links to an external
 * site.). Below I have provided a short code segment that will match most URLs
 * found in anchor tags on a page. The loop will execute for each link found:
 * 
 * @author Liz Mahoney
 * @author Jacob Langham
 * @version 1.0
 *
 */
public class Parser extends Thread {

	// declare instance variables
	private PageQueue pageText;
	private LinkQueue linkQueue;
	private Vector<String> keywordList;
	//private ArrayList<String> keywordList;
	private int keywordsFound;

	/**
	 * A parameterized constructor that passes a page from the page queue
	 * 
	 * @param pageText page from the queue
	 * @param 
	 */
	public Parser(PageQueue pageText, LinkQueue linkQueue, Vector<String> keywordList) {
		this.pageText = pageText;
		this.linkQueue = linkQueue;
		this.keywordList = keywordList;
	}

	@Override
	public void run() {

		while (true) {
			try {

				// pull a page from the page queue
				String page = pageText.getNextPage();

				// detects links in HTMl file in page
				Pattern pattern = Pattern.compile("href=\"(http:.*?)\"");

				// match the link found
				Matcher matcher = pattern.matcher(page);

				// continuously find a link
				while (matcher.find()) {

					// denote
					String link = matcher.group(1);

					// System.out.println(link);

					// add link to queue
					linkQueue.addLink(link);

					////// looking for keywords the user search
					// search all keywords

		
				}
				for (Object foundKey : keywordList) {
					
					//foundKey = (String)foundKey;
					//System.out.println(foundKey);
					// split page
					String[] splitText = page.split((String) foundKey);

					// find number of keywords, the length of the array - 1
					keywordsFound += splitText.length - 1;
				}

			} 
			catch (InterruptedException e) {
				System.out.println("Link already added " + e.getMessage());
			}
		}
	}

	public void addKeyword(String keyword) {
		keywordList.add(keyword);
	}

	public int getKeywords() {

		return keywordsFound;
	}

}
