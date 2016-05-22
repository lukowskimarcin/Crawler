package org.crawler.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.crawler.ICrawlTask;
import org.crawler.ICrawlingCallback;
import org.crawler.IWebCrawler;

/**
 * Zadanie parsowania strony
 *
 * @param <TPage> Typ obslugiwanej strony
 */
public abstract class CrawlTask<T extends PageWrapper<?>> implements ICrawlTask<T>   {
	private static final Logger log = Logger.getLogger(CrawlTask.class.getName());   
	
	private List<ICrawlingCallback<T>> callbacks;
	
	protected IWebCrawler<T> webCrawler;
	protected T page;
	
	public CrawlTask(T page) {
		this.page = page;
	}
	
	public CrawlTask(T page, ICrawlingCallback<T> listener) {
		this(page);
		addCrawlingListener(listener);
	}
	 
	public abstract void parsePage() throws Exception;
	
	public void init(IWebCrawler<T> webCrawler) {
		this.webCrawler = webCrawler;
		ICrawlingCallback<T> callback = webCrawler.getCrawlingListener();
		if(callback!=null) {
			addCrawlingListener(callback);
		}
	}
	
	public T call() throws Exception {
		try {
			if(!webCrawler.isVisited(page)) {
				webCrawler.addVisited(page);
				
				fireOnPageCrawlingStartEvent();
				parsePage();	
				
				fireOnPageCrawlingCompletedEvent();
			} else {
				fireOnAlreadyVisitedEvent();
			}
		
			
		}catch (Exception ex) {
			fireOnPageCrawlingFailedEvent(ex);
		}
		
		webCrawler.isAllTaskComplete();
		return page;
	}
	 
	public void addCrawlingListener(ICrawlingCallback<T> listener) {
		if(callbacks==null) {
			callbacks = new ArrayList<ICrawlingCallback<T>>();
		}
		callbacks.add(listener);
	}
	
	public T getPage() { 
		return page;
	}
	
	protected void fireOnPageCrawlingStartEvent(){
		if(callbacks!=null) {
			for(ICrawlingCallback<T> callback : callbacks){
				callback.onPageCrawlingStart(this, page);
			}
		}
	}
	
	protected void fireOnPageCrawlingCompletedEvent(){
		if(callbacks!=null) {
			for(ICrawlingCallback<T> callback : callbacks){
				callback.onPageCrawlingCompleted(this, page);
			}
		}
	}
	
	protected void fireOnPageCrawlingFailedEvent(Exception ex){
		log.log(Level.SEVERE, "fireOnPageCrawlingFailedEvent", ex);
	}
	
	protected void fireOnAlreadyVisitedEvent() {
		if(callbacks!=null) {
			for(ICrawlingCallback<T> callback : callbacks){
				callback.onAlreadyVisited(this, page);
			}
		}
	}

	protected void fireOnPageProcessingProgressEvent() {
	}

	
	
 
	
}
