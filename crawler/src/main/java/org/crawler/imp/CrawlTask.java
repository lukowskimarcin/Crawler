package org.crawler.imp;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.crawler.IEventListener;
import org.crawler.events.CrawlTaskEvent;

public class CrawlTask implements Runnable, Serializable    {
	private static final long serialVersionUID = 148486884050800551L;

	private static final Logger log = Logger.getLogger(CrawlTask.class.getName());   
	
	protected WebCrawler webCrawler;
	protected PageTask page;
	
	//Poczatek przetwarzania
	private long startTime;
	
	//Koniec przetwarzania
	private long endTime;
	
	public CrawlTask(PageTask page) {
		this.page = page;
	}
	
	public void init(WebCrawler webCrawler) {
		this.webCrawler = webCrawler;
	}
	
	@Override
	public void run() {
		try {
			if(!webCrawler.isVisited(page)) {
				webCrawler.addVisited(page);
				
				fireOnPageCrawlingStartEvent();
				
				page.parse();
				
				fireOnPageCrawlingCompletedEvent();
			} else {
				fireOnAlreadyVisitedEvent();
			}
		}catch (Exception ex) {
			fireOnPageCrawlingFailedEvent(ex);
		}
	}
	
	private void fireEvent(List<IEventListener<CrawlTaskEvent>> listeners) {
		if(listeners!=null) {
			CrawlTaskEvent event = new CrawlTaskEvent(this, page);
			
			for(IEventListener<CrawlTaskEvent> callback : listeners){
				callback.handle(event);
			}
		}
	}
	
	private void fireEvent(List<IEventListener<CrawlTaskEvent>> listeners, CrawlTaskEvent event ) {
		if(listeners!=null) {
			for(IEventListener<CrawlTaskEvent> callback : listeners){
				callback.handle(event);
			}
		}
	}
	
	protected void fireOnPageProcessingProgressEvent(double progress) {
		CrawlTaskEvent event = new CrawlTaskEvent(this, page);
		event.setProgress(progress);
		fireEvent(webCrawler.getOnPageProcessingProgressListener(), event);
	}
	
	protected void fireOnAlreadyVisitedEvent() {
		fireEvent(webCrawler.getOnAlreadyVisitedListener());
	}
	
	protected void fireOnPageCrawlingStartEvent(){
		startTime = System.currentTimeMillis();
		fireEvent(webCrawler.getOnPageCrawlingStartListener());
		webCrawler.addProcessingPage(page);
	}
	
	protected void fireOnPageCrawlingCompletedEvent(){
		endTime = System.currentTimeMillis();
		
		CrawlTaskEvent event = new CrawlTaskEvent(this, page, endTime-startTime);
		fireEvent(webCrawler.getOnPageCrawlingCompletedListener(), event);
		 
		webCrawler.addCompletePage(page);
	}
	
	protected void fireOnPageCrawlingFailedEvent(Exception ex){
		log.log(Level.SEVERE, "fireOnPageCrawlingFailedEvent", ex);	
		
		CrawlTaskEvent event = new CrawlTaskEvent(this, page);
		event.setErrorMessage(ex.getMessage());
		fireEvent(webCrawler.getOnPageCrawlingFailedListener(), event);
		 
		webCrawler.addErrorPage(page);
	}

}
