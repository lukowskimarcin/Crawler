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

public class ProxyCentrumDetailCrawler extends CrawlTask<List<Proxy>> {
	private static final long serialVersionUID = -5028690879897857829L;
	
	public ProxyCentrumDetailCrawler(PageWrapper<List<Proxy>> page) {
		super(page);
	}

	@Override
	public void parsePage() throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);

		HtmlPage htmlPage = webClient.getPage(page.getUrl());
		
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
