package org.crawler.app;

import java.util.List;
import java.util.concurrent.Future;

import org.crawler.ICrawlTask;
import org.crawler.ICrawlingCallback;
import org.crawler.IWebCrawler;
import org.crawler.imp.PageWrapper;
import org.crawler.imp.Proxy;
import org.crawler.imp.WebCrawler;

public class Main {

	public static void main(String[] args) {
		
		ICrawlingCallback<PageWrapper<List<Proxy>>> callback = new ICrawlingCallback<PageWrapper<List<Proxy>>>() {

			@Override
			public void onPageCrawlingStart(ICrawlTask<PageWrapper<List<Proxy>>> crawler,
					PageWrapper<List<Proxy>> page) {
				System.out.println("Crawl start: " + page.getUrl());
			}

			@Override
			public void onPageCrawlingCompleted(ICrawlTask<PageWrapper<List<Proxy>>> crawler,
					PageWrapper<List<Proxy>> page) {
				System.out.println("Crawl complete: " + page.getUrl());
			}

			@Override
			public void onPageCrawlingFailed(ICrawlTask<PageWrapper<List<Proxy>>> crawler,
					PageWrapper<List<Proxy>> page) {
			}

			@Override
			public void onAlreadyVisited(ICrawlTask<PageWrapper<List<Proxy>>> crawler, PageWrapper<List<Proxy>> page) {
			}

			@Override
			public void onPageProcessingProgress(ICrawlTask<PageWrapper<List<Proxy>>> crawler,
					PageWrapper<List<Proxy>> page, int percent) {
			}
		};
		
		IWebCrawler<PageWrapper<List<Proxy>>> proxyWebCrawler = new WebCrawler<PageWrapper<List<Proxy>>>(callback);
		proxyWebCrawler.submitCrawlTask(new ProxyCentrumCrawler(new PageWrapper<List<Proxy>>("http://prx.centrump2p.com"))  );
		
		
		 
		
		
		
	}

}
