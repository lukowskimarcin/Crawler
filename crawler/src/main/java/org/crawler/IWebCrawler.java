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
	 * Metoda zwraca globalnego obserwatora zdarzeń
	 * @return
	 */
	ICrawlingCallback<T> getCallbackListener();
	
	
	
}
