package de.cellent.test.mdb;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class HelloWorldMDBTest {
	
	private static InitialContext context;
	private static QueueConnectionFactory factory;
	private static Queue queue;
	private static QueueConnection con;
	private static QueueSession session;
	private static QueueSender sender;
	
	public static void main(String[] args) {
		HelloWorldMDBTest.init();
		
		HelloWorldMDBTest test = new HelloWorldMDBTest();
		test.testSendMessage();
	}
	
	public void testSendMessage() {
		try {
			TextMessage tMsg = session.createTextMessage();
			tMsg.setText("HelloWorld");
			sender.send(tMsg);
		
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void init() {
		try {
			context = new InitialContext();
			factory = (QueueConnectionFactory) context
					.lookup("jms/RemoteConnectionFactory");
			queue = (Queue) context.lookup("jms/queue/LogOnQueue");
			con = factory.createQueueConnection();
			session = con.createQueueSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			sender = session.createSender(queue);
			sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
