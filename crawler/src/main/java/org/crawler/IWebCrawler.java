package org.crawler;

import java.util.List;

import org.crawler.imp.CrawlTask;
import org.crawler.imp.PageWrapper;

public interface IWebCrawler<T> {

	/**
	 * Metoda Sprawdza czy strona byla przetwarzana
	 * @param page
	 * @return
	 */
	boolean isVisited(PageWrapper<T> page);
	
	/**
	 * Metoda oznacza stronę jako przetworzoną
	 * @param page
	 */
	void addVisited(PageWrapper<T> page);
	
	/**
	 * Start przetwarzania 
	 * @param rootTask : początkowa strona do przetwarzania
	 * @param block : czy blokować przetwarzanie
	 * @return
	 */
	void start(CrawlTask<T> rootTask, boolean block);
	
	/**
	 * Metoda przekazuje zadanie do puli do wykonania
	 * @param task:	
	 */
	void addTask(CrawlTask<T> task);
	
	/**
	 * Metoda zwraca globalnego obserwatora zdarzeń
	 * @return
	 */
	ICrawlingCallback<T> getCrawlingListener();
	
	void shutdown();
	
	void addCompletePage(PageWrapper<T> page);
	
	List<PageWrapper<T>> getCompletePage();
}
