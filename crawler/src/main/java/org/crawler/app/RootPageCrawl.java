package org.crawler.app;

import org.crawler.imp.CrawlTask;
import org.crawler.imp.Page;

public class RootPageCrawl extends CrawlTask<Page<String>> {

	public RootPageCrawl(Page<String> page) {
		super(page);
	}

	@Override
	public void parsePage() {
		System.out.println("parsing page:" + getPage().getUrl());
		
		int level = page.getLevel()+1;
		if(level <= 20) {
			RootPageCrawl subtask = new RootPageCrawl(new Page<String>(page.getUrl() + "x", level));
			webCrawler.submitCrawlTask(subtask);
		}
		
	}


}
