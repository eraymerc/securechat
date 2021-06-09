package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {

	fmt.Println("--Eray Mercan--\neraymercan616@gmail.com")
	fmt.Println("--komutlar için yazın, yardım")
	fmt.Print(">")
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {

		command := scanner.Text()

		args := strings.Split(command, " ")

		switch args[0] {
		case "yardım":
			fmt.Println("sunucu {port} : Portta sunucu açar")
		case "sunucu":
			server := Server(1234)
		default:
			fmt.Println("Geçersiz Komut! Komutlar için yazın, yardım.")
		}
		fmt.Print(">")
	}

}
