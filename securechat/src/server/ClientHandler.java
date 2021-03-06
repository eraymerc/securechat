package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import encryption.AES;
import encryption.RSA;

public class ClientHandler implements Runnable {
	private Socket client;
	private Server server;
	private Scanner inputStream;
	private PrintWriter outputStream;
	


	public ClientHandler(Socket client, Server server) {
		this.client = client;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			inputStream = new Scanner(client.getInputStream());
			String publicKey = inputStream.nextLine();
			
			
			outputStream = new PrintWriter(client.getOutputStream());

			outputStream.println(RSA.encrypt(AES.getSifre(), publicKey));
			outputStream.flush();
			while (true) {
				if (!inputStream.hasNext())
					return;
				String chatLine = inputStream.nextLine();
				System.out.println(client.getRemoteSocketAddress() + " -> " +  AES.aes(2, chatLine));
				server.sendChatMessageToAll(chatLine);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
