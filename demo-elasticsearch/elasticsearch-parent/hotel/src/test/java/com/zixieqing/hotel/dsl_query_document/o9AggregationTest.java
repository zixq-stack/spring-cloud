package com.zixieqing.hotel.dsl_query_document;

import com.zixieqing.hotel.HotelApp;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * 数据聚合 aggregation 可以让我们极其方便的实现对数据的统计、分析、运算
 * 桶（Bucket）聚合：用来对文档做分组
 *      TermAggregation：按照文档字段值分组，例如按照品牌值分组、按照国家分组
 *      Date Histogram：按照日期阶梯分组，例如一周为一组，或者一月为一组
 *
 *  度量（Metric）聚合：用以计算一些值，比如：最大值、最小值、平均值等
 *      Avg：求平均值
 *      Max：求最大值
 *      Min：求最小值
 *      Stats：同时求max、min、avg、sum等
 *
 *  管道（pipeline）聚合：其它聚合的结果为基础做聚合
 *
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest(classes = HotelApp.class)
public class o9AggregationTest {
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

    @Test
    void aggregationTest() throws IOException {
        // 获取request
        SearchRequest request = new SearchRequest("indexNAME");
        // 组装DSL
        request.source()
                .size(0)
                .query(QueryBuilders
                        .rangeQuery("price")
                        .lte(250)
                )
                .aggregation(AggregationBuilders
                        .terms("brandAgg")
                        .field("brand")
                        // aggregation(String path, boolean asc)
                        .order(BucketOrder.aggregation("scoreAgg.avg",true))
                        .subAggregation(AggregationBuilders
                                .stats("scoreAgg")
                                .field("score")
                        )
                );

        // 发送请求，获取响应
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 处理响应结果
        System.out.println("response = " + response);
        // 获取全部聚合结果对象 getAggregations
        Aggregations aggregations = response.getAggregations();
        // 根据聚合名 获取其聚合对象
        Terms brandAgg = aggregations.get("brandAgg");
        // 根据聚合类型 获取对应聚合对象
        List<? extends Terms.Bucket> buckets = brandAgg.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            // 根据key获取其value
            String value = bucket.getKeyAsString();
            // 将value根据需求做处理
            System.out.println("value = " + value);
        }
    }
}