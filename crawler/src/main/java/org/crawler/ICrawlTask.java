package org.crawler;

import org.crawler.imp.Page;

/**
 * Interfejs crawlera
 * @author Marcin
 *
 */
public interface ICrawlTask<T extends Page<?>>  {
	
	/**
	 * Metoda dodaje obserwatora do zdarzeń crawlera
	 * @param listener
	 */
	void addCrawlingListener(ICrawlingCallback<T> listener);

	/**
	 * Metoda przetwarzająca stronę
	 */
	void processPage();
	
	T getPage();
}
