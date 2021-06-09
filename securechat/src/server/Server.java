package server;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class Server {


    private ArrayList<Socket> clients;
    private ServerSocket serverSocket;

    public Server(int port) {

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        clients = new ArrayList<Socket>();
    }

    public void startServer() throws IOException {
        System.out.println("Server aktif!");
        while (true) {
            Socket client = serverSocket.accept();
            clients.add(client);
            System.out.println("Bağlandı : " + client.getRemoteSocketAddress());
            System.out.println("Bağlantı Sayısı : " + clients.size());
            ClientHandler handler = new ClientHandler(client,this);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }

    public synchronized void sendChatMessageToAll(String msg) throws IOException {
		for(Iterator<Socket> it=clients.iterator(); it.hasNext();)
		{
			Socket client = it.next();
			if( !client.isClosed() )
			{
				PrintWriter pw = new PrintWriter(client.getOutputStream());
				pw.println(msg);
				pw.flush();
				//System.out.println("Sent to: " + client.getRemoteSocketAddress());
			}
		}
	}

}
