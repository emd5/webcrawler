package edu.greenriver.it.driver;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import edu.greenriver.it.consumer.Parser;
import edu.greenriver.it.producer.Fetcher;
import edu.greenriver.it.threadsafe.LinkQueue;
import edu.greenriver.it.threadsafe.PageQueue;

public class Driver {

	public static void main(String[] args) throws InterruptedException {
		
		
		//instantiate link and page queues
		PageQueue page = new PageQueue();
		LinkQueue links = new LinkQueue();
		
		Scanner in = new Scanner(System.in);
		
		ArrayList<Parser> consumerList = new ArrayList<Parser>();
		Vector<String> keywordList = new Vector<String>();
		//ArrayList<Vector> keywordList = new ArrayList<String>();
		ArrayList<Fetcher> producerList = new ArrayList<Fetcher>();
		String url = null;
		
		
		
		while(true){
			menu();
			
			String userPrompt = in.nextLine();
			
			switch(userPrompt){
			case("1"):
				
				System.out.println("Enter a URL: " );
				url= in.nextLine();
				links.addLink(url);
				
				break;
			
			case("2"):
				
				consumerList.add(new Parser(page, links, keywordList));
				consumerList.get(consumerList.size()-1).start();
			
				break;
			case("3"):
				 
				producerList.add(new Fetcher(links ,page));
				
				producerList.get(producerList.size()-1).start();
				
				break;
				
			case("4"):
				System.out.println("Enter a keyword: ");
				String keyword = in.nextLine();
				
				keywordList.add(keyword);
				break;
			
			case("5"):
				
				//keywords found
				int keywords = 0;
				for(Parser i: consumerList){
					keywords +=i.getKeywords();
				}
			
				int failedDownloads =0;
				for(Fetcher i: producerList){
					failedDownloads += i.getFailedDownloads();
				}
				
				int pagesFound = page.getTotalPages();
				int linksFound = links.getLinksFound();	
				int totalProducers = producerList.size();
				int totalConsumers = consumerList.size();
			
				System.out.println("Keywords found: "+keywords);
				System.out.println("Links found: "+linksFound);
				System.out.println("Pages found: "+pagesFound);
				System.out.println("Failed downloads: "+failedDownloads);
				System.out.println("Producers: "+totalProducers);
				System.out.println("Consumers: "+totalConsumers);
				
				break;
				
			case("6"):
				in.close();
				System.out.println("Bye Bye");
				System.exit(0);
				break;
				
			default:
				System.out.println("Invalid Choice");
			}
		
		}
		
	}
	
	public static void menu(){
		Console.print("");
		Console.print("********************");
		Console.print("[1] Add seed URL ");
		Console.print("[2] Add consumer ");
		Console.print("[3] Add producer ");
		Console.print("[4] Add keyword search ");
		Console.print("[5] Print stats ");
		Console.print("[6] Exit");
		Console.print("");
		System.out.println("Enter option: ");
	}
	
	//System.out.println(producerList.isEmpty());
	//System.out.println(producerList.size());
	//System.out.println(producerList.get(0).getClass());

}
