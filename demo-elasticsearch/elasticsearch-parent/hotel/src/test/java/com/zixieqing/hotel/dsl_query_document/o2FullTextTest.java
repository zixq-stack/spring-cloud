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
 * DLS之全文检索查询：利用分词器对用户输入内容分词，然后去倒排索引库中匹配
 * match_query 单字段查询 和 multi_match_query 多字段查询
 *
 * <p>@author       : ZiXieqing</p>
 */


@SpringBootTest(classes = HotelApp.class)
public class o2FullTextTest {
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
     * match_query  单字段查询
     */
    @Test
    void matchQueryTest() throws IOException {
        // 1、准备request
        SearchRequest request = new SearchRequest("hotel");
        // 2、准备DSL
        request.source().query(QueryBuilders.matchQuery("name", "上海"));
        // 3、发送请求，获取响应对象
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
     * multi match 多字段查询    任意一个字段符合条件就算符合查询条件
     */
    @Test
    void multiMatchTest() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().query(QueryBuilders.multiMatchQuery("如家", "name", "business"));
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