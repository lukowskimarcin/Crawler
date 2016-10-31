package org.crawler.app;

import java.util.List;

import org.crawler.IWebCrawler;
import org.crawler.imp.CrawlTask;
import org.crawler.imp.WebCrawler;
import org.crawler.imp.CrawlTask;
import org.crawler.utils.Proxy;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ProxyCentrumPagesCrawler extends CrawlTask {
	private static final long serialVersionUID = 1610323384868842390L;

	private TextArea text;
	
	public ProxyCentrumPagesCrawler(String url, TextArea text ){
		super(url);
		this.text = text;
	}
	
	@Override
	public void parse() throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);

		HtmlPage htmlPage = webClient.getPage(getUrl());
		
		HtmlDivision pager = (HtmlDivision) htmlPage.getElementById("pager");
		for(HtmlElement el : pager.getElementsByTagName("a")) {
			try {
				HtmlAnchor anchor = (HtmlAnchor) el;
				String href = anchor.getHrefAttribute();
				
				if(href.matches(".*/[0-9]+")) {
					String url = "http://prx.centrump2p.com" + href;
					ProxyCentrumDetailCrawler task = new ProxyCentrumDetailCrawler(url, text );
					webCrawler.addTask(task); 
				}
			}catch (Exception ex) {
				System.out.println(ex);
			}
		}
		
		webClient.close();
		//page.setData(null);
		
	}


	@Override
	public void process() {
		// TODO Auto-generated method stub
		
		
	}

}
