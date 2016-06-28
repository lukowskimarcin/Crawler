package org.crawler;

public interface IEventListener<T> {
	
	void handle(T event);

}
