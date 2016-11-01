package org.crawler.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.crawler.imp.CrawlTask;

public class ConsumerProcessor implements Runnable {
	
	private static final Logger log = Logger.getLogger(ConsumerProcessor.class.getName());   

	private BlockingQueue<CrawlTask> items;

	private volatile boolean keepProcessing = true;

	public ConsumerProcessor(BlockingQueue<CrawlTask> items) {
		this.items = items;
	}

	public void cancelExecution() {
		keepProcessing = false;
	}

	@Override
	public void run() {
		while (keepProcessing || !items.isEmpty()) {
			try {
				CrawlTask task = items.take();
				task.process();

			} catch (InterruptedException ex) {
				log.log(Level.SEVERE, "Thread interrupt", ex);
				Thread.currentThread().interrupt();
			}
		}
	}

}
