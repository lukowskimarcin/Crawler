package org.crawler;

/**
 * Interfejs crawlera
 * @author Marcin
 *
 */
public interface ICrawler extends Runnable {
	
	/**
	 * Metoda sprawdza czy dana strona może być obsłużona przez danego crawlera
	 * @param page
	 * @return
	 */
	boolean canParsePage(Page<?> page);
	
	/**
	 * Metoda dodaje obserwatora do zdarzeń crawlera
	 * @param listener
	 */
	void addCrawlingListener(ICrawlingCallback listener);
	
	/**
	 * Przetwarzanie danych strony
	 */
	void processPage();
	
}
