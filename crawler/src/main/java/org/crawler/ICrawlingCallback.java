package org.crawler;

import org.crawler.imp.PageTask;

/**
 * Zdarzenia zwrotne crawlerow 
 * @author Marcin
 *
 */
public interface ICrawlingCallback {
	
	/**
	 * Zdarzenie rozpoczecia przetwarzania strony
	 * @param crawler 
	 * @param page
	 */
	void onPageCrawlingStart(ICrawler crawler, PageTask<?> page); 
	
	/**
	 * Zdarzenie zakończenia przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingCompleted(ICrawler crawler, PageTask<?> page);
	
	/**
	 * Zdarzenie błędu przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingFailed(ICrawler crawler, PageTask<?> page);
	
}
