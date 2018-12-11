package cn.e3mall.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 点到点形式发送、接收消息测试
 * @Modified By:
 * @date 2018/12/11 15:50
 */
public class ActiveMqTest {

    /**
     * 生产者：生产消息，发送端。
     * @throws Exception
     */
    @Test
    public void testQueueProducer() throws Exception{
        //创建一个连接工程对象，需要指定服务的IP以及端口
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.1.111:61616");
        //使用工程对象穿件一个Connection对象
        Connection connection=connectionFactory.createConnection();
        //开启连接，调用Connection对象的start方法
        connection.start();
        //创建一个Session对象
        /**
         * 第一个参数：是否开启事务。如果true开启事务那么第二个参数没有意义。一般不开启事务false
         * 【如果消息没有发出去那就重发，一般和分布式的事务配合使用，数据库的分布式事务：同时提交多个数据库，保证这些事务都在一个事务中完成】
         * 第二个参数：应答模式。一般是自动应答
         * 【接受到了之后是自动应答，还是手动应答】
         *      CLIENT_ACKNOWLEDGE: 手动应答
         *      AUTO_ACKNOWLEDGE:   自动应答
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用Session对象创建一个Destination【目的地对象】对象，两种形式:queue,topic，现在应该使用queue
        Queue queue = session.createQueue("test-queue");
        //使用Session对象创建一个producer对象
        MessageProducer producer = session.createProducer(queue);
        //创建一个Message对象，可以使用TextMessage
        /**
         * TextMessage textMessage=new ActiveMQTextMessage();
         * textMessage.setText("hello ActiveMQ");
         */
        TextMessage textMessage = session.createTextMessage("hello ActiveMQ");
        //发送消息,发送给服务端：tcp://192.168.1.111:61616
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 消费者：接收消息。
     * @throws Exception
     */
    @Test
    public void testQueueConsumer() throws Exception{
        //创建一个ConnectionFactory对象连接MQ服务器
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.1.111:61616");
        //创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用Connection对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个Destination对象。queue对象
        Queue queue = session.createQueue("test-queue");
        //使用Session对象创建一个消费者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //接受消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印结果
                TextMessage textMessage= (TextMessage) message;
                String text = null;
                try {
                    text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // 这里可以更改设置不同的消费端
        System.out.println("Queue的消费端。。。。。");
        //等待接受消息
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
