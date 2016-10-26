package org.crawler.events;

import org.crawler.imp.CrawlTask;
import org.crawler.imp.PageTask;

public class CrawlTaskEvent {
	
	private CrawlTask task;
	
	private PageTask page;
	
	//czas w ms
	private long time;
	
	private double progress;
	
	private String errorMessage;

	public CrawlTaskEvent(CrawlTask task, PageTask page, long time) {
		this.task = task;
		this.page = page;
		this.time = time;
	}
	
	
	public CrawlTaskEvent(CrawlTask task, PageTask page) {
		this.task = task;
		this.page = page;
		this.time = -1;
	}


	public CrawlTask getTask() {
		return task;
	}

	public PageTask getPage() {
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
