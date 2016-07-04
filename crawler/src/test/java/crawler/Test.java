package crawler;

import java.io.File;

import org.crawler.IEventListener;
import org.crawler.events.CrawlTaskEvent;
import org.crawler.imp.PageWrapper;
import org.crawler.imp.WebCrawler;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Test {
	
	public static void main(String[] args) throws Exception {
	
	
		WebCrawler<String> crawler = new WebCrawler<String>(6);
		
		crawler.addOnPageCrawlingCompletedListener( new IEventListener<CrawlTaskEvent<String>>() {
			
			@Override
			public void handle(CrawlTaskEvent<String> event) {
				System.out.println("test");
				
			}
		});
		
		PageWrapper<String> xx = new PageWrapper<>("www.wp.pl");
		xx.setData("jakieś dane");
		
		crawler.addCompletePage(new PageWrapper<>("www.onet.pl"));
		crawler.addProcessingPage(xx);
		
		
		Serializer serializer = new Persister();
		File result = new File("e:/example.xml");
	
		serializer.write(crawler, result);
		
		 
		
		WebCrawler<String> crawler2 = serializer.read(WebCrawler.class, result);
		System.out.println(crawler2);
	
		
		File f = new File("E:/test.xml");
		serializer.write(xx	, f);
		
		
		PageWrapper<String> xy = serializer.read(PageWrapper.class, f);
		System.out.println(xy);
	}
	
	
	

}
