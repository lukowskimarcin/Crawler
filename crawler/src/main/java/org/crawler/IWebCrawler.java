package org.crawler;

import java.util.Collection;
import java.util.List;

import org.crawler.events.WebCrawlerEvent;
import org.crawler.imp.PageWrapper;

public interface IWebCrawler<T> extends ICrawlTaskListener<T> {

	/**
	 * Metoda Sprawdza czy strona byla przetwarzana
	 * @param page
	 * @return
	 */
	boolean isVisited(PageWrapper<T> page);
	
	/**
	 * Metoda oznacza stronę jako przetworzoną
	 * @param page
	 */
	void addVisited(PageWrapper<T> page);
	
	/**
	 * Metoda przekazuje zadanie do puli do wykonania
	 * @param task:	
	 */
	void addTask(ICrawlTask<T> task);
	
	void addTask(Collection<ICrawlTask<T>> tasks);
	
	void shutdown();
	
	void shutdownAndAwaitTermination(); 
	
	void addCompletePage(PageWrapper<T> page);
	
	void addProcessingPage(PageWrapper<T> page);
	
	void addErrorPage(PageWrapper<T> page);
	
	List<PageWrapper<T>> getCompletePages();
	
	List<PageWrapper<T>> getErrorPages();
	
	List<PageWrapper<T>> getProcesingPages();
	
	List<ICrawlTask<T>> getRejectedTasks();
	
	void waitUntilFinish();
	
	void cancel();
	
	void addOnCrawlingChangeStateListener(IEventListener<WebCrawlerEvent> listener);
	void addOnTaskRejectedListener(IEventListener<ICrawlTask<T>> listener);
	void addOnCrawlingFinishedListener(IEventListener<WebCrawlerEvent> listener);
	
}