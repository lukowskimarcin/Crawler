package org.crawler;

import org.crawler.imp.Page;

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
	void onPageCrawlingStart(ICrawler crawler, Page<?> page); 
	
	/**
	 * Zdarzenie zakończenia przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingCompleted(ICrawler crawler, Page<?> page);
	
	/**
	 * Zdarzenie błędu przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingFailed(ICrawler crawler, Page<?> page);
	
}
