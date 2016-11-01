package org.crawler.consumer;

public interface IConsumer {
	
	void startConsumption(int threads);
	
	void stopConsumption();

}
