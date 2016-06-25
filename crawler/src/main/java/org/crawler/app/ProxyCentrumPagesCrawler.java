package org.crawler.app;

import java.util.List;
import java.util.logging.Level;

import org.crawler.ICrawlTask;
import org.crawler.ICrawlTaskCallback;
import org.crawler.IWebCrawler;
import org.crawler.IWebCrawlerCallback;
import org.crawler.events.CrawlTaskEvent;
import org.crawler.events.WebCrawlerEvent;
import org.crawler.imp.CrawlTask;
import org.crawler.imp.PageWrapper;
import org.crawler.imp.Proxy;
import org.crawler.imp.WebCrawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ProxyCentrumPagesCrawler extends CrawlTask<List<Proxy>> {
	private static final long serialVersionUID = 1610323384868842390L;

	public ProxyCentrumPagesCrawler(PageWrapper<List<Proxy>> page) {
		super(page);
	}
	
	@Override
	public void parsePage() throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);

		HtmlPage htmlPage = webClient.getPage(page.getUrl());
		
		HtmlDivision pager = (HtmlDivision) htmlPage.getElementById("pager");
		for(HtmlElement el : pager.getElementsByTagName("a")) {
			try {
				HtmlAnchor anchor = (HtmlAnchor) el;
				String href = anchor.getHrefAttribute();
				
				if(href.matches(".*/[0-9]+")) {
					String url = "http://prx.centrump2p.com" + href;
					PageWrapper<List<Proxy>> nextPage = new PageWrapper<List<Proxy>>(url, page.getLevel()+1);
					ProxyCentrumDetailCrawler task = new ProxyCentrumDetailCrawler(nextPage);
					webCrawler.addTask(task); 
				}
			}catch (Exception ex) {
				System.out.println(ex);
			}
		}
		
		webClient.close();
		page.setData(null);
		
	}
	
public static void main(String[] args) throws InterruptedException {
		
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		
		
		ICrawlTaskCallback<List<Proxy>> callback = new  ICrawlTaskCallback<List<Proxy>>() {
			
			@Override
			public void onPageProcessingProgress(CrawlTaskEvent<List<Proxy>> event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageCrawlingStart(CrawlTaskEvent<List<Proxy>> event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageCrawlingFailed(CrawlTaskEvent<List<Proxy>> event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageCrawlingCompleted(CrawlTaskEvent<List<Proxy>> event) {
				System.out.println("Page complete " + event.getPage().getUrl() + " [" + event.getTimeSeconds() + " sec]");
			}
			
			@Override
			public void onAlreadyVisited(CrawlTaskEvent<List<Proxy>> event) {
				// TODO Auto-generated method stub
				
			}
		};
		
		IWebCrawler<List<Proxy>> proxyWebCrawler = new WebCrawler<List<Proxy>>(callback, 8);
		
		proxyWebCrawler.addWebCrawlerListener(new IWebCrawlerCallback<List<Proxy>>() {
			
			@Override
			public void onCrawlingFinished(WebCrawlerEvent event) {
				System.out.println("CRAWLING FINISHED!!!");
				System.out.println(event);
			}
			
			@Override
			public void onCrawlingChangeState(WebCrawlerEvent event) {
				//System.out.println(event);
			}
			
			@Override
			public void onTaskRejected(ICrawlTask<List<Proxy>> task) {
				System.out.println("Task rejected: " + task.getPage().getUrl());
			}
			
		});
		
		
		ProxyCentrumPagesCrawler rootTask = new ProxyCentrumPagesCrawler(new PageWrapper<List<Proxy>>("http://prx.centrump2p.com"));
		proxyWebCrawler.addTask(rootTask);
		
		Thread.sleep(10);
		
		//System.out.println("waitUntilFinish");
		//proxyWebCrawler.cancel();
		proxyWebCrawler.waitUntilFinish();
		
		
		//proxyWebCrawler.shutdownAndAwaitTermination();		
		System.out.println("END");
		 
	}

}
