package org.crawler;

public class Crawler<TPage extends Page<?>> implements ICrawler{
	private Manager manager;
	private TPage currentPage;

	public boolean canParsePage(Page<?> page) {
		
		 
		System.out.println(		page.getClass().getName() );
		
		
		
		return true;
	}
	
	 
	
}
