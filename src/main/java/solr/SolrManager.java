package solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/12/22.
 */
public class SolrManager {
    public static void main(String[] args) throws Exception {
        SolrManager.query();
    	System.out.println();

    }
    public static void query() throws Exception{
        String baseURL = "http://127.0.0.1:8080/solr/myCore";
        //单机版
        SolrServer server = new HttpSolrServer(baseURL);
        //查询
        SolrQuery query = new SolrQuery();
        //添加查询条件
        query.setQuery("createUserName:*");
        //过滤条件
        query.set("fq", "spdkey:fhsj_input");
        query.set("fq", "sysTime:[* TO 2018-06-30T07:53:32Z]");
        //按时间排序
        query.setSort("sysTime", SolrQuery.ORDER.desc);
        //分页
        query.setStart(0);
        query.setRows(1);
        //默认查询域
        query.set("df", "spdkey");
        //指定查询域
        query.set("fl", "id,spdvalue,createUserName,sysTime");
        //高亮(需要开启高亮)
        query.setHighlight(true);
        //指定高亮域
        query.addHighlightField("createUserName");
        //前缀
        query.setHighlightSimplePre("<span style='color:red'>");
        //后缀
        query.setHighlightSimplePost("</span>");

        //执行查询
        QueryResponse response = server.query(query);
        //获取文档结果集
        SolrDocumentList docs = response.getResults();
        //获取高亮结果集
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //总条数
        long numFound = docs.getNumFound();
        System.out.println("文档数量："+numFound);
        //遍历结果集
        for (SolrDocument doc:docs) {
            System.out.println(doc.get("id"));
            System.out.println(doc.get("spdkey"));
            System.out.println(doc.get("spdvalue"));
            System.out.println(doc.get("sysTime"));
            System.out.println(doc.get("createUserName"));
            System.out.println("------------------------------------");
            Map<String, List<String>> map = highlighting.get(doc.get("id"));
//            System.out.println(map.get("id").get(0));
//            System.out.println(map.get("spdvalue").get(0));
            System.out.println(map.get("sysTime").get(0));
            System.out.println(map.get("createUserName").get(0));
        }
    }
    /*
        id相同就是更新，id不同就是添加
     */
    public static void add() throws Exception{
        String baseURL = "http://127.0.0.1:8080/solr/myCore";
        //单机版
        SolrServer server = new HttpSolrServer(baseURL);
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id","03172d57-21a2-49a6-840e-9dd0ef74");
        doc.setField("spdkey","新增的key");
        doc.setField("spdvalue","忘乎所以");
        doc.setField("sysTime","2018-06-14T01:37:03Z");
        doc.setField("createUserName","狗三");
        server.add(doc);
        server.commit();

    }
    public static void del() throws Exception{
        String baseURL = "http://127.0.0.1:8080/solr/myCore";
        //单机版
        SolrServer server = new HttpSolrServer(baseURL);
        //删除全部
        server.deleteByQuery("*:*");
        server.commit();
    }
}
