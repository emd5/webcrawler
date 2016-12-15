/*Elizabeth Mahoney & Jacob Langham
 * Nov 14, 2016
 * Fetcher.thread (Producer)
 */
package edu.greenriver.it.producer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.greenriver.it.threadsafe.LinkQueue;
import edu.greenriver.it.threadsafe.PageQueue;

/**
 * @author Elizabeth Mahoney
 * @author Jacob Langham
 * @version 1.0
 *

	A producer thread (Fetcher) should do the following repeatedly (ie.
 *          in an infinite loop): pull a link from the link queue download the
 *          (HTML) page text at the given URL store the (HTML) page text on the
 *          page queue as a string A BufferedReader object can be used to read
 *          from the remote page using the following code sample:
 * 
 *         
 *          Note: you will still need to write a loop that pulls each line from
 *          the remote file over HTTP. You should then build a single string
 *          value that contains the entire contents of the remote file.
 */
public class Fetcher extends Thread {

	// declare instance variables
	private PageQueue pageQueue;
	private String pageText;
	private LinkQueue fetchLink;
	private int failedDownloads;

	/**
	 * Parameterized constructor that excepts a link from the link queue 
	 * and passes the page web document to the pageQueue
	 * 
	 * @param fetchLink the link from the queue
	 * @param retrieveURL user URL input
	 */
	public Fetcher(LinkQueue fetchLink, PageQueue pageQueue) {
		this.pageQueue = pageQueue;
		this.fetchLink = fetchLink;
	}

	
	/**
	 * 
	 */
	@Override
	public void run() {
		
		while (true) {
			try{
				//this pulls link from link queue
				URL url = new URL(fetchLink.getNextLink()); 
				
				System.out.println(url);
				
				//set up connection to 
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				BufferedReader download = new BufferedReader
						(new InputStreamReader(connection.getInputStream()));
				
				int count = 0;
				String line;
				while ((line = download.readLine()) != null) {
					
					if(count<2){
						System.out.println(line);
						count++;
					}
					
					
					// store HTML page text on the page queue as a string
					pageText += line;
					
				}
				if(pageText !=null)	{			
					// add page to page queue
					pageQueue.addPage(pageText);
				}
			}
			
			//if URL can't be detected, counts failed DLs
			catch(Exception e){
				
				failedDownloads++;
				
				System.out.println("Invalid Url:" + e.getMessage());
			
			}
		}
	}

	
	public int getFailedDownloads(){
		
		return failedDownloads;
	}
	
	
	
	
	
	
	
}
