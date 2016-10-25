package org.crawler;

import java.util.concurrent.Future;

import org.crawler.imp.PageWrapper;

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
	void init(IWebCrawler webCrawler);
	
	/**
	 * Metoda przetwarzająca stronę
	 */
	void parsePage() throws Exception;
	
	/**
	 * Metoda zwraca sotrone zgloszona do zadania
	 * @return
	 */
	PageWrapper getPage();
}
