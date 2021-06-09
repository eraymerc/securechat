package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable {

  // why is the ChatClient Multi-threaded?

	private Socket link;
	private PrintWriter outputStream;
	private Scanner inputStream;
	private int port;
	private String nick;
    private int color;

	public Client() throws IOException {
		initialize();
	}

	private void initialize() throws IOException {
		// get server address
		Scanner keyboard = new Scanner(System.in);

		System.out.println("Sunucu ip adresi ne?");
		String str = keyboard.next();

        System.out.println("Sunucu portu ne?");
		port = keyboard.nextInt();

		System.out.println("Adın ne?");
		nick = keyboard.next();

        System.out.println("Rengin ne?\n1 : kırmızı | 2 : yeşil\n3 : sarı | 4 : mavi\n5 : pembe | 6 : turkuaz");
        color = keyboard.nextInt();

		// connect to server
		InetAddress host = null;
		try {
			host = InetAddress.getByName(str);
		} catch (UnknownHostException e1) {
			System.out.println("Host not found");
		}
		System.out
				.println("You are now connected to: " + host.getHostAddress());

		link = null;
		try {
			link = new Socket(host, port);
			link.setReuseAddress(true);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("not found");
		}
		inputStream = new Scanner(link.getInputStream());
		outputStream = new PrintWriter(link.getOutputStream());

		// start new thread to listen from server
		// one runnable, two threads... in which cases is this legal?
		Thread t = new Thread(this);
		t.start();

		// continuously listen your user input
		while (keyboard.hasNextLine()) {
			String msg = keyboard.nextLine();
			outputStream.println(nick + " says: " + msg);
			outputStream.flush();
		}
	}

	@Override
	public void run() {
		while (true) {
			if (inputStream.hasNextLine())
				System.out.println(inputStream.nextLine());
		}
	}
}