package org.crawler.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.xml.utils.ThreadControllerWrapper;
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
	
	private ExecutorService pool;
	
	private AtomicInteger counter;
	
	public WebCrawler( ) {
		pool =  Executors.newFixedThreadPool(10);
		counter = new AtomicInteger(0);
	}
	
	/**
	 * Tworzy Managera z zdefiniowanym obserwatorem
	 * @param callback : obserwator zdarzeń
	 */
	public WebCrawler(ICrawlingCallback<T> callback) {
		pool =  Executors.newFixedThreadPool(10);
		counter = new AtomicInteger(0);
		this.callback = callback;
	}
	
	public void shutdown() {
		pool.shutdown();
	}
	
	public void shutdownAndAwaitTermination() {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}
	
	
	
	
	public ICrawlingCallback<T> getCrawlingListener() {
		return callback;
	}
	
	public void addTask(CrawlTask<T> task) {
		task.init(this, counter);
		Future<T> future = pool.submit(task);
	}
	
	@Override
	public void start(CrawlTask<T> rootTask, boolean block) {
		addTask(rootTask);
		
		while(block && counter.get() > 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
