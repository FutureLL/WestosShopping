package cn.e3mall.publish;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TextPublish {

    @Test
    public void publishService() throws Exception{
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");

        while (true){
            System.out.println("已经启动服务  ");
            System.in.read();   // 按任意键退出
            System.out.println("已经关闭服务");
        }
    }
}
