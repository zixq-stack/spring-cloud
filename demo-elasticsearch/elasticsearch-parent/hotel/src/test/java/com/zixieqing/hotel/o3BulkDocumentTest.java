package com.zixieqing.hotel;

import com.alibaba.fastjson.JSON;
import com.zixieqing.hotel.pojo.Hotel;
import com.zixieqing.hotel.pojo.HotelDoc;
import com.zixieqing.hotel.service.IHotelService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * elasticsearch 批量操作文档测试
 * 规律：EsClient.bulk(new BulkRequest()
 *                    .add(xxxRequest("indexName").id().source())
 *                    , RequestOptions.DEFAULT)
 * 其中：xxx 表示要进行的操作，如
 *      index   添加
 *      delete  删除
 *      get     查询
 *      update  修改
 *
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest(classes = HotelApp.class)
public class o3BulkDocumentTest {
    @Autowired
    private IHotelService service;

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
     * 批量添加文档数据到es中
     */
    @Test
    void bulkAddDocumentTest() throws IOException {
        // 1、去数据库批量查询数据
        List<Hotel> hotels = service.list();

        // 2、将数据库中查询的数据转成 es 的mapping需要的对象
        BulkRequest request = new BulkRequest();
        for (Hotel hotel : hotels) {
            HotelDoc hotelDoc = new HotelDoc(hotel);
            // 批量添加文档数据到es中
            request.add(new IndexRequest("hotel")
                    .id(hotelDoc.getId().toString())
                    .source(JSON.toJSONString(hotelDoc), XContentType.JSON));
        }

        // 3、发起请求
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        // 检查是否成功   成功则返回OK
        System.out.println("response.status() = " + response.status());
    }

    /**
     * 批量删除es中的文档数据
     */
    @Test
    void bulkDeleteDocumentTest() throws IOException {
        // 1、准备要删除数据的id
        List<Hotel> hotels = service.list();

        // 2、准备request对象
        BulkRequest request = new BulkRequest();
        for (Hotel hotel : hotels) {
            // 根据批量数据id 批量删除es中的文档
            request.add(new DeleteRequest("hotel").id(hotel.getId().toString()));
        }

        // 3、发起请求
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        // 检查是否成功       成功则返回 OK
        System.out.println("response.status() = " + response.status());
    }

    // 批量获取和批量修改是同样的套路  批量获取还可以使用 mget 这个API


    /**
     * mget批量获取
     */
    @Test
    void mgetTest() throws IOException {
        List<Hotel> hotels = service.list();

        // 1、准备request对象
        MultiGetRequest request = new MultiGetRequest();
        for (Hotel hotel : hotels) {
            // 添加get数据    必须指定index 和 文档id，可以根据不同index查询
            request.add("hotel", hotel.getId().toString());
        }

        // 2、发起请求，获取响应
        MultiGetResponse responses = client.mget(request, RequestOptions.DEFAULT);
        for (MultiGetItemResponse response : responses) {
            GetResponse resp = response.getResponse();
            // 如果存在则打印响应信息
            if (resp.isExists()) {
                System.out.println("获取到的数据= " + resp.getSourceAsString());
            }
        }
    }
}