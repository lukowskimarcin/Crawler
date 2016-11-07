package org.crawler.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.crawler.IWebCrawler;

public class ConsumerImpl implements IConsumer {
	
	private static final Logger log = Logger.getLogger(ConsumerImpl.class.getName());    

	@Inject
	private IWebCrawler crawler;

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private List<ConsumerProcessor> procesors = new ArrayList<ConsumerProcessor>();

	@Override
	public void startConsumption(int threads) {
		for (int i = 0; i < threads; i++) {
			ConsumerProcessor processor = new ConsumerProcessor(crawler.getCompletePages());
			executorService.submit(processor);
			procesors.add(processor);
		}
	}

	@Override
	public void stopConsumption() {
		for (ConsumerProcessor proc : procesors) {
			proc.cancelExecution();
		}
		//executorService.shutdown();
		
		log.info("stopConsumption !!!");
	}

}
