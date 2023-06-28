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
 * es的dsl文档查询之match all查询所有，也可以称之为 全量查询
 *
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest(classes = HotelApp.class)
public class o1MatchAll {
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
     * 全量查询：查询所有数据
     */
    @Test
    void matchAllTest() throws IOException {
        // 1、准备request
        SearchRequest request = new SearchRequest("hotel");
        // 2、指定哪种查询/构建DSL语句
        request.source().query(QueryBuilders.matchAllQuery());
        // 3、发起请求 获取响应对象
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4、处理响应结果
        // 4.1、获取结果中的Hits
        SearchHits searchHits = response.getHits();
        // 4.2、获取Hits中的total
        long total = searchHits.getTotalHits().value;
        System.out.println("总共获取了 " + total + " 条数据");
        // 4.3、获取Hits中的hits
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            // 4.3.1、获取hits中的source 也就是真正的数据，获取到之后就可以用来处理自己要的逻辑了
            String source = hit.getSourceAsString();
            System.out.println("source = " + source);
        }
    }
}