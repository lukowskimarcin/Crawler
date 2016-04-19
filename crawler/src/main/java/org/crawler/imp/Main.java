package org.crawler.imp;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Manager manager = new Manager();
		
		DetailCrawler crawler = new DetailCrawler(manager);
		
		manager.fetch(crawler);
		manager.fetch(crawler);
		
		
		System.out.println("test");
	}

}
