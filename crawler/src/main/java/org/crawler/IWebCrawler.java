package org.crawler;

import java.util.Collection;
import java.util.List;

import org.crawler.events.WebCrawlerEvent;
import org.crawler.imp.CrawlTask;
import org.crawler.imp.PageTask;

public interface IWebCrawler extends ICrawlTaskListener {

	/**
	 * Metoda przekazuje zadanie do puli do wykonania
	 * @param task:	
	 */
	void addTask(PageTask page);
	
	void addTask(Collection<PageTask> pages);
	
	void shutdown();
	
	void shutdownAndAwaitTermination(); 
	
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