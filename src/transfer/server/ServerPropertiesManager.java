/**
 * 
 */
package transfer.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Tortoise
 *
 */
public class ServerPropertiesManager {
	private final String propertiesFileName = System.getProperty("user.dir")
			+ "/transfer/server/properties/server.properties";
	private final String propertiesComment = "version 1.0\n server.properties\n for server";
	private String keyServerPort = "server_port";
	private String keyMaxConnectNumbers = "max_connect_numbers";
	private String keyFileSavePath = "file_save_path";
	private int serverPort = 8080;
	private int maxConnectNumbers = 3;
	private String fileSavePath = "./";

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 * 
	 */
	public ServerPropertiesManager() {
	}

	public void saveProperties() throws FileNotFoundException, IOException {
		File file = new File(propertiesFileName);
		if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {

			System.out.println("[error] properties file create failed!");

		}
		Properties properties = new Properties();

		properties.setProperty(getKeyServerPort(), Integer.toString(getServerPort()));
		properties.setProperty(getKeyMaxConnectNumbers(), Integer.toString(getMaxConnectNumbers()));
		properties.setProperty(getKeyFileSavePath(), getFileSavePath());

		properties.store(new FileOutputStream(propertiesFileName), propertiesComment);
	}

	public void loadProperties() throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesFileName));

		setServerPort(Integer.parseInt(properties.getProperty(getKeyServerPort())));
		setMaxConnectNumbers(Integer.parseInt(properties.getProperty(getKeyMaxConnectNumbers())));
		setFileSavePath(properties.getProperty(getKeyFileSavePath()));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerPropertiesManager propertiesManager = new ServerPropertiesManager();
		try {
			propertiesManager.saveProperties();
		} catch (IOException e) {
			System.out.println("[error] " + e.getMessage());
		}
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
	 * @return the keyMaxConnectNumbers
	 */
	public String getKeyMaxConnectNumbers() {
		return keyMaxConnectNumbers;
	}

	/**
	 * @param keyMaxConnectNumbers the keyMaxConnectNumbers to set
	 */
	public void setKeyMaxConnectNumbers(String keyMaxConnectNumbers) {
		this.keyMaxConnectNumbers = keyMaxConnectNumbers;
	}

	/**
	 * @return the keyFileSavePath
	 */
	public String getKeyFileSavePath() {
		return keyFileSavePath;
	}

	/**
	 * @param keyFileSavePath the keyFileSavePath to set
	 */
	public void setKeyFileSavePath(String keyFileSavePath) {
		this.keyFileSavePath = keyFileSavePath;
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

	/**
	 * @return the maxConnectNumbers
	 */
	public int getMaxConnectNumbers() {
		return maxConnectNumbers;
	}

	/**
	 * @param maxConnectNumbers the maxConnectNumbers to set
	 */
	public void setMaxConnectNumbers(int maxConnectNumbers) {
		this.maxConnectNumbers = maxConnectNumbers;
	}

	/**
	 * @return the fileSavePath
	 */
	public String getFileSavePath() {
		return fileSavePath;
	}

	/**
	 * @param fileSavePath the fileSavePath to set
	 */
	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}
}
