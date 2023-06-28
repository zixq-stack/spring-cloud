package com.zixieqing.hotel;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.zixieqing.hotel.constant.MappingConstant.mappingContext;

/**
 * elasticsearch的索引库测试
 * 规律：esClient.indices().xxx(xxxIndexRequest(IndexName), RequestOptions.DEFAULT)
 *      其中 xxx 表示要对索引进行得的操作，如：create、delete、get、flush、exists.............
 *
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest(classes = HotelApp.class)
public class o1IndexTest {
    private RestHighLevelClient client;

    @BeforeEach
    void setUp() {
        this.client = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://192.168.46.128:9200")));
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }

    /**
     * 创建索引 并 创建字段的mapping映射关系
     */
    @Test
    void createIndexAndMapping() throws IOException {
        // 1、创建索引
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        // 2、创建字段的mapping映射关系   参数1：编写的mapping json字符串  参数2：采用的文本类型
        request.source(mappingContext, XContentType.JSON);
        // 3、发送请求 正式创建索引库与mapping映射关系
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        // 查看是否创建成功
        System.out.println("response.isAcknowledged() = " + response.isAcknowledged());
        // 判断指定索引库是否存在
        boolean result = client.indices().exists(new GetIndexRequest("hotel"), RequestOptions.DEFAULT);
        System.out.println(result ? "hotel索引库存在" : "hotel索引库不存在");
    }

    /**
     * 删除指定索引库
     */
    @Test
    void deleteIndexTest() throws IOException {
        // 删除指定的索引库
        AcknowledgedResponse response = client.indices()
                .delete(new DeleteIndexRequest("hotel"), RequestOptions.DEFAULT);
        // 查看是否成功
        System.out.println("response.isAcknowledged() = " + response.isAcknowledged());
    }

    // 索引库一旦创建，则不可修改，但可以添加mapping映射

    /**
     * 获取指定索引库
     */
    @Test
    void getIndexTest() throws IOException {
        // 获取指定索引
        GetIndexResponse response = client.indices()
                .get(new GetIndexRequest("hotel"), RequestOptions.DEFAULT);
    }

    /**
     * 刷新索引库
     */
    @Test
    void flushIndexTest() throws IOException {
        // 刷新索引库
        FlushResponse response = client.indices().flush(new FlushRequest("hotel"), RequestOptions.DEFAULT);
        // 检查是否成功
        System.out.println("response.getStatus() = " + response.getStatus());
    }
}