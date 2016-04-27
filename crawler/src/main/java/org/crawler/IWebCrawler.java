package org.crawler;

import org.crawler.imp.Page;

public interface IWebCrawler<T extends Page<?>> {

	/**
	 * Metoda Sprawdza czy strona byla przetwarzana
	 * @param page
	 * @return
	 */
	boolean isVisited(T page);
	
	
	/**
	 * Metoda oznacza stronę jako przetworzoną
	 * @param page
	 */
	void addVisited(T page);
	
	
	/**
	 * Metoda przekazuje zadanie do puli do wykonania
	 * @param task:	
	 */
	void submitCrawlTask(ICrawlTask<T> task);
	
	
	/**
	 * Metoda zwraca globalnego obserwatora zdarzeń
	 * @return
	 */
	ICrawlingCallback<T> getCrawlingListener();
	

	
	
}
