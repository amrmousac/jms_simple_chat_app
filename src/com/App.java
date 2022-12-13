package com;

import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        startChat();
    }

    static void startChat() {
        System.out.print("Please Enter your username: ");
        String username = scanner.nextLine();
        if (!username.isBlank()) {
            Subscribe subscribe = new Subscribe(username);
            Thread subscribeThread = new Thread(subscribe);
            subscribeThread.start();
            PublishMessage publishMessage = new PublishMessage(username);
            Thread SendMessagethread = new Thread(publishMessage);
            SendMessagethread.start();
        } else {
            System.out.println("Please, Enter a valid name");
            startChat();
        }
    }
}
