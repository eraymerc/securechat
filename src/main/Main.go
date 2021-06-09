package main;

import "fmt";
//import "strings";



func main() {

	fmt.Println("--Eray Mercan--\neraymercan616@gmail.com")
	fmt.Println("--komutlar için yazın, yardım")

	for true{

		var command string;
		fmt.Scanln(&command);
		switch command {
		case "yardım":
			fmt.Println("sunucu {port} : Portta sunucu açar")
		case "sunucu":
			fmt.Println("sexz")
		}
	}
	
}