package org.crawler.imp;

public class DetailCrawler extends Crawler<DetailPage> {

	public DetailCrawler(Manager manager) {
		super(manager);
	}

	@Override
	public boolean canParsePage(PageTask<?> page) {
		String name = page.getClass().getName();
		boolean test =  name.contains("DetailPage");
		return test;
	}

	public void processPage() {
		//Tutaj kod dla przetwarzania danej strony
		currentPage.getClass().getName();
	}

	public DetailPage call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}