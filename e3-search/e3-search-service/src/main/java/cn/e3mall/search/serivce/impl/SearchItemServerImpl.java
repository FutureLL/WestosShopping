package cn.e3mall.search.serivce.impl;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.serivce.SearchItemServer;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 索引库维护的Service
 * @Modified By:
 * @date 2018/12/3 21:36
 */

@Service
public class SearchItemServerImpl implements SearchItemServer {

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 连接到索引库
     */
    private String SolrUrl="http://localhost:8080/solr/index.html#/new_core";
    private HttpSolrClient  client = new HttpSolrClient.Builder(SolrUrl)
            .withConnectionTimeout(10000)
            .withSocketTimeout(60000)
            .build();

    @Override
    public E3Result importAllItem() {
        //try/catch快捷键: Ctrl+Alt+t
        try {
            //查询闪屏列表
            List<SearchItem> itemList = itemMapper.getItemList();
            //遍历商品列表
            for (SearchItem searchItem:itemList){
                //创建文档对象
                SolrInputDocument document=new SolrInputDocument();
                //向文档对象中添加域
                document.addField("id",searchItem.getId());
                document.addField("item_title",searchItem.getTitle());
                document.addField("item_sell_point",searchItem.getSell_point());
                document.addField("item_price",searchItem.getPrice());
                document.addField("item_image",searchItem.getImage());
                document.addField("item_category_name",searchItem.getCategory_name());
                //把文档对象写入索引库
                client.add(document);
            }

            //提交
            client.commit();
            //返回导入成功
            return E3Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500,"数据导入时发生异常");
        }
    }
}
