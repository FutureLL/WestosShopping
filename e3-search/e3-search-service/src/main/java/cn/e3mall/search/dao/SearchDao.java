package cn.e3mall.search.dao;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: 商品搜索Dao
 * @Modified By:
 * @date 2018/12/5 0:14
 */

@Repository
public class SearchDao {

    /**
     * 在不使用spring进行配置solr的情况下可以这么写单机版的solr
     * final String solrUrl="http://localhost:8080/solr/index.html#/new_core";
     *     HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl)
     *             .withConnectionTimeout(10000)
     *             .withSocketTimeout(60000)
     *             .build();
     */



    /**
     * 在springxml中进行了Solr单机版配置
     * @Autowired
     * HttpSolrClient solrClient;
     */


    /**
     * 我们现在使用集群版
     * 在springxml中进行了配置
     */
    @Autowired
    CloudSolrClient solrClient;

    /**
     * 根据查询条件查询索引库
     * @param query
     * @return
     */
    public SearchResult search(SolrQuery query) throws Exception{
        //根据query查询索引库
        QueryResponse queryResponse=solrClient.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        //取查询结果总记录数
        long numFound = solrDocumentList.getNumFound();
        SearchResult searchResult=new SearchResult();
        searchResult.setRecordCount(numFound);
        //取商品列表，需要取高亮显示
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        List<SearchItem> itemList=new ArrayList<>();
        for (SolrDocument solrDocument:solrDocumentList){
            SearchItem item=new SearchItem();
            item.setId((String) solrDocument.get("id"));
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((Long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            //取高亮显示
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size() > 0) {
                title = list.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            item.setTitle((String) solrDocument.get("item_title"));
            //添加到上商品列表
            itemList.add(item);
        }
        searchResult.setItemList(itemList);
        //返回结果
        return searchResult;
    }
}
