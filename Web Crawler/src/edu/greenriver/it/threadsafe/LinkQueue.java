/**
 * 
 */
package edu.greenriver.it.threadsafe;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author Elizabeth Mahoney
 *
 */
public class LinkQueue{

	
	private LinkedList<String> linkQueue  = new LinkedList<String>();
	
	//list of strings with no duplicates
	private HashSet<String> confirmUrl = new HashSet<String>();
	
	private static final int MAX_LINK_SIZE = 50000;
	
	private static int uniqueLinks;
	
	public void addLink(String url) throws InterruptedException{
		
		synchronized(confirmUrl){
			
			while(linkQueue.size() >= MAX_LINK_SIZE){
				confirmUrl.wait();
			}
			
			if(confirmUrl.add(url)){
				linkQueue.add((url));
				
				uniqueLinks++;
				
				confirmUrl.notify();
			}
		}
	}
	
	
	public String getNextLink() throws InterruptedException{
		synchronized(confirmUrl){
			
			while(confirmUrl.size() ==0){
				confirmUrl.wait();
			}
			
			//remove first index  from the linkQueue
			String remove =linkQueue.removeFirst();
			
			confirmUrl.remove(remove);
			 
			confirmUrl.notify();
			 	 
			return remove;	
		}
	}
	
	//unique links found
	public int getLinksFound(){
		synchronized(confirmUrl){
			return uniqueLinks;
		}
	}
	

	
	
	
}
