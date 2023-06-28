package com.zixieqing.hotel.dsl_query_document;

import com.zixieqing.hotel.HotelApp;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * 自动补全 completion类型： 这个查询会匹配以用户输入内容开头的词条并返回
 *  参与补全查询的字段 必须 是completion类型
 *  字段的内容一般是用来补全的多个词条形成的数组
 *
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest(classes = HotelApp.class)
public class o10Suggest {
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
    void completionTest() throws IOException {
        // 准备request
        SearchRequest request = new SearchRequest("hotel");
        // 构建DSL
        request.source()
                .suggest(new SuggestBuilder()
                        .addSuggestion(
                                "title_suggest",
                                SuggestBuilders.completionSuggestion("title")
                                        .prefix("s")
                                        .skipDuplicates(true)
                                        .size(10)
                        ));

        // 发起请求，获取响应对象
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 解析响应结果
        // 获取整个suggest对象
        Suggest suggest = response.getSuggest();
        // 通过指定的suggest名字，获取其对象
        CompletionSuggestion titleSuggest = suggest.getSuggestion("title_suggest");
        for (CompletionSuggestion.Entry options : titleSuggest) {
            // 获取每一个options中的test内容
            String context = options.getText().string();
            // 按需求对内容进行处理
            System.out.println("context = " + context);
        }
    }
}