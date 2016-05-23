package org.crawler.app;

import java.util.ArrayList;
import java.util.List;

import org.crawler.imp.CrawlTask;
import org.crawler.imp.PageWrapper;
import org.crawler.imp.Proxy;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ProxyCentrumCrawler extends CrawlTask<List<Proxy>> {
	private static final long serialVersionUID = -5028690879897857829L;
	
	public ProxyCentrumCrawler(PageWrapper<List<Proxy>> page) {
		super(page);
	}

	@Override
	public void parsePage() throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage htmlPage = webClient.getPage(page.getUrl());
		
		HtmlDivision pager = (HtmlDivision) htmlPage.getElementById("pager");
		for(HtmlElement el : pager.getElementsByTagName("a")) {
			try {
				HtmlAnchor anchor = (HtmlAnchor) el;
				String href = anchor.getHrefAttribute();
				
				if(href.matches(".*/[0-9]+")) {
					String url = "http://prx.centrump2p.com" + href;
					PageWrapper<List<Proxy>> nextPage = new PageWrapper<List<Proxy>>(url, page.getLevel()+1);
					ProxyCentrumCrawler task = new ProxyCentrumCrawler(nextPage);
					webCrawler.addTask(task); 
				}
			}catch (Exception ex) {
				System.out.println(ex);
			}
		}
		
		List<Proxy> list = new ArrayList<Proxy>();
		for(HtmlAnchor anchor : htmlPage.getAnchors()) {
			String href = anchor.getHrefAttribute();
			
			if(href.contains("/ip/")) {
				int poz = href.indexOf("/ip/");
				String host = href.substring(poz+4);
				Proxy proxy = new  Proxy(host, 80);
				list.add(proxy);
			}
		}
		
		webClient.close();
		page.setData(list);

	}

}
