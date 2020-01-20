package activeMQ.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by admin on 2019/11/4.
 * 消息生产者
 */
public class TopicProducer {
    public static void main(String[] args) {
        TopicProducer producer = new TopicProducer();
        producer.sendTextMessage("测试ActiveMQ");
        System.out.println();

    }
    public void sendTextMessage(String data){
        //链接工厂
        ConnectionFactory factory = null;
        //链接
        Connection connection = null;
        //目的地
        Destination destination = null;
        //会话
        Session session = null;
        //消息发送者
        MessageProducer producer = null;
        //消息对象
        Message message = null;
        try {
            factory = new ActiveMQConnectionFactory("guest","guest","tcp://192.168.230.128:61616");
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            //创建目的地 参数是目的地的名称，是目的地的唯一标识
            destination = session.createTopic("test-topic");
            /**
             * 通过会话对象，创建消息的发送者producer
             * 创建的消息发送者，发送的消息一定要指定目的地
             */
            producer = session.createProducer(destination);
            //创建文本消息对象，作为具体数据内容的载体
            message = session.createTextMessage(data);
            //使用producer发送消息到ActiveMQ中的目的地
            producer.send(message);
            System.out.println("消息已发送");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(producer != null){
                try {
                    producer.close();
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
    }
}
