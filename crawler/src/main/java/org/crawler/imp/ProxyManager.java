package org.crawler.imp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
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
	
	
	public ProxyManager() {
		
	}
	
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
		
		List<Proxy> toRemove = new ArrayList<Proxy>();
		for(Proxy proxy : proxies) {
			try {
				String proxyHost = proxy.getHost();
				int proxyPort = proxy.getPort();
				SocketAddress addr = new InetSocketAddress(proxyHost, proxyPort);
				java.net.Proxy httpProxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, addr);
				
				URLConnection urlConn = null;
				BufferedReader reader = null;
				String response = "";
				String output = "";
				URL url = new URL("https://www.google.pl");
				//Pass the Proxy instance defined above, to the openConnection() method
				urlConn = url.openConnection(httpProxy); 
				
				urlConn.connect();
				reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
				response = reader.readLine();
				while (response!=null) {
				    output+= response;
				    response = reader.readLine();
				}   
				System.out.println("Output: " + output);
				
			}	catch (Exception ex) {
				ex.printStackTrace();
				toRemove.add(proxy);
			}
		}
		proxies.removeAll(toRemove);
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
	
	
	
	
	public static void main(String[] args) {
		//Lista proxy : http://prx.centrump2p.com/
		
		ProxyManager manager = new ProxyManager();
		manager.addProxy(new Proxy("87.98.239.19", 80)); //OK
		manager.addProxy(new Proxy("87.98.239.192", 820));
		
		manager.testProxies();
		
	}
	 

}
