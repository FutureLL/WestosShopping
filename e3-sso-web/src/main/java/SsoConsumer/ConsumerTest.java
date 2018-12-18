package SsoConsumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ConsumerTest {

    public static void main(String[] args) {

        //首先启动Zookeeper:zkServer.cmd然后启动服务生产者测试类,最后启动服务消费者的测试类,当控制台输出以下结果,则代表成功。
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/springmvc.xml");
        context.start();

        System.out.println("Dubbo SsoConsumer start...");

        try {
            System.in.read();   // 按任意键退出
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}