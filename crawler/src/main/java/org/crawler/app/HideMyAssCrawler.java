package org.crawler.app;


import org.crawler.imp.CrawlTask;
import org.crawler.imp.PageWrapper;
import org.crawler.imp.Proxy;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;


//http://proxylist.hidemyass.com/6#listable
public class HideMyAssCrawler extends CrawlTask<Proxy> {
	
	
	public HideMyAssCrawler(PageWrapper<Proxy> page) {
		super(page);
	}

	private static final long serialVersionUID = -8884778544170508208L;

	@Override
	public void parsePage() throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);

		HtmlPage htmlPage = webClient.getPage(page.getUrl());
			
		HtmlTable htmlTable = (HtmlTable) htmlPage.getElementById("listable");	
		htmlPage = htmlPage.getElementById("proxy-list-upd-btn").click();
		htmlTable = (HtmlTable) htmlPage.getElementById("listable");	
		System.out.println(htmlTable.getTextContent());
		webClient.close();
	}

}
