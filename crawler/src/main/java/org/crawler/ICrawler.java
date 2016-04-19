package org.crawler;

public interface ICrawler extends Runnable {
	
	boolean canParsePage(Page<?> page);
	
	
	void processPage();
	
	
	
	
}
