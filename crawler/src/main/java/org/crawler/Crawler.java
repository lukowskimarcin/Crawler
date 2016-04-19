package org.crawler;

/**
 * 
 *
 * @param <TPage>
 */
public abstract class Crawler<TPage extends Page<?>> implements ICrawler{
	private Manager manager;
	private TPage currentPage;

	
	public Crawler(Manager manager) {
		this.manager = manager;
	}
	
	public abstract boolean canParsePage(Page<?> page);
	
	
}
