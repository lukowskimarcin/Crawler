package org.crawler.consumer;

public interface Consumer {
	
	boolean consume();
	
	boolean finishConsumption();

}
