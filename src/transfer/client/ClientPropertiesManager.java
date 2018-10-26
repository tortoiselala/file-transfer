package transfer.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import transfer.server.ServerPropertiesManager;

public class ClientPropertiesManager {
	private final String propertiesFileName = System.getProperty("user.dir")
			+ "/transfer/server/properties/client.properties";
	private final String propertiesComment = "version 1.0\n client.properties\n for client";
	private String keyServerIP = "server_ip";
	private String keyServerPort = "server_port";
	private String serverIP = "54.179.173.217" /* "localhost" */;
	private int serverPort = 8080;

	public ClientPropertiesManager() {

	}

	public void saveProperties() throws FileNotFoundException, IOException {
		File file = new File(propertiesFileName);
		if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
			System.out.println("[error] properties file create failed!");
		}
		Properties properties = new Properties();

		properties.setProperty(getKeyServerIP(), getServerIP());
		properties.setProperty(getKeyServerPort(), Integer.toString(getServerPort()));

		properties.store(new FileOutputStream(propertiesFileName), propertiesComment);
	}

	public void loadProperties() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesFileName));

		setServerIP(properties.getProperty(getKeyServerIP(), getServerIP()));
		setServerPort(Integer.parseInt(properties.getProperty(getKeyServerPort()), getServerPort()));
	}

	public static void main(String[] args) {
		ServerPropertiesManager propertiesManager = new ServerPropertiesManager();
		try {
			propertiesManager.saveProperties();
			System.out.println("[info] save properties..");
		} catch (IOException e) {
			System.out.println("[error] " + e.getMessage());
		}

		System.out.println("[info] end..");
	}

	/**
	 * @return the keyServerIP
	 */
	public String getKeyServerIP() {
		return keyServerIP;
	}

	/**
	 * @param keyServerIP the keyServerIP to set
	 */
	public void setKeyServerIP(String keyServerIP) {
		this.keyServerIP = keyServerIP;
	}

	/**
	 * @return the keyServerPort
	 */
	public String getKeyServerPort() {
		return keyServerPort;
	}

	/**
	 * @param keyServerPort the keyServerPort to set
	 */
	public void setKeyServerPort(String keyServerPort) {
		this.keyServerPort = keyServerPort;
	}

	/**
	 * @return the serverIP
	 */
	public String getServerIP() {
		return serverIP;
	}

	/**
	 * @param serverIP the serverIP to set
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * @param serverPort the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

}
