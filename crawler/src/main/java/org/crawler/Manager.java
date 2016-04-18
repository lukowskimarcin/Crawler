package org.crawler;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.crawler.imp.DetailPage;
import org.crawler.imp.NormalPage;

public class Manager  {
	private BlockingQueue< Page<?> > toVisit = new LinkedBlockingQueue<Page<?> >();
	private List< ICrawler  > crawlers = null;
	

	public Manager() {
		toVisit.add(new DetailPage());
		toVisit.add(new NormalPage());
	}
	
	
	
	
	public Page<?> fetch(ICrawler crawler) throws InterruptedException {
		Page<?> page = toVisit.element();
		if(page!=null && crawler.canParsePage(page)) {
			return toVisit.take();
		}
		return null;
	}
	
	
}
