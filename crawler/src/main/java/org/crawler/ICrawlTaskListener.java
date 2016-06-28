package org.crawler;

import java.util.List;

import org.crawler.events.CrawlTaskEvent;

/**
 * Podstawowe zdarzenia dla przetwarzania stron
 * @author Marcin
 *
 * @param <T>
 */
public interface ICrawlTaskListener<T> {
	/**
	 * Początek przetwarzania strony
	 * @param listener
	 */
	void addOnPageCrawlingStartListener(IEventListener<CrawlTaskEvent<T>> listener);
	
	List<IEventListener<CrawlTaskEvent<T>>> getOnPageCrawlingStartListener();
	
	/**
	 * Zakończenie przetwarzania strony
	 * @param listener
	 */
	void addOnPageCrawlingCompletedListener(IEventListener<CrawlTaskEvent<T>> listener);
	
	List<IEventListener<CrawlTaskEvent<T>>> getOnPageCrawlingCompletedListener();
	
	/**
	 * Błąd przetwarzania strony
	 * @param listener
	 */
	void addOnPageCrawlingFailedListener(IEventListener<CrawlTaskEvent<T>> listener);
	
	
	List<IEventListener<CrawlTaskEvent<T>>> getOnPageCrawlingFailedListener();
	
	/**
	 * Strona była juz zgłaszana do przetworzenia
	 * @param listener
	 */
	void addOnAlreadyVisitedListener(IEventListener<CrawlTaskEvent<T>> listener);
	
	List<IEventListener<CrawlTaskEvent<T>>> getOnAlreadyVisitedListener();
	
	/**
	 * Postęp przetwarzania strony
	 * @param listener
	 */
	void addOnPageProcessingProgressListener(IEventListener<CrawlTaskEvent<T>> listener);
	
	List<IEventListener<CrawlTaskEvent<T>>> getOnPageProcessingProgressListener();
}
