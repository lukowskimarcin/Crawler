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

import org.crawler.ICrawlTask;
import org.crawler.ICrawlTaskCallback;
import org.crawler.IWebCrawler;
import org.crawler.IWebCrawlerCallback;
import org.crawler.events.WebCrawlerEvent;

/**
 * Klasa  zarządzająca porocesem przetwarzania stron
 * @author Marcin
 *
 */
public class WebCrawler<T> implements IWebCrawler<T>   {
	
	private static final Logger log = Logger.getLogger(WebCrawler.class.getName());   
	 
	//Strony już odwiedzone
	private final Set<String> visitedPages = Collections.synchronizedSet(new HashSet<String>());
	 
	//Strony dla których byly bledy 
	private final List<PageWrapper<T>> errorPages = Collections.synchronizedList(new ArrayList<PageWrapper<T>>());
	
	//Poprawnie przetworzone strony
	private final List<PageWrapper<T>> completePages = Collections.synchronizedList(new ArrayList<PageWrapper<T>>());
	
	//Strony aktualnie przetwarzane
	private final List<PageWrapper<T>> processingPages = Collections.synchronizedList(new ArrayList<PageWrapper<T>>());
	
	private final List<ICrawlTask<T>> rejectedTasks = Collections.synchronizedList(new ArrayList<ICrawlTask<T>>());
	
	private final List<Future<T>> futures = Collections.synchronizedList(new ArrayList<Future<T>>());
	
	private ICrawlTaskCallback<T> defaultCrawlTaskCallback;
	
	private List<IWebCrawlerCallback<T>> webCrawlerCallbacks;
	
	private ProxyManager proxyManager;
	
	private ExecutorService pool;
	
	private Long startTime = null;
	
	private Long endTime = null;
	
	
	/**
	 * Gdy osiągnie zero oznacza koniec przetwarzania
	 */
	private AtomicInteger counter;
	
	public WebCrawler() {
		this(10);
	}
	
	public WebCrawler(int nThreads) {
		pool =  Executors.newFixedThreadPool(nThreads);
		counter = new AtomicInteger(0);
	}
	
	/**
	 * Tworzy Managera z zdefiniowanym obserwatorem
	 * @param callback : obserwator zdarzeń
	 * @param nThreads : liczba wątków
	 */
	public WebCrawler(ICrawlTaskCallback<T> defaultCrawlTaskCallback, int nThreads) {
		pool =  Executors.newFixedThreadPool(nThreads);
		counter = new AtomicInteger(0);
		this.defaultCrawlTaskCallback = defaultCrawlTaskCallback;
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
	
	public ICrawlTaskCallback<T> getDefaultCrawlTaskListener() {
		return defaultCrawlTaskCallback;
	}
	
	@Override
	public void addWebCrawlerListener(IWebCrawlerCallback<T> listener) {
		if(listener!=null) {
			if(webCrawlerCallbacks==null) {
				webCrawlerCallbacks = new ArrayList<>();
			}
			webCrawlerCallbacks.add(listener);
		}
	}
	
	@Override
	public void addTask(ICrawlTask<T> task) {
		setStartTime();
		registerTask();
		task.init(this);
		try {
			Future<T> future = pool.submit(task);
			futures.add(future);
		}catch (RejectedExecutionException ex) {
			fireOnTaskRejected(task);
		}
	}
	
	@Override
	public void addTask(Collection<ICrawlTask<T>> tasks){
		setStartTime();
		try {
			for(ICrawlTask<T> task : tasks) {
				registerTask();
				task.init(this);
			}
			futures.addAll(pool.invokeAll(tasks));
		} catch (InterruptedException ex) {
			log.log(Level.SEVERE, "start", ex);
		}
	}
	
	@Override
	public void waitUntilFinish() {
		try {
			synchronized (this) {
				wait();
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
		if(result){
			unRegisterTask();
		}
		return result;
	}

	public synchronized void addVisited(PageWrapper<T> page) {
		visitedPages.add(page.getUrl());
		fireOnCrawlingChangeStateEvent();
	}
	
	public ProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	public ICrawlTaskCallback<T> getCallback() {
		return defaultCrawlTaskCallback;
	}
	
	public synchronized void addErrorPage(PageWrapper<T> page) {
		processingPages.remove(page);
		errorPages.add(page);
		unRegisterTask();
		fireOnCrawlingChangeStateEvent();
	}
	
	@Override
	public List<PageWrapper<T>> getErrorPages(){
		return errorPages;
	}
	
	private void addRejectedTask(ICrawlTask<T> task) {
		rejectedTasks.add(task);
		fireOnCrawlingChangeStateEvent();
	}
	
	@Override
	public List<ICrawlTask<T>> getRejectedTasks(){
		return rejectedTasks;
	}
	
	public synchronized void addCompletePage(PageWrapper<T> page) {
		processingPages.remove(page);
		completePages.add(page);
		unRegisterTask();
		fireOnCrawlingChangeStateEvent();
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
	
	private void registerTask(){
		counter.incrementAndGet();
		fireOnCrawlingChangeStateEvent();
	}
	
	private void unRegisterTask(){
		int res = counter.decrementAndGet();
		if (res<=0) {
			fireOnCrawlingFinishedEvent();
		}
	}
	
	private WebCrawlerEvent createWebCrawlerEvent() {
		WebCrawlerEvent event = new WebCrawlerEvent();
		event.setTotalPages(futures.size() + rejectedTasks.size());
		event.setCompletePages(completePages.size());
		event.setErrorPages(errorPages.size());
		event.setVisitedPages(visitedPages.size());
		event.setProcessingPages(processingPages.size());
		event.setRejectedPages(rejectedTasks.size());
		event.setElapsedTime(getElapsedTime());
		return event;
	}
	 
	protected void fireOnCrawlingFinishedEvent() {		
		synchronized (this) {
			notifyAll();	
		}
		shutdown();
		WebCrawlerEvent event = createWebCrawlerEvent();

		if(webCrawlerCallbacks!=null) {
			for(IWebCrawlerCallback<T> callback : webCrawlerCallbacks){
				callback.onCrawlingFinished(event);
			}
		}
	}

	protected void fireOnCrawlingChangeStateEvent() {
		if(webCrawlerCallbacks!=null) {
			WebCrawlerEvent event = createWebCrawlerEvent();
			
			for(IWebCrawlerCallback<T> callback : webCrawlerCallbacks){
				callback.onCrawlingChangeState(event);
			}
		}
	}
	
	protected void fireOnTaskRejected(ICrawlTask<T> task){
		addRejectedTask(task);
		unRegisterTask();
		if(webCrawlerCallbacks!=null) {
			for(IWebCrawlerCallback<T> callback : webCrawlerCallbacks){
				callback.onTaskRejected(task);
			}
		}
		
	}
	
	private void setStartTime() {
		if(startTime==null) {
			startTime = System.currentTimeMillis();
		}
	}
	
	private long getElapsedTime(){
		endTime = System.currentTimeMillis();
		if(startTime!=null) {
			return endTime-startTime;
		}
		return -1;
	}
	
}
