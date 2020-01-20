package activeMQ.listener;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by admin on 2019/11/4.
 * 消息消费者
 */
public class ConsumerListener {
    public static void main(String[] args) {
        ConsumerListener consumer = new ConsumerListener();
        consumer.consumerMessage();

    }
    public void consumerMessage(){
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
        try {
            factory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.230.128:61616");
            connection = factory.createConnection();
            //消息的消费者必须启动连接，否则无法处理消息
            connection.start();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("test-listener");
            consumer = session.createConsumer(destination);
            //注册监听器，注册成功后，队列中的消息变化会自动触发监听器代码，接收消息并处理
            consumer.setMessageListener(new MessageListener() {
            /**
             * 监听器一旦注册，永久有效
             * 永久 - consumer线程不关闭
             * 处理消息的方式，只要有消息未处理，自动调用onMessage方法，处理消息
             * 监听器可以注册若干，注册多个监听器，相当于集群
             * ActiveMQ自动的循环调用多个监听器，处理队列中的消息，实现并行处理
             * 处理消息的方法：就是监听方法
             * 监听的事件是：消息，消息未处理
             * message 未处理的消息
             *
             */
                public void onMessage(Message message) {
                    try {
                        //acknowledge方法，就是确认方法，代表consumer已经收到消息，确认后，MQ删除对应的消息
                        message.acknowledge();
                        ObjectMessage om = (ObjectMessage) message;
                        Object data = om.getObject();
                        System.out.println(data);
                    } catch (Exception e) {
                    	// TODO: handle exception
                    }
                }
            });
            //阻塞当前代码，保证listener代码未结束，如果代码结束listener自动关闭
            System.in.read();
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
    }
}
