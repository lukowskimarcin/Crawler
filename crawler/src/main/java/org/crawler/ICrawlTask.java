package org.crawler;

import java.util.concurrent.Callable;

import org.crawler.events.CrawlTaskEvent;
import org.crawler.imp.PageWrapper;

/**
 * Interfejs crawlera
 * @author Marcin
 *
 */
public interface ICrawlTask<T> extends Callable<T>   {
	
	/**
	 * Metoda inicjuje zadanie do wykonania
	 * @param webCrawler crawler, który ma wykonać zadanie
	 */
	void init(IWebCrawler<T> webCrawler);
	
	
	void addOnPageProcessingProgressListener(IEventListener<CrawlTaskEvent<T>> listener);

	void addOnAlreadyVisitedListener(IEventListener<CrawlTaskEvent<T>> listener);
	
	void addOnPageCrawlingFailedListener(IEventListener<CrawlTaskEvent<T>> listener);
	
	void addOnPageCrawlingCompletedListener(IEventListener<CrawlTaskEvent<T>> listener);
	
	void addOnPageCrawlingStartListener(IEventListener<CrawlTaskEvent<T>> listener);
	
	/**
	 * Metoda przetwarzająca stronę
	 */
	void parsePage() throws Exception;
	
	/**
	 * Metoda zwraca sotrone zgloszona do zadania
	 * @return
	 */
	PageWrapper<T> getPage();
}
