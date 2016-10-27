package org.crawler;

import java.util.Collection;
import java.util.List;

import org.crawler.events.WebCrawlerEvent;
import org.crawler.imp.CrawlTask;

public interface IWebCrawler extends ICrawlTaskListener {

	/**
	 * Metoda przekazuje zadanie do puli do wykonania
	 * @param task:	
	 */
	void addTask(CrawlTask page);
	
	void addTask(Collection<CrawlTask> pages);
	
	void shutdown();
	
	void shutdownAndAwaitTermination(); 
	
	List<CrawlTask> getCompletePages();
	
	List<CrawlTask> getErrorPages();
	
	List<CrawlTask> getProcesingPages();
	
	List<CrawlTask> getRejectedTasks();
	
	void waitUntilFinish();
	
	void cancel();
	
	void addOnCrawlingChangeStateListener(IEventListener<WebCrawlerEvent> listener);

	void addOnTaskRejectedListener(IEventListener<CrawlTask> listener);
	
	void addOnCrawlingFinishedListener(IEventListener<WebCrawlerEvent> listener);
	
}