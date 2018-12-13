package cn.e3mall.search.message;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 监听商品添加消息，接收消息，将对应的商品信息同步到索引库
 * @Modified By:
 * @date 2018/12/13 21:43
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private ItemMapper itemMapper;

    private String SolrUrl="http://localhost:8080/solr/index.html#/new_core";
    private HttpSolrClient client = new HttpSolrClient.Builder(SolrUrl)
            .withConnectionTimeout(10000)
            .withSocketTimeout(60000)
            .build();

    @Override
    public void onMessage(Message message) {
        try {
            //从这个消息中取商品Id
            TextMessage textMessage= (TextMessage) message;
            String text = textMessage.getText();
            Long itemId=new Long(text);
            //等待事务
            /**
             * 发消息的时候，事务可能还没有提交，消息可能已经到达了Listener，取到了Id之后到数据库中查，事务还没有提交查不到，
             * 事务隔离，所以就会报错，【空指针异常】
             * 解决：两种方案
             *      1、拿到商品之后再下边等待一会，代码如下
             *      2、在ShoppingWeb层的ItemController发送消息，这里事务肯定提交了
             */
            Thread.sleep(100);
            //根据商品Id查询商品信息
            SearchItem searchItem = itemMapper.getItemById(itemId);
            //创建一个文档对象
            SolrInputDocument document=new SolrInputDocument();
            //向文档对象中添加域
            document.addField("id",searchItem.getId());
            document.addField("item_title",searchItem.getTitle());
            document.addField("item_sell_point",searchItem.getSell_point());
            document.addField("item_price",searchItem.getPrice());
            document.addField("item_image",searchItem.getImage());
            document.addField("item_category_name",searchItem.getCategory_name());
            //把文档写入索引库
            client.add(document);
            //提交
            client.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
