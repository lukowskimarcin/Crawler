package org.crawler.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

import org.apache.commons.lang.NotImplementedException;
import org.crawler.ICrawlTask;
import org.crawler.ICrawlingCallback;
import org.crawler.IWebCrawler;

/**
 * Zadanie parsowania strony
 *
 * @param <TPage> Typ obslugiwanej strony
 */
public abstract  class CrawlTask<T extends Page<?>> extends RecursiveAction implements ICrawlTask   {
	private List<ICrawlingCallback> callbacks;
	
	protected IWebCrawler webCrawler;
	protected T page;
	
	public CrawlTask(IWebCrawler webCrawler, T page) {
		this.webCrawler = webCrawler;
		this.page = page;
	}
	
	public CrawlTask(IWebCrawler webCrawler, T page, ICrawlingCallback listener) {
		this(webCrawler, page);
		addCrawlingListener(listener);
	}
	
	public abstract void processPage();

	@Override
	protected void compute() {
		try {
			fireOnPageCrawlingStartEvent();
		
			processPage();
		
			fireOnPageCrawlingCompletedEvent();
		}catch (Exception ex) {
			fireOnPageCrawlingFailedEvent(ex);
		}
	}

	 
	public void addCrawlingListener(ICrawlingCallback listener) {
		if(callbacks==null) {
			callbacks = new ArrayList<ICrawlingCallback>();
		}
		callbacks.add(listener);
	}
	
	private void fireOnPageCrawlingStartEvent(){
		throw new NotImplementedException();
	}
	
	private void fireOnPageCrawlingCompletedEvent(){
		throw new NotImplementedException();
	}
	
	private void fireOnPageCrawlingFailedEvent(Exception ex){
		throw new NotImplementedException();
	}
 
	
}
