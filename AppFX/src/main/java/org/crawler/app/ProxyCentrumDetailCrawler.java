package org.crawler.app;

import java.util.ArrayList;
import java.util.List;

import org.crawler.imp.CrawlTask;
import org.crawler.imp.WebCrawler;
import org.crawler.utils.Proxy;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ProxyCentrumDetailCrawler extends CrawlTask {
	private static final long serialVersionUID = -5028690879897857829L;
	
	private TextArea text;
	
	
	public ProxyCentrumDetailCrawler(String url, TextArea text ){
		super(url);
		this.text= text;
	}
	

	@Override
	public void parse() throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);

		
		HtmlPage htmlPage = webClient.getPage(getUrl());
		
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
		setData(list);

	}

	@Override
	public void process() {
		Platform.runLater(() -> {
			List<Proxy> list = (List<Proxy> )getData();
			String ip = "";
			if(list!=null){
				for(Proxy s : list) {
					ip += s + "\n";
				}
				
			}
			text.setText(text.getText() + ip);
		});
	}

}
