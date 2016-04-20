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
	private String port;
	
	public Proxy(String host, String port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	
}
