package com.zixieqing.hotel.dsl_query_document;

import com.alibaba.fastjson.JSON;
import com.zixieqing.hotel.HotelApp;
import com.zixieqing.hotel.pojo.HotelDoc;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

/**
 * DSL之高亮查询
 *
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest(classes = HotelApp.class)
public class o8HighLightTest {
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
     * 高亮查询
     * 返回结果处理不太一样
     */
    @Test
    void highLightTest() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source()
                .query(QueryBuilders.matchQuery("city", "北京"))
                .highlighter(SearchSourceBuilder.highlight()
                        .field("name")  // 要高亮的字段
                        .preTags("<em>")    // 前置HTML标签 默认就是em
                        .postTags("</em>")  // 后置标签
                        .requireFieldMatch(false));     // 是否进行查询字段和高亮字段匹配

        // 发起请求，获取响应对象
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 处理响应结果
        for (SearchHit hit : response.getHits()) {
            String originalData = hit.getSourceAsString();
            HotelDoc hotelDoc = JSON.parseObject(originalData, HotelDoc.class);
            System.out.println("原始数据为：" + originalData);

            // 获取高亮之后的结果
            // key 为要进行高亮的字段，如上为field("name")   value 为添加了标签之后的高亮内容
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (!CollectionUtils.isEmpty(highlightFields)) {
                // 根据高亮字段，获取对应的高亮内容
                HighlightField name = highlightFields.get("name");
                if (name != null) {
                    // 获取高亮内容   是一个数组
                    String highLightStr = name.getFragments()[0].string();
                    hotelDoc.setName(highLightStr);
                }
            }
            System.out.println("hotelDoc = " + hotelDoc);
        }
    }
}