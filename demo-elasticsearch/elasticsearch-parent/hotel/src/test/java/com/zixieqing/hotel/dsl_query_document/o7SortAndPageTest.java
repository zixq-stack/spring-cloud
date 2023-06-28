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
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * DSL之排序和分页
 *
 * <p>@author       : ZiXieqing</p>
 */


@SpringBootTest(classes = HotelApp.class)
public class o7SortAndPageTest {
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
     * sort 排序查询
     */
    @Test
    void sortTest() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source()
                .query(QueryBuilders.matchAllQuery())
                .sort("price", SortOrder.ASC);
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
     * page 分页查询
     */
    @Test
    void pageTest() throws IOException {
        int page = 2, size = 20;
        SearchRequest request = new SearchRequest("hotel");
        request.source()
                .query(QueryBuilders.matchAllQuery())
                .from((page - 1) * size).size(size);

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
