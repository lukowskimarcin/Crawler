package org.crawler;

/**
 * 
 *
 * @param <TPage>
 */
public abstract class Crawler<TPage extends Page<?>> implements ICrawler{
	private Manager manager;
	private TPage currentPage;
	private CrawlerStatus status;
	
	public Crawler(Manager manager) {
		this.manager = manager;
		status = CrawlerStatus.STOPED;
	}
	
	public abstract boolean canParsePage(Page<?> page);
	
	
	
	
	public TPage getCurrentPage() {
		return currentPage;
	}
	
}
