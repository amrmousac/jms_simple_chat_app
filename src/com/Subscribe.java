package com;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.JMSContext;
import javax.jms.JMSConsumer;
import javax.jms.JMSRuntimeException;
import javax.jms.JMSException;

public class Subscribe implements Runnable {
    private static String TOPIC_NAME = "dev/";
    final String username;

    public Subscribe(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        startSubscribe();
    }

    private void startSubscribe() {
        try {
            JMSContext context = ContextBuilder.connect();
            Destination destination = context.createTopic("topic://" + TOPIC_NAME);
            JMSConsumer subscriber = context.createConsumer(destination);
            while (true) {
                Message receivedMessage = subscriber.receive();
                displayTextMessage(receivedMessage);
                Thread.sleep(1000);
            }
        } catch (JMSRuntimeException jmsex) {
            jmsex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void displayTextMessage(Message receivedMessage) {
        if (receivedMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) receivedMessage;
            try {
                String[] msgData = textMessage.getText().split(":", 2);
                if (msgData.length < 2) {
                    System.out.println("unknown: " + textMessage.getText());
                } else {
                    if (!msgData[0].equals(username)) {
                        System.out.println(textMessage.getText());
                    }
                }
            } catch (JMSException jmsex) {
                jmsex.printStackTrace();
            }

        } else {
            System.out.println("***Message received was not of type TextMessage.***");
        }
    }
}