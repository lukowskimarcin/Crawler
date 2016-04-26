package org.crawler.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import org.crawler.ICrawlTask;
import org.crawler.ICrawlingCallback;

/**
 * Klasa  zarządzająca porocesem przetwarzania stron
 * @author Marcin
 *
 */
public class WebCrawler  {
	
	//Strony do przetworzenia
	private BlockingQueue< Page<?> > notProcessedPages = new LinkedBlockingQueue<Page<?> >();
	
	//Strony już przetworzone
	private BlockingQueue< Page<?> > processedPages = new LinkedBlockingQueue<Page<?> >();
	
	private BlockingQueue< Page<?> > errorPages = new LinkedBlockingQueue<Page<?> >();
	
	//Lista zadań
	private List<ICrawlTask> crawlers = new ArrayList<ICrawlTask>();
	
	private ICrawlingCallback callback;
	
	private ProxyManager proxyManager;
	
	private  ExecutorService pool = Executors.newFixedThreadPool(10);

	
	/**
	 * Tworzy Managera z zdefiniowanym obserwatorem
	 * @param callback : obserwator zdarzeń
	 */
	public WebCrawler(ICrawlingCallback callback) {
		this.callback = callback;
	}
	
	/**
	 * Dodaje crawlera. Jeśli jest zdefiniowany globalny 
	 * obserwator zdarzeń, to przekazuje go do crawlera
	 * @param crawler
	 */
	public void addCrawler(ICrawlTask crawler) {
		crawlers.add(crawler);
		//Dodanie globalnego listenera crawlerów
		if(callback != null) {
			crawler.addCrawlingListener(callback);
		}
		
	}
	 
	
	
	/**
	 * Dodaje poprawnie przetworzoną stronę
	 * @param page : strona
	 */
	public void addProcessedPage(Page<?> page) {
		processedPages.add(page);
	}
	
	/**
	 * Lista poprawnie przetworzonych stron
	 * @return
	 */
	public BlockingQueue<Page<?>> getProcessedPages(){
		return processedPages;
	}
	
	/**
	 * Dodaje błędnie przetworzoną stronę
	 * @param page : strona
	 */
	public void addErrorPage(Page<?> page) {
		errorPages.add(page);
	}
	
	/**
	 * Lista błędnie przetworzonych stron
	 * @return
	 */
	public BlockingQueue<Page<?>> getErrorPages(){
		return errorPages;
	}
	
	public ProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
}
