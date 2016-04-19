package org.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.crawler.imp.DetailPage;
import org.crawler.imp.NormalPage;

/**
 * Klasa  zarządzająca porocesem przetwarzania stron
 * @author Marcin
 *
 */
public class Manager  {
	
	private BlockingQueue< Page<?> > notProcessedPages = new LinkedBlockingQueue<Page<?> >();
	
	private BlockingQueue< Page<?> > processedPages = new LinkedBlockingQueue<Page<?> >();
	
	private List<ICrawler> crawlers = new ArrayList<ICrawler>();
	

	public Manager() {
		notProcessedPages.add(new DetailPage("wp.pl"));
		notProcessedPages.add(new NormalPage("onet.pl"));
	}
	
	
	
	/**
	 * Metoda pobiera strone do przetworzenia
	 * @param crawler 
	 * @return
	 * @throws InterruptedException
	 */
	public Page<?> fetch(ICrawler crawler) throws InterruptedException {
		Page<?> page = notProcessedPages.element();
		if(page!=null && crawler.canParsePage(page)) {
			return notProcessedPages.take();
		}
		return null;
	}
	
	
}
