package server;


import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket client;
    private Server server;
    private Scanner inputStream;

    public static final String ANSI_RESET = "\u001B[0m";
    private String[] colors = {"\u001B[30m","\u001B[31m","\u001B[32m","\u001B[33m","\u001B[34m","\u001B[35m","\u001B[36m","\u001B[37m"};

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
