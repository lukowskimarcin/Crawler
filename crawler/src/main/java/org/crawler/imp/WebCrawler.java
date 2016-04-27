package org.crawler.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.crawler.ICrawlTask;
import org.crawler.ICrawlingCallback;
import org.crawler.IWebCrawler;

/**
 * Klasa  zarządzająca porocesem przetwarzania stron
 * @author Marcin
 *
 */
public class WebCrawler<T extends Page<?>> implements IWebCrawler<T>  {
	 
	//Strony już przetworzone
	private HashSet<T> visitedPages = new HashSet<T>(); 
	private List<T> errorPages = new ArrayList<T>();
	
	private ICrawlingCallback<T> callback;
	
	private ProxyManager proxyManager;
	
	private List<Future<T>> pagesTask = new ArrayList<Future<T>>();
	
	private ExecutorService pool;
	
	public WebCrawler( ) {
		pool =  Executors.newFixedThreadPool(20);
	}
	
	/**
	 * Tworzy Managera z zdefiniowanym obserwatorem
	 * @param callback : obserwator zdarzeń
	 */
	public WebCrawler(ICrawlingCallback<T> callback) {
		pool =  Executors.newFixedThreadPool(20);
		this.callback = callback;
	}
	
	public ICrawlingCallback<T> getCrawlingListener() {
		return callback;
	}
	
	public void submitCrawlTask(ICrawlTask<T> task) {
		task.init(this);
		Future<T> future = 	pool.submit(task);
		pagesTask.add(future);
	}
	
	public boolean isVisited(T page) {
		return visitedPages.contains(page);
	}

	public synchronized void addVisited(T page) {
		visitedPages.add(page);
	}
	
	/**
	 * Dodaje błędnie przetworzoną stronę
	 * @param page : strona
	 */
	public synchronized void addErrorPage(T page) {
		errorPages.add(page);
	}
	
	/**
	 * Lista błędnie przetworzonych stron
	 * @return
	 */
	public List<T> getErrorPages(){
		return errorPages;
	}
	
	public ProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public List<Future<T>> getPagesTask() {
		return pagesTask;
	}
	
	public ICrawlingCallback<T> getCallback() {
		return callback;
	}
}
