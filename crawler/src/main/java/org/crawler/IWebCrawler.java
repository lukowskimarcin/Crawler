package org.crawler;

import java.util.Collection;
import java.util.List;

import org.crawler.events.WebCrawlerEvent;
import org.crawler.imp.PageWrapper;

public interface IWebCrawler extends ICrawlTaskListener {

	/**
	 * Metoda Sprawdza czy strona byla przetwarzana
	 * @param page
	 * @return
	 */
	boolean isVisited(PageWrapper page);
	
	/**
	 * Metoda oznacza stronę jako przetworzoną
	 * @param page
	 */
	void addVisited(PageWrapper page);
	
	/**
	 * Metoda przekazuje zadanie do puli do wykonania
	 * @param task:	
	 */
	void addTask(ICrawlTask task);
	
	void addTask(Collection<ICrawlTask> tasks);
	
	void shutdown();
	
	void shutdownAndAwaitTermination(); 
	
	void addCompletePage(PageWrapper page);
	
	void addProcessingPage(PageWrapper page);
	
	void addErrorPage(PageWrapper page);
	
	List<PageWrapper> getCompletePages();
	
	List<PageWrapper> getErrorPages();
	
	List<PageWrapper> getProcesingPages();
	
	List<ICrawlTask> getRejectedTasks();
	
	void waitUntilFinish();
	
	void cancel();
	
	void addOnCrawlingChangeStateListener(IEventListener<WebCrawlerEvent> listener);
	void addOnTaskRejectedListener(IEventListener<ICrawlTask> listener);
	void addOnCrawlingFinishedListener(IEventListener<WebCrawlerEvent> listener);
	
}