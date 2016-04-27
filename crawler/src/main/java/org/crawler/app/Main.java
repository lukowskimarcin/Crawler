package org.crawler.app;

import org.crawler.ICrawlTask;
import org.crawler.IWebCrawler;
import org.crawler.imp.*;

public class Main {

	public static void main(String[] args) {
		IWebCrawler<Page<String>> webCrawler = new WebCrawler<Page<String>>();
		
		Page<String> root = new Page<String>("http://wp.pl");
		ICrawlTask<Page<String>> task = new RootPageCrawl(root);
		
		Page<String> page2 = new Page<String>("http://onet.pl");
		ICrawlTask<Page<String>> task2 = new RootPageCrawl(page2);
		
		
		webCrawler.submitCrawlTask(task);
		webCrawler.submitCrawlTask(task2);
		
		
		System.out.println("END");

	}

}
