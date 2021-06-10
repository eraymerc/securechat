package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.crypto.Cipher;

import encryption.*;

public class Client implements Runnable {

	// why is the ChatClient Multi-threaded?

	private Socket link;
	private PrintWriter outputStream;
	private Scanner inputStream;
	private int port;
	private String nick;
	private int color;

	public static final String ANSI_RESET = (char) 0x1b + "[0m";
	private String[] colors = { (char) 0x1b + "[31m", (char) 0x1b + "[32m", (char) 0x1b + "[33m", (char) 0x1b + "[34m",
			(char) 0x1b + "[35m", (char) 0x1b + "[36m" };

	public Client() throws Exception {
		initialize();
	}

	private void initialize() throws Exception {
		// get server address
		Scanner keyboard = new Scanner(System.in);

		System.out.println("\u001b[33mSunucu ip adresi ne?\u001b[0m");
		System.out.print(">");
		String str = keyboard.next();

		System.out.println("\u001b[33mSunucu portu ne?\u001b[0m");
		System.out.print(">");
		port = keyboard.nextInt();

		System.out.println("\u001b[33mAdın ne?\u001b[0m");
		System.out.print(">");
		nick = keyboard.next();

		System.out.println("\u001b[33mRengin ne?\n\u001b[31m1 : kırmızı \u001b[0m| \u001b[32m2 : yeşil\n\u001b[33m3 : sarı    \u001b[0m| \u001b[34m4 : mavi\n\u001b[35m5 : mor     \u001b[0m| \u001b[36m6 : turkuaz\u001b[0m");
		System.out.print(">");
		color = keyboard.nextInt();

		// connect to server
		InetAddress host = null;
		try {
			host = InetAddress.getByName(str);
		} catch (UnknownHostException e1) {
			System.out.println("\u001b[31mSunucu bulunamadı :\'(\u001b[0m");
		}
		System.out.println("\u001b[32mBağlandın! : " + host.getHostAddress()+"\u001b[0m");

		link = null;
		try {
			link = new Socket(host, port);
			link.setReuseAddress(true);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\u001b[31mbulunamadı\u001b[0m");
		}
		inputStream = new Scanner(link.getInputStream());
		outputStream = new PrintWriter(link.getOutputStream());

		// start new thread to listen from server
		// one runnable, two threads... in which cases is this legal?


		RSA rsa = new RSA();
		System.out.println("\u001b[33mUmumi Anahtar Yollanıyor...\u001b[0m");
		String base64PublicKey = rsa.getPublicKeyBase64();
		
		outputStream.println(base64PublicKey);
		outputStream.flush();
		System.out.println("\u001b[32mUmumi Anahtar Yollandı!\u001b[0m");

		String aesPass = rsa.decrypt(inputStream.nextLine());
		AES.setPassword(aesPass);
		// continuously listen your user input
		Thread t = new Thread(this);
		t.start();
		while (keyboard.hasNextLine()) {
			String msg = keyboard.nextLine();
			
			outputStream.println(AES.aes(Cipher.ENCRYPT_MODE , colors[color - 1] + nick + ANSI_RESET + " : " + msg));
			outputStream.flush();
		}
		keyboard.close();
	}

	@Override
	public void run() {
		while (true) {
			if (inputStream.hasNextLine())
				try {
					System.out.println(AES.aes(Cipher.DECRYPT_MODE,inputStream.nextLine()));
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
}