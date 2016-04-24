package org.crawler.imp;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Manager manager = new Manager();
		
		
		manager.addNewPage(new DetailPage("wp.pl"));
		manager.addNewPage(new DetailPage("onet.pl"));
		manager.addNewPage(new PageTask<Integer>("stooq.pl"));
		manager.addNewPage(new NormalPage("google.com"));
		
		
		
		DetailCrawler crawler = new DetailCrawler(manager);
		
		manager.fetch(crawler);
		manager.fetch(crawler);
		manager.fetch(crawler);
		manager.fetch(crawler);
		manager.fetch(crawler);
		
		
		System.out.println("test");
	}

}
