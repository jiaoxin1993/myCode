package activeMQ.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by admin on 2019/11/4.
 * 消息消费者
 */
public class TopicConsumer {
    public static void main(String[] args) {
        TopicConsumer consumer = new TopicConsumer();
        String resultCode = consumer.receiveTextMessage();
    	System.out.println(resultCode);

    }
    public String receiveTextMessage(){
        String resultCode = "";
        //链接工厂
        ConnectionFactory factory = null;
        //链接
        Connection connection = null;
        //会话
        Session session = null;
        //目的地
        Destination destination = null;
        //消息的消费者
        MessageConsumer consumer = null;
        //消息对象
        Message message = null;
        try {
            factory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.230.128:61616");
            connection = factory.createConnection();
            //消息的消费者必须启动连接，否则无法处理消息
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("test-topic");
            consumer = session.createConsumer(destination);
            //获取队列的消息 receive方法是一个主动获取消息的方法，执行一次，拉取一次
            message = consumer.receive();
            resultCode = ((TextMessage)message).getText();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(consumer != null){
                try {
                    consumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(session != null){
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultCode;

    }

}
