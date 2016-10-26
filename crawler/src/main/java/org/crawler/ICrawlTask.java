package org.crawler;


import org.crawler.imp.WebCrawler;

/**
 * Interfejs crawlera
 * @author Marcin
 *
 */
public interface ICrawlTask  extends Runnable  {
	
	/**
	 * Metoda inicjuje zadanie do wykonania
	 * @param webCrawler crawler, który ma wykonać zadanie
	 */
	void init(WebCrawler webCrawler);
	
}
