/*Liz Mahoney & Jacob Langham
 * Nov 14th, 2016
 * PageQueue.java
 */

package edu.greenriver.it.threadsafe;

import java.util.LinkedList;

/**
 * The shared page queue should store a linked list of strings, each of which
 * has the text of a web document. Adds a new page to the queue. The queue
 * should have a maximum size of 50000. If there is no room on the queue, then
 * the thread should call wait() on the queue. After adding a new page to the
 * queue, you should call notify() on the queue.
 * 
 * @author Liz Mahoney
 * @author Jacob Langham
 * @version 1.0
 *
 */
public class PageQueue {

	// declare all instance variables
	private static final int PAGE_QUEUE_SIZE = 50000;
	private LinkedList<String> pageQueue = new LinkedList<String>();
	private int totalPages;

	/**
	 * Adds a new page to the queue. If there is no room on the queue, then the
	 * thread should call wait() on the queue. After adding a new page to the
	 * queue, you should call notify() on the queue.
	 * 
	 * @param pageText
	 */
	public void addPage(String pageText) throws InterruptedException {
		synchronized (pageQueue) {
			// System.out.println("page text" + pageText);
			// if queue is full, thread calls wait on queue
			

				while (pageQueue.size() >= PAGE_QUEUE_SIZE) {
					pageQueue.wait();
				}

				// adds new page to the queue
				pageQueue.add(pageText);

				// System.out.println("PageText " +pageText);
				// increments the number of pages when added
				totalPages++;

				// wakes up queue
				pageQueue.notify();
			
		}
	}

	public int getTotalPages() {
		synchronized (pageQueue) {

			return totalPages;
		}
	}

	/**
	 * Returns a page from the queue. If the queue is empty, then the thread
	 * should call wait() on the queue. After removing a page from the queue,
	 * you should call notify() on the queue before returning the page text.
	 * 
	 * @return a page from the queue
	 * @throws InterruptedException
	 */
	public String getNextPage() throws InterruptedException {

		synchronized (pageQueue) {
			
			// if queue is empty, wait
			while (pageQueue.size() <= 0) {

				pageQueue.wait();
			}

			String removePage = pageQueue.removeFirst();

			pageQueue.notify();

			return removePage;
		}

	}

	/**
	 * This method returns the total number of pages that have been added to the
	 * queue through the addPage() method.
	 * 
	 * @return the total number of pages
	 * @throws InterruptedException
	 */
	public int getPagesDownloaded() throws InterruptedException {
		synchronized (pageQueue) {

			return totalPages;

		}
	}

}
