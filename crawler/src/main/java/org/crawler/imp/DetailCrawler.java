package org.crawler.imp;

import org.crawler.Crawler;
import org.crawler.Manager;
import org.crawler.Page;

public class DetailCrawler extends Crawler<DetailPage> {

	public DetailCrawler(Manager manager) {
		super(manager);
	}

	@Override
	public boolean canParsePage(Page<?> page) {
		System.out.println(		page.getClass().getName() );
		return true;
	}

	public void processPage() {
		//Tutaj kod dla przetwarzania danej strony
		currentPage.getClass().getName();
	}
	
}