package org.crawler.imp;

import org.crawler.Manager;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		DetailCrawler crawler = new DetailCrawler();
		
		
		Manager manager = new Manager();
		
		manager.fetch(crawler);
		manager.fetch(crawler);
		
		
		System.out.println("test");
	}

}
