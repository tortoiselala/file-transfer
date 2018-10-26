/**
 * 
 */
package transfer.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Tortoise
 *
 */
public class Client {
	private String serverIP = "54.179.173.217";
	private int serverPort = 8080;
	private Socket socket;
	Scanner scanner;

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 * 
	 */
	public Client() {
	}

	public Client(boolean flag) {
		if (flag) {
			try {
				ClientPropertiesManager loadProperties = new ClientPropertiesManager();
				loadProperties.loadProperties();
				this.setServerIP(loadProperties.getServerIP());
				this.setServerPort(loadProperties.getServerPort());
			} catch (IOException e) {
				System.out.println("[error] properties file doesn't exits, cause: " + e.getMessage());
				System.out.println("[info] using default setting... IP:" + getServerIP() + " port:" + getServerPort());
			}
		}
	}

	public Client(String serverIP, int serverPort) {
		setServerIP(serverIP);
		setServerPort(serverPort);
	}

	public void getConnection() throws UnknownHostException, IOException {
		socket = new Socket(getServerIP(), getServerPort());
		System.out.println("[info] Successfully connected to IP:" + getServerIP() + " port:" + getServerPort());
	}

	public void printMenu() {
		System.out.println("[info]  menu info to be fixed..");
	}

	public void mainLogic() {
		this.scanner = new Scanner(System.in);
		printMenu();
		try {
			getConnection();
			while (true) {
				System.out.print("[info] enter command:");
				String cmd = scanner.nextLine();
				switch (cmd) {
				case "send":
					sendFile();
					break;
				case "get":

					break;
				case "quit":
				case "q":
				case "exit":
					System.out.println("[info] exit transfer...");
					return;
				default:
					System.out.println("[error] wrong command! try again...");
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("[error] connection failed, cause: " + e.getMessage());
			return;
		}

	}

	public void sendFile() {
		System.out.print("[info] file name: ");
		String fileName = scanner.nextLine();
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("[error] file doesn't exist..");
			return;
		}
		byte[] byteArray = new byte[(int) file.length()];

		try {
			DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			dataInputStream.readFully(byteArray, 0, byteArray.length);
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

			System.out.println("[info] uploading " + fileName + "...");
			dataOutputStream.writeUTF(file.getName());
			dataOutputStream.writeLong(byteArray.length);
			dataOutputStream.write(byteArray, 0, byteArray.length);
			dataOutputStream.flush();

			dataInputStream.close();
			dataOutputStream.close();
			System.out.println(
					"[info] File " + fileName + " send to server. IP:" + getServerIP() + " port:" + getServerPort());
		} catch (FileNotFoundException e) {
			System.out.println("[error] file stream create failed! cause:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("[error] file stream load failed! cause:" + e.getMessage());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client(true);
		client.mainLogic();
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
