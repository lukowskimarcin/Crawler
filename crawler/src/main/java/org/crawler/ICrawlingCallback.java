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
	void onPageCrawlingStart(ICrawlTask crawler, Page<?> page); 
	
	/**
	 * Zdarzenie zakończenia przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingCompleted(ICrawlTask crawler, Page<?> page);
	
	/**
	 * Zdarzenie błędu przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingFailed(ICrawlTask crawler, Page<?> page);
	
}
