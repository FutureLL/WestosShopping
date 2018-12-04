package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: Solr测试
 * @Modified By:
 * @date 2018/12/3 19:41
 */
public class TestSolrj {

    /**
     * 向索引库增加文档
     * @throws Exception
     */
    @Test
    public void addDocument() throws Exception{

        //创建一个SolrServer对象，创建一个连接。参数solr服务的url
        final String solrUrl="http://localhost:8080/solr/index.html#/new_core";
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                //所有的SolrClient实现允许用户指定的的连接和读取超时与Solr的沟通，这些是提供在创建用户时
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        //创建一个文档对象SolrInputDocument
        SolrInputDocument input = new SolrInputDocument();
        //向文档对象中添加域。文档中必须包含一个id域，所有的域的名称必须在managed-schema中定义
        input.addField("id", "doc01");
        input.addField("item_title", "测试商品01");
        input.addField("item_price", 1000);
        System.out.println("添加完成");
        //把文档写入索引库
        client.add(input);
        //提交
        client.commit();
        //关闭连接
        client.close();
    }

    /**
     * 根据id和query查询语法删除文档
     * @throws Exception
     */
    @Test
    public void deleteDocument(String id) throws Exception{

        //创建一个SolrServer对象，创建一个连接。参数solr服务的url
        final String solrUrl="http://localhost:8080/solr/index.html#/new_core";
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        //删除文档
        client.deleteById(id);
        //根据query查询删除
        client.deleteByQuery("id:doc01");
        //提交
        client.commit();
    }

    /**
     * Solr的简单查询
     * @throws Exception
     */
    @Test
    public void findDocument() throws Exception{

        //创建一个SolrServer对象，创建一个连接。参数solr服务的url
        final String solrUrl="http://localhost:8080/solr/index.html#/new_core";
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        //创建一个SolrQuery对象
        SolrQuery solrQuery=new SolrQuery();
        //设置查询条件
        //solrQuery.setQuery("*:*");
        solrQuery.set("q","*:*");
        //执行查询
        QueryResponse queryResponse=client.query(solrQuery);
        //取查询结果。取查询结果的总记录数
        SolrDocumentList solrDocumentList=queryResponse.getResults();
        System.out.println("查询结果总记录数:"+solrDocumentList.getNumFound());
        //遍历查询结果，从文档中取域的内容
        for (SolrDocument solrDocument:solrDocumentList){
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));

        }
    }

    /**
     * Solr的复杂查询
     * @throws Exception
     */
    public void queryIndexFuza() throws Exception{

        //创建一个SolrServer对象，创建一个连接。参数solr服务的url
        final String solrUrl="http://localhost:8080/solr/index.html#/new_core";
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        //创建一个SolrQuery对象
        SolrQuery solrQuery=new SolrQuery();
        //查询条件
        solrQuery.setQuery("手机");
        //分页条件，起始和结尾
        solrQuery.setStart(0);
        solrQuery.setRows(20);
        //设置默认搜索域，如果不设置，默认为text域
        solrQuery.set("df", "item_title");
        //开启高亮
        solrQuery.setHighlight(true);
        //高亮显示的域
        solrQuery.addHighlightField("item_title");
        //高亮显示的前缀
        solrQuery.setHighlightSimplePre("<em>");
        //高亮显示的后缀
        solrQuery.setHighlightSimplePost("</em>");
        //执行查询
        QueryResponse queryResponse=client.query(solrQuery);
        //取文档列表。取查询结果的总记录数
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
        //遍历文档列表，从文档中取域的内容
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            //取高亮显示
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (list !=null && list.size() > 0 ) {
                //包含了关键字的话，那么就把带有关键字的这个list赋给title变量
                title = list.get( 0);
            } else {
                //如果没有那么从原来的文档中取标题，只不过没有包含我们需要的关键字
                title = (String) solrDocument.get("item_title");
            }
            System.out.println(title);
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
        }
    }
}
