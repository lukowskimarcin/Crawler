package org.crawler.factories;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import org.crawler.imp.WebCrawler;


public class WebCrawlerFactory {
	
	private final int THREADS = 10;
	private WebCrawler crawler = null;
	
	@Produces
	public WebCrawler createWebCrawler() {
		if(crawler==null) {
			crawler = new WebCrawler(THREADS);	
		}
		return crawler;
	}
	

}
