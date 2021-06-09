package server;

import java.net.Socket;
import java.util.Scanner;

public class ClientHandler {
    private Socket client;
    private Server server;
    private Scanner input;

    public ClientHandler(Socket client, Server server) {
        this.client = client;
        this.server = server;
    }

    public void run() {
		try {
			inputStream = new Scanner(client.getInputStream());
			while(true)
			{
				if(!inputStream.hasNext())
					return;
				String chatLine = inputStream.nextLine();
				System.out.println(client.getRemoteSocketAddress() + " said: " + chatLine);
				server.sendChatMessageToAll(chatLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
