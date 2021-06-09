package main;

import java.util.Scanner;
import server.Server;

public class App {

    public static void main(String[] a) {

        System.out.println("--Eray Mercan--\neraymercan616@gmail.com");
        System.out.println("Yardım için \"yardim\" yazın");
        Scanner sc = new Scanner(System.in,"UTF-8");

        loop: while (true) {
            System.out.print(">");
            String command = sc.next();

            String[] args = command.split(" ");

            switch (args[0]) {
                case "yardim":
                    System.out.println("sunucu {port} : Sunucuyu portta başlatır.");
                    System.out.println("dosya {klasör konumu} : Gelen dosyaların atılacağı klasörü seçer.");
                    break;
                case "kapat":
                    break loop;
                case "sunucu":
                    new Server(Integer.parseInt(args[1]));
                default:
                    System.out.println("Geçersiz Komut! Komutlar için \"yardım\" yazın.");
                    break;
            }

        }
        sc.close();
    }

}