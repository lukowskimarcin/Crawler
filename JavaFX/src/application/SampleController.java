package application;

import java.util.logging.Level;

import java.util.*;


import org.crawler.ICrawlTask;
import org.crawler.ICrawlTaskCallback;
import org.crawler.IWebCrawler;
import org.crawler.IWebCrawlerCallback;
import org.crawler.app.ProxyCentrumPagesCrawler;
import org.crawler.events.CrawlTaskEvent;
import org.crawler.events.WebCrawlerEvent;
import org.crawler.imp.Proxy;
import org.crawler.imp.WebCrawler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SampleController {

    @FXML
    private Button button;

    @FXML
    void onAction(ActionEvent event) {
    	java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		
		
		ICrawlTaskCallback<List<Proxy>> callback = new  ICrawlTaskCallback<List<Proxy>>() {
			
			public void onPageProcessingProgress(CrawlTaskEvent<List<Proxy>> event) {
				// TODO Auto-generated method stub
				
			}
			
			public void onPageCrawlingStart(CrawlTaskEvent<List<Proxy>> event) {
				// TODO Auto-generated method stub
				
			}
			
			public void onPageCrawlingFailed(CrawlTaskEvent<List<Proxy>> event) {
				// TODO Auto-generated method stub
				
			}
			
			public void onPageCrawlingCompleted(CrawlTaskEvent<List<Proxy>> event) {
				System.out.println("Page complete " + event.getPage().getUrl() + " [" + event.getTimeSeconds() + " sec]");
			}
			
			public void onAlreadyVisited(CrawlTaskEvent<List<Proxy>> event) {
				System.out.println("Already visited " + event.getPage().getUrl());
				
			}
		};
		
		IWebCrawler<List<Proxy>> proxyWebCrawler = new WebCrawler<List<Proxy>>(callback, 8);
		
		proxyWebCrawler.addWebCrawlerListener(new IWebCrawlerCallback<List<Proxy>>() {
			
			public void onCrawlingFinished(WebCrawlerEvent event) {
				System.out.println("CRAWLING FINISHED!!!");
				System.out.println(event);
			}
			
			public void onCrawlingChangeState(WebCrawlerEvent event) {
				//System.out.println(event);
			}
			
			public void onTaskRejected(ICrawlTask<List<Proxy>> task) {
				System.out.println("Task rejected: " + task.getPage().getUrl());
			}
			
		});
		
		
		ProxyCentrumPagesCrawler rootTask = new ProxyCentrumPagesCrawler("http://prx.centrump2p.com");
		proxyWebCrawler.addTask(rootTask);
		
		 
		//proxyWebCrawler.addTask(new ProxyCentrumPagesCrawler("http://prx.centrump2p.com"));
		
		System.out.println("waitUntilFinish!!");
		//proxyWebCrawler.cancel();
		proxyWebCrawler.waitUntilFinish();
		
		
		//proxyWebCrawler.shutdownAndAwaitTermination();		
		System.out.println("END");
    }

}