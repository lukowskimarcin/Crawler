package org.crawler;

import java.util.List;

import org.crawler.events.CrawlTaskEvent;

/**
 * Podstawowe zdarzenia dla przetwarzania stron
 * @author Marcin
 *
 * @param 
 */
public interface ICrawlTaskListener {
	/**
	 * Początek przetwarzania strony
	 * @param listener
	 */
	void addOnPageCrawlingStartListener(IEventListener<CrawlTaskEvent> listener);
	
	List<IEventListener<CrawlTaskEvent>> getOnPageCrawlingStartListener();
	
	/**
	 * Zakończenie przetwarzania strony
	 * @param listener
	 */
	void addOnPageCrawlingCompletedListener(IEventListener<CrawlTaskEvent> listener);
	
	List<IEventListener<CrawlTaskEvent>> getOnPageCrawlingCompletedListener();
	
	/**
	 * Błąd przetwarzania strony
	 * @param listener
	 */
	void addOnPageCrawlingFailedListener(IEventListener<CrawlTaskEvent> listener);
	
	
	List<IEventListener<CrawlTaskEvent>> getOnPageCrawlingFailedListener();
	
	/**
	 * Strona była juz zgłaszana do przetworzenia
	 * @param listener
	 */
	void addOnAlreadyVisitedListener(IEventListener<CrawlTaskEvent> listener);
	
	List<IEventListener<CrawlTaskEvent>> getOnAlreadyVisitedListener();
	
	/**
	 * Postęp przetwarzania strony
	 * @param listener
	 */
	void addOnPageProcessingProgressListener(IEventListener<CrawlTaskEvent> listener);
	
	List<IEventListener<CrawlTaskEvent>> getOnPageProcessingProgressListener();
}
