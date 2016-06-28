package crawler;

import org.crawler.imp.WebCrawler;

public class Main {

	public static void main(String[] args) {
		
		WebCrawler<String> crawler = new WebCrawler<>();
		crawler.addOnAlreadyVisitedListener( e -> System.out.println("ss"));

	}

}
