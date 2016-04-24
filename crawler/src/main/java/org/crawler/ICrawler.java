package org.crawler;

import java.util.concurrent.Callable;

import org.crawler.imp.Page;

/**
 * Interfejs crawlera
 * @author Marcin
 *
 */
public interface ICrawler<T extends Page<?>> extends Callable<T> {
	
	/**
	 * Metoda sprawdza czy dana strona może być obsłużona przez danego crawlera
	 * @param page
	 * @return
	 */
	boolean canParsePage(Page page);
	
	/**
	 * Metoda dodaje obserwatora do zdarzeń crawlera
	 * @param listener
	 */
	void addCrawlingListener(ICrawlingCallback listener);

	
	T processPage();
}
