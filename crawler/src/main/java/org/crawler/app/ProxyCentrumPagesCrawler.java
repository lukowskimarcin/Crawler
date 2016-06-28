package org.crawler.app;

import java.util.List;

import org.crawler.imp.CrawlTask;
import org.crawler.imp.Proxy;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ProxyCentrumPagesCrawler extends CrawlTask<List<Proxy>> {
	private static final long serialVersionUID = 1610323384868842390L;

	public ProxyCentrumPagesCrawler(String url){
		super(url);
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
					ProxyCentrumDetailCrawler task = new ProxyCentrumDetailCrawler(url);
					webCrawler.addTask(task); 
				}
			}catch (Exception ex) {
				System.out.println(ex);
			}
		}
		
		webClient.close();
		page.setData(null);
		
	}
	
 

}
