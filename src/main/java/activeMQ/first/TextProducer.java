package activeMQ.first;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by admin on 2019/11/4.
 * 消息生产者
 * 发送消息到ActiveMQ中，具体的消息内容为参数信息
 * 开发jsm代码相关过程中，使用的接口类型都是Javax.jms包下的类型
 * @param  --消息内容
 */
public class TextProducer {
    public static void main(String[] args) {
        TextProducer producer = new TextProducer();
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
            /**创建链接工厂，链接ActiveMQ服务的链接工厂
             * 创建工厂，构造方法有三个参数，分别是用户名、密码、链接地址
             * 无参构造，有默认的链接地址，本地连接 localhost
             * 单参数构造，无验证模式的，没有用户的认证
             * 三参数构造， 有认证+指定地址，默认端口是61616，从ActiveMQ的conf/activemq.xml配置文件中查看
            */
            factory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.230.128:61616");
            /**
             * 通过工厂，创建连接对象
             * 创建连接对象的方法有重载，其中有createConnection(String username,Stirng password);
             * 可以在创建连接工厂是，只传递连接地址，不传递用户信息
             */
            connection = factory.createConnection();
            /**
             * 建议启动连接，消息的发送者不是必须启动连接，消息的消费者必须启动连接
             * Producer在发送消息的时候，会检查是否启动连接，如果未启动，自动启动
             * 如果有特殊的配置，建议配置完毕后，再启动连接
             */
            connection.start();
            /**
             * 通过连接对象，创建会话对象
             * 创建会话的时候，必须传递两个参数，分别代表是否支持事务和如何确认消息处理
             * transacted - 是否支持事务，数据类型是boolean，true--支持 false--不支持
             * true -- 支持事务，第二个参数对Producer来说默认无效，建议传递的数据都是Session.SESSION_TRANSACTED
             * false - 不支持事务，常用参数，第二个参数必须传递，且必须有效
             *  Session
             *     AUTO_ACKNOWLEDGE 自动确认消息，消息的消费者处理消息后，自动确认，常用 （商业开发不建议使用）
             *     CLIENT_ACKNOWLEDGE 客户端手动确认，消息的消费者处理后，必须手工确认
             *     DUPS_OK_ACKNOWLEDGE 有副本的客户端手动确认
             *          一个消息可以多次处理
             *          可以降低Session的消耗，在可以容忍重复消息时使用（不推荐）
             *
             */
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            //创建目的地 参数是目的地的名称，是目的地的唯一标识
            destination = session.createQueue("first-mq");
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
