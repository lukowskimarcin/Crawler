package org.crawler;

/**
 * Klasa bazowa dla Crawlera
 *
 * @param <TPage> Typ obslugiwanej strony
 */
public abstract class Crawler<TPage extends Page<?>> implements ICrawler {
	private final static long WAITING_FOR_PAGE_TIMEOUT = 1000;
	
	private Manager manager;
	private CrawlerStatus status;
	protected TPage currentPage;
	
	public Crawler(Manager manager) {
		this.manager = manager;
		status = CrawlerStatus.STOPED;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			do {
				currentPage = (TPage) manager.fetch(this);
				if(currentPage==null) {
					//Brak strony do przetwarzania czekaj 
					status = CrawlerStatus.WAITING_FOR_PAGE;
					Thread.sleep(WAITING_FOR_PAGE_TIMEOUT);
				} else {
					//Zacznij przetwarzac stronę
					status = CrawlerStatus.PROCESSING_PAGE;
					processPage();
					
					
				}
			} while (status != CrawlerStatus.STOPED);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public abstract boolean canParsePage(Page<?> page);
	
	public TPage getCurrentPage() {
		return currentPage;
	}
	
	public CrawlerStatus getStatus() {
		return status;
	}
	
}
