package org.crawler.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.crawler.ICrawler;
import org.crawler.ICrawlingCallback;

/**
 * Klasa  zarządzająca porocesem przetwarzania stron
 * @author Marcin
 *
 */
public class Manager  {
	
	//Strony do przetworzenia
	private BlockingQueue< Page<?> > notProcessedPages = new LinkedBlockingQueue<Page<?> >();
	
	//Strony już przetworzone
	private BlockingQueue< Page<?> > processedPages = new LinkedBlockingQueue<Page<?> >();
	
	private BlockingQueue< Page<?> > errorPages = new LinkedBlockingQueue<Page<?> >();
	
	//Do sprawdzenia czy dana strona nie była już procesowana
	private  ConcurrentMap<String, Boolean> crawledURL = new ConcurrentHashMap<String, Boolean>();
	
	private List<ICrawler> crawlers = new ArrayList<ICrawler>();
	
	private ICrawlingCallback callback;
	
	private ProxyManager proxyManager;
	
	
	public Manager() {
	}

	/**
	 * Tworzy Managera z zdefiniowanym obserwatorem
	 * @param callback : obserwator zdarzeń
	 */
	public Manager(ICrawlingCallback callback) {
		this.callback = callback;
	}
	
	/**
	 * Dodaje crawlera. Jeśli jest zdefiniowany globalny 
	 * obserwator zdarzeń, to przekazuje go do crawlera
	 * @param crawler
	 */
	public void addCrawler(ICrawler crawler) {
		crawlers.add(crawler);
		//Dodanie globalnego listenera crawlerów
		if(callback != null) {
			crawler.addCrawlingListener(callback);
		}
	}
	
	/**
	 * Metoda dodaje listę crawlerów, ustawia domyślnego 
	 * obserwatora zdarzeń
	 * @param list
	 */
	public void addCrawler(Collection<ICrawler> list) {
		crawlers.addAll(list);
		//Dodanie globalnego listenera crawlerów
		if(callback != null) {
			for(ICrawler crawler : list) {
				crawler.addCrawlingListener(callback);	
			}
			
		}
	}

	/**
	 * Metoda dodaje nowa stronę do przetwarzania
	 * @param page
	 * @return true jeśli nie była wcześniej dodawana do przetwarzania
	 */
	public  boolean addNewPage(Page<?> page){
		if(crawledURL.containsKey(page.getUrl())) {
			return false;
		} else {
			crawledURL.put(page.getUrl(), Boolean.TRUE);
			notProcessedPages.add(page);
			return true;	
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
	 
	/**
	 * Metoda pobiera strone do przetworzenia
	 * @param crawler 
	 * @return
	 * @throws InterruptedException
	 */
	public Page<?> fetch(ICrawler crawler) throws InterruptedException {
		if(notProcessedPages.size()>0) {
			Page<?> page = notProcessedPages.element();
			if(page!=null && crawler.canParsePage(page)) {
				return notProcessedPages.take();
			}
		}
		return null;
	}

	
	public ProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}
	
	
	
	
}
