package org.crawler;


/**
 * Interfejs crawlera
 * @author Marcin
 *
 */
public interface ICrawlTask  {
	
	/**
	 * Metoda dodaje obserwatora do zdarzeń crawlera
	 * @param listener
	 */
	void addCrawlingListener(ICrawlingCallback listener);

	/**
	 * Metoda przetwarzająca stronę
	 */
	void processPage();
}
