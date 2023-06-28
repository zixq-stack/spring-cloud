package com.zixieqing.hotel.dsl_query_document;

import com.zixieqing.hotel.HotelApp;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * DSL之精确查询：根据精确词条值查找数据，一般是查找keyword、数值、日期、boolean等类型字段，所以 不会 对搜索条件分词
 * range 范围查询 和 term 精准查询
 *
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest(classes = HotelApp.class)
public class o3ExactTest {
    private RestHighLevelClient client;

    @BeforeEach
    void setUp() {
        this.client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://192.168.46.128:9200"))
        );
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }

    /**
     * term 精准查询 根据词条精确值查询
     * 和 match 单字段查询有区别，term要求内容完全匹配
     */
    @Test
    void termTest() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().query(QueryBuilders.termQuery("city", "深圳"));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 处理响应结果，后面都是一样的流程 都是解析json结果而已
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value;
        System.out.println("获取了 " + total + " 条数据");
        for (SearchHit hit : searchHits.getHits()) {
            String dataJson = hit.getSourceAsString();
            System.out.println("dataJson = " + dataJson);
        }
    }

    /**
     * range 范围查询
     */
    @Test
    void rangeTest() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().query(QueryBuilders.rangeQuery("price").lte(250));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 处理响应结果，后面都是一样的流程 都是解析json结果而已
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value;
        System.out.println("获取了 " + total + " 条数据");
        for (SearchHit hit : searchHits.getHits()) {
            String dataJson = hit.getSourceAsString();
            System.out.println("dataJson = " + dataJson);
        }
    }
}
