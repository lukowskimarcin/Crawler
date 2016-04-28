package org.crawler.imp;

import java.io.Serializable;

/**
 * Proxy serwerów
 * @author Marcin
 *
 */
public class Proxy implements Serializable {
	private static final long serialVersionUID = 925740658693615705L;
	private String host;
	private int port;
	
	public Proxy(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public String toString() {
		return host + ":" + port;
	}
	
	
}
