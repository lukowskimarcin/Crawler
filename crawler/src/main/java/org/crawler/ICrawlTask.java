package org.crawler;

import java.util.concurrent.Callable;

import org.crawler.imp.Page;

/**
 * Interfejs crawlera
 * @author Marcin
 *
 */
public interface ICrawlTask<T extends Page<?>> extends Callable<T> {
	
	/**
	 * Metoda inicjuje zadanie do wykonania
	 * @param webCrawler crawler, który ma wykonać zadanie
	 */
	void init(IWebCrawler<T> webCrawler);
	
	/**
	 * Metoda dodaje obserwatora do zdarzeń crawlera
	 * @param listener
	 */
	void addCrawlingListener(ICrawlingCallback<T> listener);

	/**
	 * Metoda przetwarzająca stronę
	 */
	void parsePage();
	
	/**
	 * Metoda zwraca sotrone zgloszona do zadania
	 * @return
	 */
	T getPage();
}
