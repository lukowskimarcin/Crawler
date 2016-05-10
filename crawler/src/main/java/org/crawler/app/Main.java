package org.crawler.app;

import org.crawler.IWebCrawler;
import org.crawler.imp.Page;
import org.crawler.imp.Proxy;
import org.crawler.imp.WebCrawler;

public class Main {

	public static void main(String[] args) {
//		IWebCrawler<Page<String>> webCrawler = new WebCrawler<Page<String>>();
//		
//		Page<String> root = new Page<String>("http://wp.pl");
//		ICrawlTask<Page<String>> task = new RootPageCrawl(root);
//		
//		Page<String> page2 = new Page<String>("http://onet.pl");
//		ICrawlTask<Page<String>> task2 = new RootPageCrawl(page2);
//		
//		
//		webCrawler.submitCrawlTask(task);
//		webCrawler.submitCrawlTask(task2);
//		
		
		IWebCrawler<Page<Proxy>> proxyWebCrawler = new WebCrawler<Page<Proxy>>();
		proxyWebCrawler.submitCrawlTask(new HideMyAssCrawler(new Page<Proxy>("http://proxylist.hidemyass.com")));
		
		proxyWebCrawler.shutdown();
		System.out.println("END");
	}

}
