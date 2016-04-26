package org.crawler;

import org.crawler.imp.Page;

public interface IWebCrawler {

	
	boolean isVisited(Page<?> page);
	
	void addVisited(Page<?> page);
	
	
	
}
