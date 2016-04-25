package org.crawler;

import java.util.ArrayList;
import java.util.List;

import org.crawler.imp.Manager;
import org.crawler.imp.PageTask;
import org.crawler.imp.Proxy;
import org.crawler.imp.ProxyManager;

/**
 * Klasa bazowa dla Crawlera
 *
 * @param <TPage> Typ obslugiwanej strony
 */
public abstract class Crawler<T extends Page<?>> implements ICrawler<T>   {
	private Manager manager;
	private List<ICrawlingCallback> callbacks;
	private T pageResult;
	
	public Crawler(Manager manager) {
		this.manager = manager;
	}
	
	public Crawler(Manager manager, ICrawlingCallback listener) {
		this(manager);
		addCrawlingListener(listener);
	}
	
	public abstract boolean canParsePage(PageTask<?> page);
	
	public abstract T processPage();
	
	/**
	 * Urochomienie zadania przetworzenia strony
	 */
	public T call() throws Exception {
		try {
			fireOnPageCrawlingStartEvent();
		
			pageResult = processPage();
		
			fireOnPageCrawlingCompletedEvent();
		}catch (Exception ex) {
			fireOnPageCrawlingFailedEvent(ex);
		}
		return null;
	}
	
	
	public void addCrawlingListener(ICrawlingCallback listener) {
		if(callbacks==null) {
			callbacks = new ArrayList<ICrawlingCallback>();
		}
		callbacks.add(listener);
	}
	
	private void fireOnPageCrawlingStartEvent(){
		if(callbacks!=null) {
			for(ICrawlingCallback callback : callbacks) {
				callback.onPageCrawlingStart(this, null);
			}
		}
	}
	
	private void fireOnPageCrawlingCompletedEvent(){
		manager.addProcessedPage(null);
		if(callbacks!=null) {
			for(ICrawlingCallback callback : callbacks) {
				callback.onPageCrawlingCompleted(this, null);
			}
		}
	}
	
	private void fireOnPageCrawlingFailedEvent(Exception ex){
		pageResult.setErrorMessage(ex.getMessage());
		
		if(callbacks!=null) {
			for(ICrawlingCallback callback : callbacks) {
				callback.onPageCrawlingFailed(this, null);
			}
		}
	}
	 
	
	/**
	 * Pobiera losow proxy o ile są dostępne 
	 * @return
	 */
	public Proxy getProxy(){
		ProxyManager proxyManager = manager.getProxyManager();
		if(proxyManager != null) {
			return proxyManager.getProxy();
		}
		return null;
	}
	
}
