package org.crawler;

import java.util.concurrent.Callable;

/**
 * Interfejs crawlera
 * @author Marcin
 *
 */
public interface ICrawler<T extends Page<?>> extends Callable<T> {
	
	/**
	 * Metoda dodaje obserwatora do zdarzeń crawlera
	 * @param listener
	 */
	void addCrawlingListener(ICrawlingCallback listener);

	
	T processPage();
}
