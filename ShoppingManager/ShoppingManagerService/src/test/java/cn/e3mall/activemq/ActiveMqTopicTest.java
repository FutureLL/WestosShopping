package cn.e3mall.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description:  订阅模式发送、接收消息测试
 * @Modified By:
 * @date 2018/12/11 22:49
 */
public class ActiveMqTopicTest {

    /**
     * 生产者：生产消息，发送端。
     * @throws Exception
     */
    @Test
    public void testTopicProducer() throws Exception {
        // 第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
        // brokerURL服务器的ip及端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.111:61616");
        // 第二步：使用ConnectionFactory对象创建一个Connection对象。
        Connection connection = connectionFactory.createConnection();
        // 第三步：开启连接，调用Connection对象的start方法。
        connection.start();
        /**
         * 第四步：使用Connection对象创建一个Session对象。
         *      第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
         *      第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        /**
         * 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个topic对象。
         *      参数：话题的名称。
         */
        Topic topic = session.createTopic("test-topic");
        // 第六步：使用Session对象创建一个Producer对象。
        MessageProducer producer = session.createProducer(topic);
        // 第七步：创建一个Message对象，创建一个TextMessage对象。
        /**
         * TextMessage message = new ActiveMQTextMessage(); message.setText(
         * "hello activeMq,this is my first test.");
         */
        TextMessage textMessage = session.createTextMessage("hello activeMq,this is my topic test");
        // 第八步：使用Producer对象发送消息。
        producer.send(textMessage);
        // 第九步：关闭资源。
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 消费者：接收消息。
     * @throws Exception
     */
    @Test
    public void testTopicConsumer() throws Exception {
        // 第一步：创建一个ConnectionFactory对象。
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.111:61616");
        // 第二步：从ConnectionFactory对象中获得一个Connection对象。
        Connection connection = connectionFactory.createConnection();
        // 第三步：开启连接。调用Connection对象的start方法。
        connection.start();
        // 第四步：使用Connection对象创建一个Session对象。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 第五步：使用Session对象创建一个Destination对象。和发送端保持一致topic，并且话题的名称一致。
        Topic topic = session.createTopic("test-topic");
        // 第六步：使用Session对象创建一个Consumer对象。
        MessageConsumer consumer = session.createConsumer(topic);
        // 第七步：接收消息。
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    String text = null;
                    // 取消息的内容
                    text = textMessage.getText();
                    // 第八步：打印消息。
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // 这里可以更改设置不同的消费端
        System.out.println("topic的消费端。。。。。");
        // 等待接受消息
        System.in.read();
        // 第九步：关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
