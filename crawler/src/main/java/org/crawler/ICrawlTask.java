package org.crawler;

import java.util.concurrent.Callable;

import org.crawler.imp.PageWrapper;

/**
 * Interfejs crawlera
 * @author Marcin
 *
 */
public interface ICrawlTask<T> extends Callable<T>   {
	
	/**
	 * Metoda inicjuje zadanie do wykonania
	 * @param webCrawler crawler, który ma wykonać zadanie
	 */
	void init(IWebCrawler<T> webCrawler);
	
	/**
	 * Metoda dodaje obserwatora do zdarzeń crawlera
	 * @param listener
	 */
	void addCrawTaskListener(ICrawlTaskCallback<T> listener);

	/**
	 * Metoda przetwarzająca stronę
	 */
	void parsePage() throws Exception;
	
	/**
	 * Metoda zwraca sotrone zgloszona do zadania
	 * @return
	 */
	PageWrapper<T> getPage();
}
