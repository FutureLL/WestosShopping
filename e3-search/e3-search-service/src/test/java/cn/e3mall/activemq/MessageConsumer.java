package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description:
 * @Modified By:
 * @date 2018/12/13 21:21
 */
public class MessageConsumer {

    @Test
    public void msgConsumer() throws Exception{
        //初始化一个Spring容器
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //等待
        System.in.read();
    }
}
