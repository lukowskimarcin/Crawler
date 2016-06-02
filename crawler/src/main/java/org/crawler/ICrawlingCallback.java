package org.crawler;

import org.crawler.imp.PageWrapper;

/**
 * Zdarzenia zwrotne crawlerow 
 * @author Marcin
 * 
 */
public interface ICrawlingCallback<T> {
	
	/**
	 * Zdarzenie rozpoczecia przetwarzania strony
	 * @param crawler 
	 * @param page
	 */
	void onPageCrawlingStart(ICrawlTask<T> crawler, PageWrapper<T> page); 
	
	/**
	 * Zdarzenie zakończenia przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingCompleted(ICrawlTask<T> crawler, PageWrapper<T> page);
	
	
	/**
	 * Zdarzenie błędu przetwarzania strony
	 * @param crawler
	 * @param page
	 */
	void onPageCrawlingFailed(ICrawlTask<T> crawler, PageWrapper<T> page);
	
	/**
	 * Zdarzenie ponownego zgłoszenia strony do przetworzenia
	 * @param crawler
	 * @param page
	 */
	void onAlreadyVisited(ICrawlTask<T> crawler, PageWrapper<T> page);
	
	/**
	 * Zdarzenie dla postępu prac nad stroną
	 * @param crawler
	 * @param page
	 * @param percent
	 */
	void onPageProcessingProgress(ICrawlTask<T> crawler, PageWrapper<T> page, int percent);
	
	
	void onCrawlingFinished();
	
}
