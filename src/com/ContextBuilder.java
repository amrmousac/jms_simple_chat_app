package com;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

public class ContextBuilder {
    private static String HOST = "192.168.1.13";
    private static int PORT = 1414;
    private static String CHANNEL = "DEV.APP.SVRCONN";
    private static String QMGR = "QM1";
    private static String USER = "app";
    private static String PASSWORD = "VM_2016pass";
    private static String SUBSCRIPTION_NAME = "EduSubscriber";

    public static JMSContext connect() {
        JMSContext context = null;
        try {
            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            JmsConnectionFactory cf = ff.createConnectionFactory();
            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
            cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
            cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "Edu JMS Process");
            cf.setStringProperty(WMQConstants.USERID, USER);
            cf.setStringProperty(WMQConstants.PASSWORD, PASSWORD);
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
            cf.setStringProperty(WMQConstants.CLIENT_ID, SUBSCRIPTION_NAME);
            context = cf.createContext();
        } catch (JMSException jmsex) {
            jmsex.printStackTrace();
        }
        return context;
    }


    public static void close(JMSContext context) {
        try {
            context.close();
        } catch (JMSRuntimeException ex) {
         ex.printStackTrace();
        }
    }
}


