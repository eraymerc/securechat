package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private int port;
    private ArrayList<Socket> clients;
    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;

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
}
