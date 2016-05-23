package org.crawler.app;

import java.util.List;

import org.crawler.ICrawlTask;
import org.crawler.ICrawlingCallback;
import org.crawler.IWebCrawler;
import org.crawler.imp.PageWrapper;
import org.crawler.imp.Proxy;
import org.crawler.imp.ProxyManager;
import org.crawler.imp.WebCrawler;

public class Main {

	public static void main(String[] args) {
		
		ICrawlingCallback<List<Proxy>> callback = new ICrawlingCallback<List<Proxy>>() {

			@Override
			public void onPageCrawlingStart(ICrawlTask<List<Proxy>> crawler, PageWrapper<List<Proxy>> page) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageCrawlingCompleted(ICrawlTask<List<Proxy>> crawler, PageWrapper<List<Proxy>> page) {
				System.out.println("Complete : " + page.getUrl() + " size: " + page.getData().size());
			}

			@Override
			public void onPageCrawlingFailed(ICrawlTask<List<Proxy>> crawler, PageWrapper<List<Proxy>> page) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAlreadyVisited(ICrawlTask<List<Proxy>> crawler, PageWrapper<List<Proxy>> page) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageProcessingProgress(ICrawlTask<List<Proxy>> crawler, PageWrapper<List<Proxy>> page,
					int percent) {
				// TODO Auto-generated method stub
			}
		};  
		
		IWebCrawler<List<Proxy>> proxyWebCrawler = new WebCrawler<List<Proxy>>(callback);
		
		ProxyCentrumCrawler rootTask = new ProxyCentrumCrawler(new PageWrapper<List<Proxy>>("http://prx.centrump2p.com"));
		boolean result = proxyWebCrawler.start(rootTask);
		
		System.out.println("END");
		
		List<PageWrapper<List<Proxy>>> list  = proxyWebCrawler.getCompletePage();
		if(list != null) {
			ProxyManager proxyManager = new ProxyManager();
			for(PageWrapper<List<Proxy>> x : list) {
				for(Proxy p : x.getData()) {
					proxyManager.addProxy(p);
				}
			}
			
			proxyManager.testProxies();
			proxyManager.saveProxies("E:/proxy.txt");
		}
	}

}
