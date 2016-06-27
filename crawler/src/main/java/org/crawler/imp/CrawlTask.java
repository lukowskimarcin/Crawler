package org.crawler.imp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.crawler.ICrawlTask;
import org.crawler.ICrawlTaskCallback;
import org.crawler.IWebCrawler;
import org.crawler.events.CrawlTaskEvent;

/**
 * Zadanie parsowania strony
 *
 * @param <TPage> Typ obslugiwanej strony
 */
public abstract class CrawlTask<T>  implements ICrawlTask<T>, Serializable   {
	private static final long serialVersionUID = 148486884050800551L;

	private static final Logger log = Logger.getLogger(CrawlTask.class.getName());   
	
	private List<ICrawlTaskCallback<T>> callbacks;
	
	protected IWebCrawler<T> webCrawler;
	protected PageWrapper<T> page;
	
	//Poczatek przetwarzania
	private long startTime;
	
	//Koniec przetwarzania
	private long endTime;
	
	public CrawlTask(String url) {
		this.page = new PageWrapper<>(url);
	}
	
	public CrawlTask(PageWrapper<T> page, ICrawlTaskCallback<T> listener) {
		this.page = page;
		addCrawTaskListener(listener);
	}
	 
	public abstract void parsePage() throws Exception;
	
	public void init(IWebCrawler<T> webCrawler) {
		this.webCrawler = webCrawler;
		
		ICrawlTaskCallback<T> callback = webCrawler.getDefaultCrawlTaskListener();
		if(callback!=null) {
			addCrawTaskListener(callback);
		}
	}
	
	@Override
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
		return page.getData();
	}
	 
	public void addCrawTaskListener(ICrawlTaskCallback<T> listener) {
		if(listener != null) {
			if(callbacks==null) {
				callbacks = new ArrayList<ICrawlTaskCallback<T>>();
			}
			callbacks.add(listener);
		}
	}
	
	public PageWrapper<T> getPage() { 
		return page;
	}
	
	protected void fireOnPageCrawlingStartEvent(){
		startTime = System.currentTimeMillis();
		
		if(callbacks!=null) {
			for(ICrawlTaskCallback<T> callback : callbacks){
				callback.onPageCrawlingStart(new CrawlTaskEvent<T>(this, page));
			}
		}
		webCrawler.addProcessingPage(page);
	}
	
	protected void fireOnPageCrawlingCompletedEvent(){
		endTime = System.currentTimeMillis();
		
		if(callbacks!=null) {
			CrawlTaskEvent<T> event = new CrawlTaskEvent<T>(this, page, endTime-startTime);
			for(ICrawlTaskCallback<T> callback : callbacks){
				callback.onPageCrawlingCompleted(event);
			}
		}
		webCrawler.addCompletePage(page);
	}
	
	protected void fireOnPageCrawlingFailedEvent(Exception ex){
		log.log(Level.SEVERE, "fireOnPageCrawlingFailedEvent", ex);	
		if(callbacks!=null) {
			CrawlTaskEvent<T> event = new CrawlTaskEvent<T>(this, page);
			event.setErrorMessage(ex.getMessage());
			
			for(ICrawlTaskCallback<T> callback : callbacks){
				callback.onPageCrawlingFailed(event);
			}
		}
		webCrawler.addErrorPage(page);
	}
	
	protected void fireOnAlreadyVisitedEvent() {
		if(callbacks!=null) {
			CrawlTaskEvent<T> event = new CrawlTaskEvent<T>(this, page);
			for(ICrawlTaskCallback<T> callback : callbacks){
				callback.onAlreadyVisited(event);
			}
		}
	}

	protected void fireOnPageProcessingProgressEvent(int percent) {
		if(callbacks!=null) {
			CrawlTaskEvent<T> event = new CrawlTaskEvent<T>(this, page);
			event.setPercent(percent);
			
			for(ICrawlTaskCallback<T> callback : callbacks){
				callback.onPageProcessingProgress(event);
			}
		}
	}
}
