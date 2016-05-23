package org.crawler.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
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
public abstract class CrawlTask<T> extends RecursiveAction  implements ICrawlTask<T>   {
	private static final long serialVersionUID = -1936115110237484455L;

	private static final Logger log = Logger.getLogger(CrawlTask.class.getName());   
	
	private List<ICrawlingCallback<T>> callbacks;
	
	protected IWebCrawler<T> webCrawler;
	protected PageWrapper<T> page;
	
	public CrawlTask(PageWrapper<T> page) {
		this.page = page;
	}
	
	public CrawlTask(PageWrapper<T> page, ICrawlingCallback<T> listener) {
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
	
	@Override
	protected void compute()  {
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
		
	}
	 
	public void addCrawlingListener(ICrawlingCallback<T> listener) {
		if(callbacks==null) {
			callbacks = new ArrayList<ICrawlingCallback<T>>();
		}
		callbacks.add(listener);
	}
	
	public PageWrapper<T> getPage() { 
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
		webCrawler.addCompletePage(page);
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
