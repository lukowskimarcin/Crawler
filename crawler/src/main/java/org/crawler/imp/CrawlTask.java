package org.crawler.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.crawler.ICrawlTask;
import org.crawler.ICrawlingCallback;
import org.crawler.IWebCrawler;

/**
 * Zadanie parsowania strony
 *
 * @param <TPage> Typ obslugiwanej strony
 */
public abstract class CrawlTask<T extends Page<?>> implements ICrawlTask<T>   {
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
	 
	public abstract void parsePage();
	
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
		return null;
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
	}
	
	protected void fireOnPageCrawlingCompletedEvent(){
	}
	
	protected void fireOnPageCrawlingFailedEvent(Exception ex){
	}
	
	protected void fireOnAlreadyVisitedEvent() {
	}

	protected void fireOnPageProcessingProgressEvent() {
	}

	
	
 
	
}
