package main;

import java.util.Scanner;
import java.io.PrintStream;

import client.Client;
import server.Server;

public class App {

    public static void main(String[] a) throws Exception {
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        System.out.println("\u001b[33m--Eray Mercan--\neraymercan616@gmail.com");
        System.out.println("Yardım için \"yardim\" yazın\u001b[0m");

        Scanner sc = new Scanner(System.in);

        loop: while (true) {
            System.out.print(">");
            String command = sc.nextLine();

            String[] args = command.split(" ");

            switch (args[0]) {
                case "yardim":
                    System.out.println("kapat : programı kapatır.");
                    System.out.println("baglan : sunucuya bağlanır.");
                    System.out.println("sunucu {port} : Sunucuyu portta başlatır.");
                    System.out.println("dosya {klasör konumu} : Gelen dosyaların atılacağı klasörü seçer.");
                    
                    break;
                case "kapat":
                    break loop;
                case "sunucu":
                    new Server(Integer.parseInt(args[1]));
                    break loop;
                case "baglan":
                    new Client();
                    break;
                default:
                    System.out.println("\u001b[31mGeçersiz Komut! Komutlar için \"yardim\" yazın.\u001b[0m");
                    break;
            }

        }
        sc.close();
    }

}
