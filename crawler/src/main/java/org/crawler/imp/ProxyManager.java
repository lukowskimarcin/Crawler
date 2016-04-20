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
	
	public ProxyManager(String filePath) {
		loadProxies(filePath);
	}
	
	public void addProxy(Proxy proxy) {
		proxies.add(proxy);
	}
	
	public void removeProxy(Proxy proxy) {
		proxies.remove(proxy);
	}
	
	/**
	 * Metoda zwraca proxy
	 * @param index : index proxy
	 * @return
	 */
	public Proxy getProxy(int index) {
		if(proxies.size()>=index) {
			return proxies.get(index);
		}
		return null;
	}
	
	/**
	 * Metoda pobiera losowe proxy z puli
	 * @return
	 */
	public Proxy getProxy() {
		Random rn = new Random();
		int index = rn.nextInt((proxies.size()) + 1);
		return getProxy(index);
	}
	
	/**
	 * Metoda testuje czy proxy są aktywne.
	 * Automatycznie usuwa nieaktywne
	 */
	public void testProxies() {
		throw new NotImplementedException();
	}
	
	/**
	 * Wczytuje proxy z pliku, i sprawdza ich aktywność
	 * fromat: [IP]:[PORT]
	 * @param filePath : ściezka do pliku
	 */
	public void loadProxies(String filePath) {
		testProxies();
		throw new NotImplementedException(); 
	}
	
	/**
	 * Zapisuje proxy do wskazanego pliku w formacie [IP]:[PORT]
	 * Jeśli plik juz istnieje zostanie nadpisany
	 * @param filePath : sciezka docelowa pliku
	 * @return
	 */
	public File saveProxies(String filePath) {
		throw new NotImplementedException(); 
	}	
	

}
