package cn.e3mall.item.listener;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 监听商品添加消息，生成对应的静态页面
 * @Modified By:
 * @date 2018/12/18 18:28
 */
public class HtmlGenListener implements MessageListener {

    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void onMessage(Message message) {
        try {
            //创建一个模板，参考jsp
            //从消息中取商品Id
            TextMessage textMessage= (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            //等待事务提交
            Thread.sleep(1000);
            //根据商品Id查询信息，商品基本信息和商品描述
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            //取商品描述
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            //创建一个数据集，把商品数据封装
            Map data=new HashMap<>();
            data.put("item",item);
            data.put("itemDesc",itemDesc);
            //加载模板对象
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            //创建一个输出流，指定输出的目录及文件名
            Writer out=new FileWriter(new File("F:\\JavaEE32\\freemarker\\item\\" + itemId + ".html"));
            //生成静态页面
            template.process(data,out);
            System.out.println("生成静态页面成功");
            //关闭流
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
