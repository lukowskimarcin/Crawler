package org.crawler;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;

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
	
	BlockingQueue<CrawlTask> getCompletePages();
	
	BlockingQueue<CrawlTask> getErrorPages();
	
	BlockingQueue<CrawlTask> getProcesingPages();
	
	BlockingQueue<CrawlTask> getRejectedTasks();
	
	void waitUntilFinish();
	
	void cancel();
	
	void addOnCrawlingChangeStateListener(IEventListener<WebCrawlerEvent> listener);

	void addOnTaskRejectedListener(IEventListener<CrawlTask> listener);
	
	void addOnCrawlingFinishedListener(IEventListener<WebCrawlerEvent> listener);
	
}