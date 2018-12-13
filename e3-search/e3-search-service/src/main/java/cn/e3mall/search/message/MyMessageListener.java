package cn.e3mall.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description:
 * @Modified By:
 * @date 2018/12/13 20:31
 */
public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        //取消息内容
        TextMessage textMessage= (TextMessage) message;
        try {
            String text = textMessage.getText();
            System.out.println(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
