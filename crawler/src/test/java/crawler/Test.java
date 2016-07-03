package crawler;

import java.io.File;

import org.crawler.imp.PageWrapper;
import org.crawler.imp.WebCrawler;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Test {
	
	public static void main(String[] args) throws Exception {
	
	
	WebCrawler<String> crawler = new WebCrawler<String>(6);
	
	PageWrapper<String> xx = new PageWrapper<>("www.wp.pl");
	xx.setData("jakieś dane");
	
	crawler.addCompletePage(new PageWrapper<>("www.onet.pl"));
	crawler.addProcessingPage(xx);
	
	
	Serializer serializer = new Persister();
	File result = new File("e:/example.xml");

	serializer.write(crawler, result);
	
	
	}
	
	
	

}
