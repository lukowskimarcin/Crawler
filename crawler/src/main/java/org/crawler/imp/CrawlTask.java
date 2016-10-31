package org.crawler.imp;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.crawler.IEventListener;
import org.crawler.IWebCrawler;
import org.crawler.events.CrawlTaskEvent;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Przetwarzana strona
 * @author Marcin
 *
 */
public abstract class CrawlTask implements Runnable, Serializable {
	private static final long serialVersionUID = 6857632664456273402L;
	private static final Logger log = Logger.getLogger(CrawlTask.class.getName());   
	
	@Attribute
	private String url;
	
	@Element(required=false)
	private String errorMessage;
	
	@Attribute
	private int level;

	@Element(required=false)
	private Object data;
	
	//Poczatek przetwarzania
	private long startTime;
		
	//Koniec przetwarzania
	private long endTime;

	protected WebCrawler webCrawler;
	
	public CrawlTask(String url) {
		this.url = url;
		this.level = 0;
	}
	
	public CrawlTask(String url,   int level) {
		this.url = url;
		this.level = level;
	}
	
	public void init(WebCrawler crawler) {
		this.webCrawler = crawler;
	}
	
	
	@Override
	public int hashCode() {
		return url.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		CrawlTask second = (CrawlTask) obj;
		return  url.equals(second.getUrl());
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public WebCrawler getWebCrawler() {
		return webCrawler;
	}

	 
	/**
	 * Przetwarzanie danych strony po pobraniu
	 */
	public abstract void process();  
	
	
	/**
	 * Pobieranie danych ze strony
	 */
	public abstract void parse() throws Exception;
	
	
	@Override
	public void run() {
		try {
			if(!webCrawler.isVisited(this)) {
				webCrawler.addVisited(this);
				
				fireOnPageCrawlingStartEvent();
				
				parse();
				
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
			CrawlTaskEvent event = new CrawlTaskEvent(this);
			
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
		CrawlTaskEvent event = new CrawlTaskEvent(this);
		event.setProgress(progress);
		fireEvent(webCrawler.getOnPageProcessingProgressListener(), event);
	}
	
	protected void fireOnAlreadyVisitedEvent() {
		fireEvent(webCrawler.getOnAlreadyVisitedListener());
	}
	
	protected void fireOnPageCrawlingStartEvent(){
		startTime = System.currentTimeMillis();
		fireEvent(webCrawler.getOnPageCrawlingStartListener());
		webCrawler.addProcessingPage(this);
	}
	
	protected void fireOnPageCrawlingCompletedEvent(){
		endTime = System.currentTimeMillis();
		
		CrawlTaskEvent event = new CrawlTaskEvent(this, endTime-startTime);
		fireEvent(webCrawler.getOnPageCrawlingCompletedListener(), event);
		 
		webCrawler.addCompletePage(this);
	}
	
	protected void fireOnPageCrawlingFailedEvent(Exception ex){
		log.log(Level.SEVERE, "fireOnPageCrawlingFailedEvent", ex);	
		
		CrawlTaskEvent event = new CrawlTaskEvent(this);
		event.setErrorMessage(ex.getMessage());
		fireEvent(webCrawler.getOnPageCrawlingFailedListener(), event);
		 
		webCrawler.addErrorPage(this);
	}

	

	@Override
	public String toString() {
		return "Page [" +
				"\n\turl: " + url +
				"\n\tlevel: " + level +
				"\n\terrorMessage: " + errorMessage + 
				"\n\tdata: " + data + "\n]";
	}
}
