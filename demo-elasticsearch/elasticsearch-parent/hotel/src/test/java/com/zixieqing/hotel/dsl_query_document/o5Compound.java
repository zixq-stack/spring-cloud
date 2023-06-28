package com.zixieqing.hotel.dsl_query_document;

import com.zixieqing.hotel.HotelApp;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * DSL之复合查询：基础DSL查询进行组合，从而得到实现更复杂逻辑的复合查询
 * function_score 算分函数查询
 *
 * bool布尔查询
 *  must     必须匹配每个子查询   即：and “与”   参与score算分
 *  should   选择性匹配子查询    即：or “或”    参与score算分
 *  must not 必须不匹配         即：“非"       不参与score算分
 *  filter   必须匹配           即：过滤        不参与score算分
 *
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest(classes = HotelApp.class)
public class o5Compound {
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
     * bool布尔查询
     *  must     必须匹配每个子查询   即：and “与”   参与score算分
     *  should   选择性匹配子查询    即：or “或”    参与score算分
     *  must not 必须不匹配         即：“非"       不参与score算分
     *  filter   必须匹配           即：过滤        不参与score算分
     */
    @Test
    void boolTest() throws IOException {
        SearchRequest request = new SearchRequest("indexName");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 构建must   即：and 与
        boolQueryBuilder.must(QueryBuilders.termQuery("city", "北京"));
        // 构建should   即：or 或
        boolQueryBuilder.should(QueryBuilders.multiMatchQuery("速8", "brand", "name"));
        // 构建must not   即：非
        boolQueryBuilder.mustNot(QueryBuilders.rangeQuery("price").gte(250));
        // 构建filter   即：过滤
        boolQueryBuilder.filter(QueryBuilders.termQuery("starName", "二钻"));

        request.source().query(boolQueryBuilder);
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