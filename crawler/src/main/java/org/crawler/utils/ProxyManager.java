package org.crawler.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa zarządzająca proxy
 * @author Marcin
 *
 */
public class ProxyManager {
	private static final Logger log = Logger.getLogger(ProxyManager.class.getName());   
	private static final int CONNECTION_TIMEOUT = 1000; //1s napolaczenie
	
	private List<Proxy> proxies = new ArrayList<Proxy>();
	
	
	public ProxyManager() {
		log.setLevel(Level.ALL);
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
		int index = rn.nextInt(proxies.size());
		return getProxy(index);
	}
	
	public void initSystemProperty(Proxy proxy) {
		if(proxy.getLogin()!=null && proxy.getPassword()!= null) {
			System.setProperty("http.proxyUser", proxy.getLogin());
			System.setProperty("http.proxyPassword", proxy.getPassword()); 
			
		    final String proxyUser = proxy.getLogin();
		    final String proxyPassword = proxy.getPassword();

		    if (proxyUser != null && proxyPassword != null) {
		        Authenticator.setDefault(
		          new Authenticator() {
		            public PasswordAuthentication getPasswordAuthentication() {
		              return new PasswordAuthentication(
		                proxyUser, proxyPassword.toCharArray()
		              );
		            }
		          }
		        );
		    }
		}
	}	
	 
	
	public boolean testProxy(Proxy proxy){
		try {
			initSystemProperty(proxy);
			
			String proxyHost = proxy.getHost();
			int proxyPort = proxy.getPort();
			java.net.Proxy httpProxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			
			URL url = new URL("https://www.google.com");
			URLConnection con = url.openConnection(httpProxy);
			
			con.setConnectTimeout(CONNECTION_TIMEOUT);
			con.connect();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			// Read it ...
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			    System.out.println(inputLine);
			
			in.close();
			
			
			log.info(proxy + " test SUCCES");
		}	catch (Exception ex) {
			log.info(proxy + " test FAIL : " + ex.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Metoda testuje czy proxy są aktywne.
	 * Automatycznie usuwa nieaktywne
	 */
	public void testProxies() {
		List<Proxy> toRemove = new ArrayList<Proxy>();
		for(Proxy proxy : proxies) {
			if(!testProxy(proxy)) {
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
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			while ((line = br.readLine()) != null) {
				if(line.length()>0) {
					String[] ip = line.split(":");
					addProxy(new Proxy(ip[0], Integer.parseInt(ip[1])));
				}
			}

			br.close();
		} catch (Exception ex) {
			log.log(Level.SEVERE, "loadProxies : " + filePath, ex);
		}

	}
	
	/**
	 * Zapisuje proxy do wskazanego pliku w formacie [IP]:[PORT]
	 * Jeśli plik juz istnieje zostanie nadpisany
	 * @param filePath : sciezka docelowa pliku
	 * @return
	 */
	public File saveProxies(String filePath) {
		try {
			File fout = new File(filePath);
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
			for(Proxy proxy : proxies) {
				bw.write(proxy.toString());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		
			return fout;
		}catch (Exception ex) {
			log.log(Level.SEVERE, "saveProxies : " + filePath, ex);
			return null;
		}
	}	
	
	public static void main(String[] args) {
		//Lista proxy : http://prx.centrump2p.com/
		//https://www.us-proxy.org
		//http://proxylist.hidemyass.com/6#listable
		ProxyManager manager = new ProxyManager();
		//manager.addProxy(new Proxy("112.5.220.199", 80));
		
		manager.addProxy(new Proxy("47.91.78.201", 3128));
		 
		
		manager.testProxies();
	 
	}
	 
}
