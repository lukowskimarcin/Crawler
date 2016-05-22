package org.crawler;

import java.util.List;
import java.util.concurrent.Future;

import org.crawler.imp.PageWrapper;

public interface IWebCrawler<T extends PageWrapper<?>> {

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
	
	
	List<Future<T>> getPagesTask();
	
	void shutdown();
	
	
	boolean isAllTaskComplete();
	
	
	void onAllTaskComplete();
	
}
