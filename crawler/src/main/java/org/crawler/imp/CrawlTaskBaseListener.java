package org.crawler.imp;

import java.util.ArrayList;
import java.util.List;

import org.crawler.IEventListener;
import org.crawler.events.CrawlTaskEvent;

/**
 * Podstawowe zdarzenia obslugiwane przez przetwarzane strony
 * @author Marcin
 *
 * @param <T>
 */
public class CrawlTaskBaseListener<T> {
	
	protected List<IEventListener<CrawlTaskEvent<T>>> onPageCrawlingStartListener;
	protected List<IEventListener<CrawlTaskEvent<T>>> onPageCrawlingCompletedListener;
	protected List<IEventListener<CrawlTaskEvent<T>>> onPageCrawlingFailedListener;
	protected List<IEventListener<CrawlTaskEvent<T>>> onPageAlreadyVisitedListener;
	protected List<IEventListener<CrawlTaskEvent<T>>> onPageProcessingProgressListener;

	
	public List<IEventListener<CrawlTaskEvent<T>>> getOnPageCrawlingStartListener() {
		return onPageCrawlingStartListener;
	}
	public void addOnPageCrawlingStartListener(IEventListener<CrawlTaskEvent<T>> listener) {
		if(listener!=null) {
			if(onPageCrawlingStartListener==null) {
				onPageCrawlingStartListener = new ArrayList<IEventListener<CrawlTaskEvent<T>>>();
			}
			onPageCrawlingStartListener.add(listener);
		}
	}
	
	public List<IEventListener<CrawlTaskEvent<T>>> getOnPageCrawlingCompletedListener() {
		return onPageCrawlingCompletedListener;
	}
	public void addOnPageCrawlingCompletedListener(IEventListener<CrawlTaskEvent<T>> listener) {
		if(listener!=null) {
			if(onPageCrawlingCompletedListener==null) {
				onPageCrawlingCompletedListener = new ArrayList<IEventListener<CrawlTaskEvent<T>>>();
			}
			onPageCrawlingCompletedListener.add(listener);
		}
	}
	 
	public List<IEventListener<CrawlTaskEvent<T>>> getOnPageCrawlingFailedListener() {
		return onPageCrawlingFailedListener;
	}
	public void addOnPageCrawlingFailedListener(IEventListener<CrawlTaskEvent<T>> listener) {
		if(listener!=null) {
			if(onPageCrawlingFailedListener==null) {
				onPageCrawlingFailedListener = new ArrayList<IEventListener<CrawlTaskEvent<T>>>();
			}
			onPageCrawlingFailedListener.add(listener);
		}
	}
	
	public List<IEventListener<CrawlTaskEvent<T>>> getOnAlreadyVisitedListener() {
		return onPageAlreadyVisitedListener;
	}
	public void addOnAlreadyVisitedListener(IEventListener<CrawlTaskEvent<T>> listener) {
		if(listener!=null) {
			if(onPageAlreadyVisitedListener==null) {
				onPageAlreadyVisitedListener = new ArrayList<IEventListener<CrawlTaskEvent<T>>>();
			}
			onPageAlreadyVisitedListener.add(listener);
		}
	}
	
	public List<IEventListener<CrawlTaskEvent<T>>> getOnPageProcessingProgressListener() {
		return onPageProcessingProgressListener;
	}
	public void addOnPageProcessingProgressListener(IEventListener<CrawlTaskEvent<T>> listener) {
		if(listener!=null) {
			if(onPageProcessingProgressListener==null) {
				onPageProcessingProgressListener = new ArrayList<IEventListener<CrawlTaskEvent<T>>>();
			}
			onPageProcessingProgressListener.add(listener);
		}
	}
	
}
