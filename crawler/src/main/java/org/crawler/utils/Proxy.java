package org.crawler.utils;

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
	private String login;
	private String password;
	
	public Proxy(String host, int port) {
		this(host, port, null, null);
	}
	
	public Proxy(String host, int port, String login, String password) {
		this.host = host;
		this.port = port;
		this.login = login;
		this.password = password;
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
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return host + ":" + port + " [login: " + login + ", password: " + password + "]";
	}
	
	
}
