package org.crawler.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.NotImplementedException;

/**
 * Klasa zarządzająca proxy
 * @author Marcin
 *
 */
public class ProxyManager {
	private List<Proxy> proxies = new ArrayList<Proxy>();
	
	
	public ProxyManager(List<Proxy> list) {
		proxies = list;
	}
	
	public ProxyManager(File file) {
		throw new  NotImplementedException();
	}
	
	public void addProxy(Proxy proxy) {
		proxies.add(proxy);
	}
	
	public void removeProxy(Proxy proxy) {
		proxies.remove(proxy);
	}
	
	public Proxy getProxy(int index) {
		if(proxies.size()>=index) {
			return proxies.get(index);
		}
		return null;
	}
	
	public Proxy getProxy() {
		Random rn = new Random();
		int index = rn.nextInt((proxies.size()) + 1);
		return getProxy(index);
	}
	
	

}
