package crawler;

import org.crawler.IEventListener;
import org.crawler.IWebCrawler;
import org.crawler.events.CrawlTaskEvent;
import org.crawler.imp.WebCrawler;

public class Test {
	
	public static void main(String[] args) throws Exception {
	
	
		IWebCrawler crawler =  WebCrawler.getInstance();
		
		crawler.addOnPageCrawlingCompletedListener( new IEventListener<CrawlTaskEvent>() {
			
			@Override
			public void handle(CrawlTaskEvent event) {
				System.out.println("test");
				
			}
		});
		
		

		
	}
}
