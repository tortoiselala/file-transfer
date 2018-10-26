/**
 * 
 */
package transfer.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Tortoise
 *
 */
public class Server {

	private int serverPort = 8080;
	private int maxConnextionNumber = 10;
	private String fileSavePath = "./";

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 * 
	 */
	public Server() {
		try {
			ServerPropertiesManager loadProperties = new ServerPropertiesManager();
			loadProperties.loadProperties();
			this.setServerPort(loadProperties.getServerPort());
			this.setMaxConnextionNumber(loadProperties.getMaxConnectNumbers());
			this.setFileSavePath(loadProperties.getFileSavePath());
		} catch (IOException e) {
			System.out.println("[error] properties file doesn't exits, cause: " + e.getMessage());
			System.out.println("[info] using default setting... server port:" + getServerPort()
					+ " max connection numbers:" + getMaxConnextionNumber());
		}
		System.out.println("[info] server begin.. port: " + getServerPort());
		mainThread();
	}

	public void mainThread() {
		try {
			ServerSocket listener = new ServerSocket(this.getServerPort());
			System.out.println("Connection:" + listener.getInetAddress());

			while (true) {
				Socket socket = listener.accept();
				Thread thread = new Thread(new TransferFileThread(socket));
				thread.start();
			}

		} catch (IOException e) {
			System.out.println("[error] " + e.getMessage());
			return;
		}
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
	 * @return the maxConnextionNumber
	 */
	public int getMaxConnextionNumber() {
		return maxConnextionNumber;
	}

	/**
	 * @param maxConnextionNumber the maxConnextionNumber to set
	 */
	public void setMaxConnextionNumber(int maxConnextionNumber) {
		this.maxConnextionNumber = maxConnextionNumber;
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
	}

}
