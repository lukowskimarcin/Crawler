package crawler;

import javax.inject.Inject;

import org.crawler.IEventListener;
import org.crawler.IWebCrawler;
import org.crawler.events.CrawlTaskEvent;
import org.crawler.imp.WebCrawler;
import org.crawler.utils.ProxyManager;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Test {
	
	
	@Inject
	private static IWebCrawler crawler;
	
	
	public static void main(String[] args) throws Exception {
	
		WeldContainer weldContainer = new Weld().initialize();
		Test test = new Test();
		
		
		 
		
		crawler.addOnPageCrawlingCompletedListener( new IEventListener<CrawlTaskEvent>() {
			
			@Override
			public void handle(CrawlTaskEvent event) {
				System.out.println("test");
				
			}
		});
		
		

		
	}
}
