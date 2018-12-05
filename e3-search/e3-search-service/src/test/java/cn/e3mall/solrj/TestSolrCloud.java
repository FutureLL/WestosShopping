package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Li
 * @version 1.0
 * @Description: Solr集群测试
 * @Modified By:
 * @date 2018/12/5 17:51
 */
public class TestSolrCloud {

    /**
     * 简单添加
     * @throws Exception
     */
    @Test
    public void testAddDocument() throws Exception{
        // 第一步：把solrJ相关的jar包添加到工程中。
        // 第二步：创建一个SolrClient对象，需要使用CloudSolrServer子类。构造方法的参数是zookeeper的地址列表。
        // 参数是zookeeper的地址列表
        List<String> zkHost=new ArrayList<>();
        zkHost.add("192.168.229.128:2181");
        zkHost.add("192.168.229.128:2182");
        zkHost.add("192.168.229.128:2183");
        //builder的构造函数需要一个List和一个Optional
        Optional<String> zkChroot = Optional.of("/");
        CloudSolrClient solrClient = new CloudSolrClient.Builder(zkHost,zkChroot)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        // 第三步：需要设置DefaultCollection属性，里边的名字和自己创建的collection名字相同
        solrClient.setDefaultCollection("collection");
        // 第四步：创建一SolrInputDocument对象
        SolrInputDocument document = new SolrInputDocument();
        // 第五步：向文档对象中添加域
        document.addField("id","solrcloud01");
        document.addField("item_title", "测试商品01");
        document.addField("item_price", 123);
        // 第六步：把文档对象写入索引库。
        solrClient.add(document);
        // 第七步：提交。
        solrClient.commit();
    }

    /**
     * 简单查询
     * @throws Exception
     */
    @Test
    public void testQueryDocument() throws Exception{
        // 第一步：把solrJ相关的jar包添加到工程中。
        // 第二步：创建一个SolrClient对象，需要使用CloudSolrServer子类。构造方法的参数是zookeeper的地址列表。
        // 参数是zookeeper的地址列表
        List<String> zkHost=new ArrayList<>();
        zkHost.add("192.168.229.128:2181");
        zkHost.add("192.168.229.128:2182");
        zkHost.add("192.168.229.128:2183");
        //builder的构造函数需要一个List和一个Optional
        Optional<String> zkChroot=Optional.of("/");
        CloudSolrClient solrClient=new CloudSolrClient.Builder(zkHost,zkChroot)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        // 第三步：需要设置DefaultCollection属性，里边的名字和自己创建的collection名字相同
        solrClient.setDefaultCollection("collection");
        // 第四步：创建一个查询对象
        SolrQuery query=new SolrQuery();
        // 第五步：设置查询条件
        query.setQuery("*:*");
        // 第六步：执行查询
        QueryResponse queryResponse = solrClient.query(query);
        // 第七步：取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("总记录数:" + solrDocumentList.getNumFound());
        // 第八步：打印
        for (SolrDocument solrDocument:solrDocumentList){
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("title"));
            System.out.println(solrDocument.get("item_tile"));
            System.out.println(solrDocument.get("item_price"));
        }
    }
}
