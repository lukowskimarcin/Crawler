package org.crawler.imp;

import org.crawler.Crawler;
import org.crawler.ICrawler;

public class DetailCrawler extends Crawler<DetailPage> implements ICrawler<NormalPage> {

	public DetailCrawler(Manager manager) {
		super(manager);
	}

	@Override
	public boolean canParsePage(PageTask<?> page) {
		String name = page.getClass().getName();
		boolean test =  name.contains("DetailPage");
		return test;
	}

	public DetailPage processPage() {
		//Tutaj kod dla przetwarzania danej strony
	}

	public DetailPage call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}