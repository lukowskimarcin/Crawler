package org.crawler.app;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.crawler.ICrawlTask;
import org.crawler.ICrawlingCallback;
import org.crawler.IWebCrawler;
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
		
		
		ICrawlingCallback<List<Proxy>> callback = new ICrawlingCallback<List<Proxy>>() {

			@Override
			public void onPageCrawlingStart(ICrawlTask<List<Proxy>> crawler, PageWrapper<List<Proxy>> page) {
			//	System.out.println("Start : " + page.getUrl() );
			}

			@Override
			public void onPageCrawlingCompleted(ICrawlTask<List<Proxy>> crawler, PageWrapper<List<Proxy>> page) {
				int size = 0;
				if(page.getData()!=null){
					size = page.getData().size();
				}
				
				System.out.println("Complete : " + page.getUrl() + " size: " + size);
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
			
			@Override
			public void onCrawlingFinished() {
				System.out.println("All pages crawled !!!");
				
			}
		};  
		
		IWebCrawler<List<Proxy>> proxyWebCrawler = new WebCrawler<List<Proxy>>(callback);
		
		ProxyCentrumPagesCrawler rootTask = new ProxyCentrumPagesCrawler(new PageWrapper<List<Proxy>>("http://prx.centrump2p.com"));
		proxyWebCrawler.addTask(rootTask);
		
		Thread.sleep(10);
		
		//proxyWebCrawler.cancel();
		proxyWebCrawler.waitUntilFinish();
		
		
		proxyWebCrawler.shutdownAndAwaitTermination();		
		System.out.println("END");
		
		 System.out.println("Errors: " + proxyWebCrawler.getErrorPages().size());
		 System.out.println("Processing: " + proxyWebCrawler.getProcesingPages().size());
		 System.out.println("Completed: " + proxyWebCrawler.getCompletePages().size());
		
		
 
	}

}
