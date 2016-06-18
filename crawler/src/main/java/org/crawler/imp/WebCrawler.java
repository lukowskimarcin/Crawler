package org.crawler.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.crawler.ICrawlingCallback;
import org.crawler.IWebCrawler;

/**
 * Klasa  zarządzająca porocesem przetwarzania stron
 * @author Marcin
 *
 */
public class WebCrawler<T> implements IWebCrawler<T>   {
	
	private static final Logger log = Logger.getLogger(WebCrawler.class.getName());   
	 
	//Strony już przetworzone
	private final Set<String> visitedPages = Collections.synchronizedSet(new HashSet<String>());
	 
	private final List<PageWrapper<T>> errorPages = Collections.synchronizedList(new ArrayList<PageWrapper<T>>());
	
	private final List<PageWrapper<T>> completePages = Collections.synchronizedList(new ArrayList<PageWrapper<T>>());
	
	private final List<PageWrapper<T>> processingPages = Collections.synchronizedList(new ArrayList<PageWrapper<T>>());
	
	private final List<Future<T>> futures = Collections.synchronizedList(new ArrayList<Future<T>>());
	
	private ICrawlingCallback<T> callback;
	
	private ProxyManager proxyManager;
	
	private ExecutorService pool;
	
//	private Object lock = new Object();
	
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
	
	@Override
	public void addTask(CrawlTask<T> task) {
		task.init(this, counter);
		try {
			Future<T> future = pool.submit(task);
			futures.add(future);
		}catch (RejectedExecutionException ex) {
			fireOnTaskRejected(task);
		}
		
	}
	
	@Override
	public void addTask(Collection<CrawlTask<T>> tasks){
		try {
			for(CrawlTask<T> task : tasks) {
				task.init(this, counter);
			}
			futures.addAll(pool.invokeAll(tasks));
		} catch (InterruptedException ex) {
			log.log(Level.SEVERE, "start", ex);
		}
	}
	
	protected void fireOnTaskRejected(CrawlTask<T> task){
		log.log(Level.WARNING, "Task rejected: " + task.getPage().getUrl());
	}
	
	
	@Override
	public void waitUntilFinish() {
		try {
			synchronized (this) {
				this.wait();
			}
		} catch (InterruptedException ex) {
			log.log(Level.SEVERE, "waitUntilFinish", ex);
		}
	}
	
	@Override
	public void cancel() {
		for(Future<T> f : futures) {
			f.cancel(false);
		}
		pool.shutdown();
	}
	
	
	public boolean isVisited(PageWrapper<T> page) {
		boolean result = visitedPages.contains(page.getUrl());
		return result;
	}

	public synchronized void addVisited(PageWrapper<T> page) {
		visitedPages.add(page.getUrl());
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
	
	public synchronized void addErrorPage(PageWrapper<T> page) {
		processingPages.remove(page);
		errorPages.add(page);
	}
	
	@Override
	public List<PageWrapper<T>> getErrorPages(){
		return errorPages;
	}
	
	public synchronized void addCompletePage(PageWrapper<T> page) {
		processingPages.remove(page);
		completePages.add(page);
	}
	
	@Override
	public List<PageWrapper<T>> getCompletePages() {
		return completePages;
	}

	public synchronized void addProcessingPage(PageWrapper<T> page) {
		processingPages.add(page);
	}
	
	@Override
	public List<PageWrapper<T>> getProcesingPages() {
		return processingPages;
	}
	
}
