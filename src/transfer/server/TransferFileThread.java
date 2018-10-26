/**
 * 
 */
package transfer.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Tortoise
 *
 */
public class TransferFileThread implements Runnable {
	private Socket clientSocket;
	private BufferedReader clientDataIn;

	/**
	 * 
	 */
	public TransferFileThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {

		try {
			clientDataIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			/*
			 * String clientCommand = clientDataIn.readLine(); if
			 * (clientCommand.equals("send")) { System.out.println(clientCommand);
			 * receiveFile(); }
			 */
			receiveFile();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public void receiveFile() {
		int bytesRead = 0;
		int totalBytesRead = 0;
		try {

			DataInputStream fileStream = new DataInputStream(clientSocket.getInputStream());
			String fileName = fileStream.readUTF();

			OutputStream outputStream = new FileOutputStream(fileName);

			long tmpfileSize = fileStream.readLong();
			long fileSize = tmpfileSize;
			byte[] buffer = new byte[1024];

			while (tmpfileSize > 0
					&& (bytesRead = fileStream.read(buffer, 0, (int) Math.min(buffer.length, tmpfileSize))) != -1) {
				outputStream.write(buffer, 0, bytesRead);
				totalBytesRead += bytesRead;
				tmpfileSize -= bytesRead;

				int percentageRecv = (int) (totalBytesRead / fileSize) * 100;
				System.out.printf(clientSocket.getInetAddress() + "transferring %2d%% |", percentageRecv);
				for (int i = 0; i < 50; ++i) {
					if (percentageRecv > i * 2) {
						System.out.print("=");
					} else if (percentageRecv == i * 2) {
						System.out.print(">");
					} else {
						System.out.print(" ");
					}
					System.out.print(" | (" + totalBytesRead + "/" + fileSize + ")\r");
				}

			}
			System.out.println();
			outputStream.flush();
			outputStream.close();
			System.out.println("[Info] file transfer completed! total size(bytes)" + totalBytesRead);

		} catch (IOException ex) {
			System.out.println("[error] " + ex.getMessage());
			return;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
