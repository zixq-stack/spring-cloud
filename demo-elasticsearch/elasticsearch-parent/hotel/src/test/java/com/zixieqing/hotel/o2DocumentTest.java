package com.zixieqing.hotel;

import com.alibaba.fastjson.JSON;
import com.zixieqing.hotel.pojo.Hotel;
import com.zixieqing.hotel.pojo.HotelDoc;
import com.zixieqing.hotel.service.IHotelService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
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

/**
 * elasticsearch的文档测试
 * 规律：esClient.xxx(xxxRequest(IndexName, docId), RequestOptions.DEFAULT)
 *      其中 xxx 表示要进行的文档操作，如：
 *          index   新增文档
 *          delete  删除指定id文档
 *          get     获取指定id文档
 *          update  修改指定id文档的局部数据
 *
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest(classes = HotelApp.class)
public class o2DocumentTest {
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
     * 添加文档
     */
    @Test
    void addDocumentTest() throws IOException {

        // 1、准备要添加的文档json数据
        // 通过id去数据库获取数据
        Hotel hotel = service.getById(36934L);
        // 当数据库中定义的表结构和es中定义的字段mapping映射不一致时：将从数据库中获取的数据转成 es 中定义的mapping映射关系对象
        HotelDoc hotelDoc = new HotelDoc(hotel);

        // 2、准备request对象    指定 indexName+文档id
        IndexRequest request = new IndexRequest("hotel").id(hotel.getId().toString());

        // 3、把数据转成json
        request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);

        // 4、发起请求，正式在ES中添加文档    就是根据数据建立倒排索引，所以这里调研了index()
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        // 5、检查是否成功     使用下列任何一个API均可   若成功二者返回的结果均是 CREATED
        System.out.println("response.getResult() = " + response.getResult());
        System.out.println("response.status() = " + response.status());
    }

    /**
     * 根据id删除指定文档
     */
    @Test
    void deleteDocumentTest() throws IOException {
        // 1、准备request对象
        DeleteRequest request = new DeleteRequest("hotel", "36934");

        // 2、发起请求
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        // 查看是否成功   成功则返回 OK
        System.out.println("response.status() = " + response.status());
    }

    /**
     * 获取指定id的文档
     */
    @Test
    void getDocumentTest() throws IOException {
        // 1、获取request
        GetRequest request = new GetRequest("hotel", "36934");

        // 2、发起请求，获取响应对象
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        // 3、解析结果
        HotelDoc hotelDoc = JSON.parseObject(response.getSourceAsString(), HotelDoc.class);
        System.out.println("hotelDoc = " + hotelDoc);
    }

    /**
     * 修改指定索引库 和 文档id的局部字段数据
     * 全量修改是直接删除指定索引库下的指定id文档，然后重新添加相同文档id的文档即可
     */
    @Test
    void updateDocumentTest() throws IOException {
        // 1、准备request对象
        UpdateRequest request = new UpdateRequest("hotel", "36934");

        // 2、要修改那个字段和值      注：参数是 key, value 形式 中间是 逗号
        request.doc(
                "price",500
        );

        // 3、发起请求
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        // 查看结果 成功则返回 OK
        System.out.println("response.status() = " + response.status());
    }
}