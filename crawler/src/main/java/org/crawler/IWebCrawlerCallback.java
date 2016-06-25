package org.crawler;

import org.crawler.events.WebCrawlerEvent;

/**
 * Zdarzenia crawlera
 * @author marcian
 *
 */
public interface IWebCrawlerCallback<T> {

	/**
	 * Wszystkie zadania przetwarzania stron zostaly wykonane
	 */
	void onCrawlingFinished(WebCrawlerEvent event);
	
	/**
	 * Aktualne statystyki przetwarzania
	 * @param event
	 */
	void onCrawlingChangeState(WebCrawlerEvent event);
	
	
	void onTaskRejected(ICrawlTask<T> task);
	
}
