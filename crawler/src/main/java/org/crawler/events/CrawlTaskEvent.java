package org.crawler.events;

import org.crawler.ICrawlTask;
import org.crawler.imp.PageWrapper;

public class CrawlTaskEvent<T> {
	
	private ICrawlTask<T> task;
	
	private PageWrapper<T> page;
	
	//czas w ms
	private long time;
	
	private double progress;
	
	private String errorMessage;

	public CrawlTaskEvent(ICrawlTask<T> task, PageWrapper<T> page, long time) {
		this.task = task;
		this.page = page;
		this.time = time;
	}
	
	
	public CrawlTaskEvent(ICrawlTask<T> task, PageWrapper<T> page) {
		this.task = task;
		this.page = page;
		this.time = -1;
	}


	public ICrawlTask<T> getTask() {
		return task;
	}

	public PageWrapper<T> getPage() {
		return page;
	}

	public long getTimeMillis() {
		return time;
	}
	
	public String getTimeSeconds() {
		return String.format("%.2f", time * 1.0 / 1000);
	}

	public double getProgress() {
		return progress;
	}


	public void setProgress(double progress) {
		this.progress = progress;
	}


	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
