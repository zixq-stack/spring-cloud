package com.zixieqing.hotel.dsl_query_document;

import com.zixieqing.hotel.HotelApp;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * DSL之模糊查询
 *
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest(classes = HotelApp.class)
public class o6FuzzyTest {
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
     * 模糊查询
     */
    @Test
    void fuzzyTest() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        // fuzziness(Fuzziness.ONE)     表示的是：字符误差数  取值有：zero、one、two、auto
        // 误差数  指的是：fuzzyQuery("name","深圳")这里面匹配的字符的误差    可以有几个字符不一样，多/少几个字符？
        request.source().query(QueryBuilders.fuzzyQuery("name", "深圳").fuzziness(Fuzziness.ONE));
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
