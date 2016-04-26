package org.crawler.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import org.apache.commons.lang.NotImplementedException;
import org.crawler.ICrawlTask;
import org.crawler.ICrawlingCallback;
import org.crawler.IWebCrawler;

/**
 * Zadanie parsowania strony
 *
 * @param <TPage> Typ obslugiwanej strony
 */
public  abstract  class CrawlTask<T extends Page<?>> extends RecursiveAction implements ICrawlTask<T>   {
	private static final long serialVersionUID = 7715097372705014994L;

	private List<ICrawlingCallback<T>> callbacks;
	
	protected IWebCrawler<T> webCrawler;
	protected T page;
	
	public CrawlTask(IWebCrawler<T> webCrawler, T page) {
		this.webCrawler = webCrawler;
		this.page = page;
	}
	
	public CrawlTask(IWebCrawler<T> webCrawler, T page, ICrawlingCallback<T> listener) {
		this(webCrawler, page);
		addCrawlingListener(listener);
	}
	 
	public abstract void processPage();

	@Override
	protected void compute() {
		try {
			
		
			if(!webCrawler.isVisited(page)) {
				webCrawler.addVisited(page);
				
				fireOnPageCrawlingStartEvent();
				processPage();	
				
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
	
	public T getPage() { 
		return page;
	}
	
	protected void fireOnPageCrawlingStartEvent(){
		throw new NotImplementedException();
	}
	
	protected void fireOnPageCrawlingCompletedEvent(){
		throw new NotImplementedException();
	}
	
	protected void fireOnPageCrawlingFailedEvent(Exception ex){
		throw new NotImplementedException();
	}
	
	protected void fireOnAlreadyVisitedEvent() {
		throw new NotImplementedException();
	}

	protected void fireOnPageProcessingProgressEvent() {
		throw new NotImplementedException();
	}
	
 
	
}
