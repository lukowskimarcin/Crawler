package org.crawler;

import org.crawler.imp.Page;

/**
 * Zdarzenia zwrotne crawlerow 
 * @author Marcin
 * test
 */
public interface ICrawlingCallback<T extends Page<?>> {
	
	/**
	 * Zdarzenie rozpoczecia przetwarzania strony
	 * @param crawler 
	 * @param page
	 */
	void onPageCrawlingStart(ICrawlTask<T> crawler, T page); 
	
	/**
	 * Zdarzenie zakończenia przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingCompleted(ICrawlTask<T> crawler, T page);
	
	/**
	 * Zdarzenie błędu przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingFailed(ICrawlTask<T> crawler, T page);
	
	/**
	 * Zdarzenie ponownego zgłoszenia strony do przetworzenia
	 * @param crawler
	 * @param page
	 */
	void onAlreadyVisited(ICrawlTask<T> crawler, T page);
	
	/**
	 * Zdarzenie dla postępu prac nad stroną
	 * @param crawler
	 * @param page
	 * @param percent
	 */
	void onPageProcessingProgress(ICrawlTask<T> crawler, T page, int percent);
	
}
