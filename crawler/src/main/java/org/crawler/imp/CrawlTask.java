package org.crawler.imp;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.crawler.ICrawlTask;
import org.crawler.IEventListener;
import org.crawler.IWebCrawler;
import org.crawler.events.CrawlTaskEvent;

/**
 * Zadanie parsowania strony
 *
 * @param <TPage> Typ obslugiwanej strony
 */
public abstract class CrawlTask<T> extends CrawlTaskBaseListener<T>  implements ICrawlTask<T>, Serializable    {
	private static final long serialVersionUID = 148486884050800551L;

	private static final Logger log = Logger.getLogger(CrawlTask.class.getName());   
	
	
	
	protected IWebCrawler<T> webCrawler;
	protected PageWrapper<T> page;
	
	//Poczatek przetwarzania
	private long startTime;
	
	//Koniec przetwarzania
	private long endTime;
	
	public CrawlTask(String url) {
		this.page = new PageWrapper<>(url);
	}
	
	public CrawlTask(PageWrapper<T> page) {
		this.page = page;
	}
	 
	public abstract void parsePage() throws Exception;
	
	public void init(IWebCrawler<T> webCrawler) {
		this.webCrawler = webCrawler;
		
		 
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
	
	public PageWrapper<T> getPage() { 
		return page;
	}
	
	protected void fireEvent(List<IEventListener<CrawlTaskEvent<T>>> listeners) {
		if(listeners!=null) {
			CrawlTaskEvent<T> event = new CrawlTaskEvent<T>(this, page);
			
			for(IEventListener<CrawlTaskEvent<T>> callback : listeners){
				callback.handle(event);
			}
		}
	}
	
	protected void fireEvent(List<IEventListener<CrawlTaskEvent<T>>> listeners, CrawlTaskEvent<T> event ) {
		if(listeners!=null) {
			for(IEventListener<CrawlTaskEvent<T>> callback : listeners){
				callback.handle(event);
			}
		}
	}
	
	protected void fireOnPageProcessingProgressEvent(double progress) {
		CrawlTaskEvent<T> event = new CrawlTaskEvent<T>(this, page);
		event.setProgress(progress);
		fireEvent(onPageProcessingProgressListener, event);
	}
	
	protected void fireOnAlreadyVisitedEvent() {
		fireEvent(onPageAlreadyVisitedListener);
	}
	
	protected void fireOnPageCrawlingStartEvent(){
		startTime = System.currentTimeMillis();
		fireEvent(onPageCrawlingStartListener);
		webCrawler.addProcessingPage(page);
	}
	
	protected void fireOnPageCrawlingCompletedEvent(){
		endTime = System.currentTimeMillis();
		
		CrawlTaskEvent<T> event = new CrawlTaskEvent<T>(this, page, endTime-startTime);
		fireEvent(onPageCrawlingStartListener, event);
		 
		webCrawler.addCompletePage(page);
	}
	
	protected void fireOnPageCrawlingFailedEvent(Exception ex){
		log.log(Level.SEVERE, "fireOnPageCrawlingFailedEvent", ex);	
		
		CrawlTaskEvent<T> event = new CrawlTaskEvent<T>(this, page);
		event.setErrorMessage(ex.getMessage());
		fireEvent(onPageCrawlingStartListener, event);
		 
		webCrawler.addErrorPage(page);
	}
}
