package org.crawler.imp;

import java.util.ArrayList;
import java.util.List;

import org.crawler.CrawlerStatus;
import org.crawler.ICrawler;
import org.crawler.ICrawlingCallback;

/**
 * Klasa bazowa dla Crawlera
 *
 * @param <TPage> Typ obslugiwanej strony
 */
public abstract class Crawler<TPage extends Page<?>> implements ICrawler {
	private final static long WAITING_FOR_PAGE_TIMEOUT = 100;
	
	private Manager manager;
	private CrawlerStatus status;
	private List<ICrawlingCallback> callbacks;
	
	protected TPage currentPage;
	
	public Crawler(Manager manager) {
		this.manager = manager;
		status = CrawlerStatus.STOPED;
	}
	
	public Crawler(Manager manager, ICrawlingCallback listener) {
		this(manager);
		addCrawlingListener(listener);
	}
	
	public void addCrawlingListener(ICrawlingCallback listener) {
		if(callbacks==null) {
			callbacks = new ArrayList<ICrawlingCallback>();
		}
		callbacks.add(listener);
	}
	
	private void fireOnPageCrawlingStartEvent(){
		status = CrawlerStatus.PROCESSING_PAGE;
		if(callbacks!=null) {
			for(ICrawlingCallback callback : callbacks) {
				callback.onPageCrawlingStart(this, currentPage);
			}
		}
	}
	
	private void fireOnPageCrawlingCompletedEvent(){
		status = CrawlerStatus.WAITING_FOR_PAGE;
		manager.addProcessedPage(currentPage);
		if(callbacks!=null) {
			for(ICrawlingCallback callback : callbacks) {
				callback.onPageCrawlingCompleted(this, currentPage);
			}
		}
		currentPage = null;
	}
	
	private void fireOnPageCrawlingFailedEvent(Exception ex){
		status = CrawlerStatus.PAGE_ERROR;
		currentPage.setErrorMessage(ex.getMessage());
		
		manager.addErrorPage(currentPage);
		if(callbacks!=null) {
			for(ICrawlingCallback callback : callbacks) {
				callback.onPageCrawlingFailed(this, currentPage);
			}
		}
		currentPage = null;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			do {
				currentPage = (TPage) manager.fetch(this);
				if (currentPage == null) {
					// Brak strony do przetwarzania czekaj
					status = CrawlerStatus.WAITING_FOR_PAGE;
					Thread.sleep(WAITING_FOR_PAGE_TIMEOUT);
				} else {
					try {
						fireOnPageCrawlingStartEvent();
						processPage();
						fireOnPageCrawlingCompletedEvent();
					} catch (Exception e) {
						fireOnPageCrawlingFailedEvent(e);
					}
				}
			} while (status != CrawlerStatus.STOPED);
			
		} catch (InterruptedException ex) {
			status = CrawlerStatus.CRAWLER_ERROR;
			ex.printStackTrace();
		}
	}
	
	public abstract boolean canParsePage(Page<?> page);
	
	public abstract void processPage();
	
	public TPage getCurrentPage() {
		return currentPage;
	}
	
	public CrawlerStatus getStatus() {
		return status;
	}
	
}
