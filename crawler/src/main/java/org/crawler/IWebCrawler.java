package org.crawler;

import java.util.Collection;
import java.util.List;

import org.crawler.events.WebCrawlerEvent;
import org.crawler.imp.CrawlTask;
import org.crawler.imp.PageTask;

public interface IWebCrawler extends ICrawlTaskListener {

	/**
	 * Metoda Sprawdza czy strona byla przetwarzana
	 * @param page
	 * @return
	 */
	boolean isVisited(PageTask page);
	
	/**
	 * Metoda oznacza stronę jako przetworzoną
	 * @param page
	 */
	void addVisited(PageTask page);
	
	/**
	 * Metoda przekazuje zadanie do puli do wykonania
	 * @param task:	
	 */
	void addTask(PageTask page);
	
	void addTask(Collection<PageTask> pages);
	
	void shutdown();
	
	void shutdownAndAwaitTermination(); 
	
	void addCompletePage(PageTask page);
	
	void addProcessingPage(PageTask page);
	
	void addErrorPage(PageTask page);
	
	List<PageTask> getCompletePages();
	
	List<PageTask> getErrorPages();
	
	List<PageTask> getProcesingPages();
	
	List<CrawlTask> getRejectedTasks();
	
	void waitUntilFinish();
	
	void cancel();
	
	void addOnCrawlingChangeStateListener(IEventListener<WebCrawlerEvent> listener);
	void addOnTaskRejectedListener(IEventListener<CrawlTask> listener);
	void addOnCrawlingFinishedListener(IEventListener<WebCrawlerEvent> listener);
	
}