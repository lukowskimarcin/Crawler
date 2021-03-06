package org.crawler.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.crawler.ICrawlTaskListener;
import org.crawler.IEventListener;
import org.crawler.IWebCrawler;
import org.crawler.events.WebCrawlerEvent;
import org.crawler.listeners.CrawlTaskBaseListener;
import org.crawler.utils.ProxyManager;

/**
 * Klasa  zarządzająca porocesem przetwarzania stron
 * @author Marcin
 *
 */
public class WebCrawler extends CrawlTaskBaseListener implements IWebCrawler, ICrawlTaskListener   {
	
	private static final Logger log = Logger.getLogger(WebCrawler.class.getName());   
	 
	//Strony już odwiedzone
	private Set<String> visitedPages = Collections.synchronizedSet(new HashSet<String>());
	
	//Strony dla których byly bledy
	private BlockingQueue<CrawlTask> errorPages = new LinkedBlockingQueue<CrawlTask>();
		
	//Poprawnie przetworzone strony
	private BlockingQueue<CrawlTask>  completePages = new LinkedBlockingQueue<CrawlTask>();
	
	//Strony aktualnie przetwarzane
	private BlockingQueue<CrawlTask> processingPages = new LinkedBlockingQueue<CrawlTask>();
	
	private BlockingQueue<CrawlTask> rejectedTasks = new LinkedBlockingQueue<CrawlTask>();
	
	private List<Future<?>> futures = Collections.synchronizedList(new ArrayList<Future<?>>());
	
	private ProxyManager proxyManager;
	
	private ExecutorService pool;
	
	private List<IEventListener<WebCrawlerEvent>> onCrawlingFinishedListener;
	
	private List<IEventListener<WebCrawlerEvent>> onCrawlingChangeStateListener;
	
	private List<IEventListener<CrawlTask>> onTaskRejectedListener;
	
	private Long startTime = null;
	
	private Long endTime = null;
	
	private Integer maxCrawlDeep = null;
	
	private Integer maxCrawlTasksNumber = null;
	
	/**
	 * Gdy osiągnie zero oznacza koniec przetwarzania
	 */
	private AtomicInteger counter;
	
	private AtomicInteger completed;
	
	
	public WebCrawler(int nThreads) {
		init(nThreads);
	}
	
	public void shutdown() {
		pool.shutdown();
	}
	
	@Override
	public void init(int nThreads) {
		startTime = null;
		endTime = null;
		pool =  Executors.newFixedThreadPool(nThreads);
		counter = new AtomicInteger(0);
		completed = new AtomicInteger(0);
		visitedPages = Collections.synchronizedSet(new HashSet<String>());
		errorPages = new LinkedBlockingQueue<CrawlTask>();
		completePages = new LinkedBlockingQueue<CrawlTask>();
		futures = Collections.synchronizedList(new ArrayList<Future<?>>());
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
	
	@Override	
	public void addTask(CrawlTask task) {
		if(maxCrawlTasksNumber==null || maxCrawlTasksNumber.compareTo(counter.get()) >= 0 ) { 
			if(maxCrawlDeep==null || maxCrawlDeep.compareTo(task.getLevel()) >= 0 ) {
				
				if(task.validateUrl()){
					initStartTime();
					registerTask();
					task.init(this);
					
					try {
						Future<?> future = pool.submit(task);
						futures.add(future);
					}catch (RejectedExecutionException ex) {
						task.setErrorMessage(ex.getMessage());
						fireOnTaskRejected(task);
					}		
				} else {
					task.setErrorMessage("invalid Url");
					fireOnTaskRejected(task);
				}
				
			}
		}
	}
	
	@Override
	public void addTask(Collection<CrawlTask> tasks){
		for(CrawlTask task : tasks) {
			addTask(task);
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
		for(Future<?> f : futures) {
			f.cancel(false);
		}
		pool.shutdown();
	}
	
	public boolean isVisited(CrawlTask page) {
		boolean result = visitedPages.contains(page.getUrl());
		if(result){
			unRegisterTask();
		}
		return result;
	}

	public synchronized void addVisited(CrawlTask page) {
		visitedPages.add(page.getUrl());
		fireOnCrawlingChangeStateEvent();
	}
	
	public ProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	 
	public synchronized void addErrorPage(CrawlTask page) {
		processingPages.remove(page);
		errorPages.add(page);
		unRegisterTask();
		fireOnCrawlingChangeStateEvent();
	}
	
	@Override
	public BlockingQueue<CrawlTask> getErrorPages(){
		return errorPages;
	}
	
	private void addRejectedTask(CrawlTask task) {
		rejectedTasks.add(task);
		fireOnCrawlingChangeStateEvent();
	}
	
	@Override
	public BlockingQueue<CrawlTask> getRejectedTasks(){
		return rejectedTasks;
	}
	
	public synchronized void addCompletePage(CrawlTask page) {
		processingPages.remove(page);
		completePages.add(page);
		//Informacja dla procesorow
		synchronized (completePages) {
			completePages.notifyAll(); 
		}
		
		completed.incrementAndGet();
		
		unRegisterTask();
		fireOnCrawlingChangeStateEvent();
	}
	
	@Override
	public BlockingQueue<CrawlTask> getCompletePages() {
		return completePages;
	}

	public synchronized void addProcessingPage(CrawlTask page) {
		processingPages.add(page);
	}
	
	@Override
	public BlockingQueue<CrawlTask> getProcesingPages() {
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
	
	private void initStartTime() {
		if(startTime==null) {
			startTime = System.currentTimeMillis();
		}
	}
	
	private long getElapsedTime(){
		if(startTime!=null) {
			endTime = System.currentTimeMillis();
			return endTime-startTime;
		}
		return -1;
	}
	
	//Crawler Events
	private WebCrawlerEvent createWebCrawlerEvent() {
		WebCrawlerEvent event = new WebCrawlerEvent();
		event.setTotalPages(futures.size() + rejectedTasks.size());
		event.setCompletePages(completed.get());
		event.setErrorPages(errorPages.size());
		event.setVisitedPages(visitedPages.size());
		event.setProcessingPages(processingPages.size());
		event.setRejectedPages(rejectedTasks.size());
		event.setElapsedTime(getElapsedTime());
		return event;
	}
	
	
	@Override
	public void addOnCrawlingFinishedListener(IEventListener<WebCrawlerEvent> listener) {
		if(listener!=null) {
			if(onCrawlingFinishedListener==null) {
				onCrawlingFinishedListener = new ArrayList<IEventListener<WebCrawlerEvent>>();
			}
			onCrawlingFinishedListener.add(listener);
		}
	}
	 
	protected void fireOnCrawlingFinishedEvent() {		
		synchronized (this) {
			notifyAll();	
		}
		shutdown();
		WebCrawlerEvent event = createWebCrawlerEvent();

		if(onCrawlingFinishedListener!=null) {
			for(IEventListener<WebCrawlerEvent> callback : onCrawlingFinishedListener){
				callback.handle(event);
			}
		}
	}
	
	@Override
	public void addOnCrawlingChangeStateListener(IEventListener<WebCrawlerEvent> listener) {
		if(listener!=null) {
			if(onCrawlingChangeStateListener==null) {
				onCrawlingChangeStateListener = new ArrayList<IEventListener<WebCrawlerEvent>>();
			}
			onCrawlingChangeStateListener.add(listener);
		}
	}

	protected void fireOnCrawlingChangeStateEvent() {
		if(onCrawlingChangeStateListener!=null) {
			WebCrawlerEvent event = createWebCrawlerEvent();
			
			for(IEventListener<WebCrawlerEvent> callback : onCrawlingChangeStateListener){
				callback.handle(event);
			}
		}
	}
	
	@Override
	public void addOnTaskRejectedListener(IEventListener<CrawlTask> listener) {
		if(listener!=null) {
			if(onTaskRejectedListener==null) {
				onTaskRejectedListener = new ArrayList<IEventListener<CrawlTask>>();
			}
			onTaskRejectedListener.add(listener);
		}
	}
	
	protected void fireOnTaskRejected(CrawlTask task){
		addRejectedTask(task);
		unRegisterTask();
		if(onTaskRejectedListener!=null) {
			for(IEventListener<CrawlTask> callback : onTaskRejectedListener){
				callback.handle(task);
			}
		}
		
	}

	public Integer getMaxCrawlDeep() {
		return maxCrawlDeep;
	}

	public void setMaxCrawlDeep(Integer maxCrawlDeep) {
		this.maxCrawlDeep = maxCrawlDeep;
	}

	public Integer getMaxCrawlTasksNumber() {
		return maxCrawlTasksNumber;
	}

	public void setMaxCrawlTasksNumber(Integer maxCrawlTasksNumber) {
		this.maxCrawlTasksNumber = maxCrawlTasksNumber;
	}

	 
	
}
