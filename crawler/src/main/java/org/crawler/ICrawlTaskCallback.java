package org.crawler;

import org.crawler.events.CrawlTaskEvent;

/**
 * Zdarzenia zwrotne crawlerow 
 * @author Marcin
 * 
 */
public interface ICrawlTaskCallback<T> {
	
	/**
	 * Zdarzenie rozpoczecia przetwarzania strony
	 * @param crawler 
	 * @param page
	 */
	void onPageCrawlingStart(CrawlTaskEvent<T> event); 
	
	/**
	 * Zdarzenie zakończenia przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingCompleted(CrawlTaskEvent<T> event);
	
	
	/**
	 * Zdarzenie błędu przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingFailed(CrawlTaskEvent<T> event);
	
	/**
	 * Zdarzenie ponownego zgłoszenia strony do przetworzenia
	 * @param crawler
	 * @param page
	 */
	void onAlreadyVisited(CrawlTaskEvent<T> event);
	
	/**
	 * Zdarzenie dla postępu prac nad stroną
	 * @param crawler
	 * @param page
	 * @param percent
	 */
	void onPageProcessingProgress(CrawlTaskEvent<T> event);
	
}
