package org.crawler.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.crawler.ICrawlingCallback;
import org.crawler.IWebCrawler;

/**
 * Klasa  zarządzająca porocesem przetwarzania stron
 * @author Marcin
 *
 */
public class WebCrawler<T> implements IWebCrawler<T>   {
	 
	//Strony już przetworzone
	private final Set<String> visitedPages = Collections.synchronizedSet(new HashSet<String>());
	 
	private List<PageWrapper<T>> errorPages = new ArrayList<PageWrapper<T>>();
	
	private final List<PageWrapper<T>> completePages = Collections.synchronizedList(new ArrayList<PageWrapper<T>>());
	
	private ICrawlingCallback<T> callback;
	
	private ProxyManager proxyManager;
	
	private ForkJoinPool pool;
	
	
	public WebCrawler( ) {
		pool =  new ForkJoinPool();
	}
	
	public void shutdown() {
		pool.shutdown();
	}
	
	/**
	 * Tworzy Managera z zdefiniowanym obserwatorem
	 * @param callback : obserwator zdarzeń
	 */
	public WebCrawler(ICrawlingCallback<T> callback) {
		pool =  new ForkJoinPool();
		this.callback = callback;
	}
	
	public ICrawlingCallback<T> getCrawlingListener() {
		return callback;
	}
	
	public void addTask(CrawlTask<T> task) {
		task.init(this);
		pool.invoke(task); 
	}
	
	@Override
	public boolean start(CrawlTask<T> rootTask) {
		addTask(rootTask);
		return pool.awaitQuiescence(120, TimeUnit.SECONDS);
	}
	
	public boolean isVisited(PageWrapper<T> page) {
		boolean result = visitedPages.contains(page.getUrl());
		return result;
	}

	public synchronized void addVisited(PageWrapper<T> page) {
		visitedPages.add(page.getUrl());
	}
	
	/**
	 * Dodaje błędnie przetworzoną stronę
	 * @param page : strona
	 */
	public synchronized void addErrorPage(PageWrapper<T> page) {
		errorPages.add(page);
	}
	
	/**
	 * Lista błędnie przetworzonych stron
	 * @return
	 */
	public List<PageWrapper<T>> getErrorPages(){
		return errorPages;
	}
	
	public ProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public ICrawlingCallback<T> getCallback() {
		return callback;
	}
	
	public synchronized void addCompletePage(PageWrapper<T> page) {
		completePages.add(page);
	}
	
	@Override
	public List<PageWrapper<T>> getCompletePage() {
		return completePages;
	}
}
