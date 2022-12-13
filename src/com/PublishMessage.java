package com;

import java.util.Scanner;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;

public class PublishMessage implements Runnable {
    private static String TOPIC_NAME = "dev/";

    final String username;

    public PublishMessage(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        publish();
    }

    void publish() {
        try {
            Scanner scanner = new Scanner(System.in);
            JMSContext context = ContextBuilder.connect();
            Destination destination = context.createTopic("topic://" + TOPIC_NAME);
            JMSProducer producer = context.createProducer();
            while (true) {
                final String line = scanner.nextLine();
                System.out.println("you: " + line);
                producer.send(destination, username + ": " + line);
            }

        } catch (JMSRuntimeException e) {
            e.printStackTrace();
        }
    }
}
