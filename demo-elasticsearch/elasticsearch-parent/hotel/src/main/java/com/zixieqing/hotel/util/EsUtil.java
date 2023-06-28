package com.zixieqing.hotel.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * elasticsearch工具类
 *
 * <p>@author       : ZiXieqing</p>
 */


public class EsUtil {
    private static final String HOST = "http://192.168.46.128:9200";
    // private static final int PORT = 9200;

    public static RestHighLevelClient  getEsClient() {
        return new RestHighLevelClient(RestClient.builder(HttpHost.create(HOST)));
        // 还有一种方式
        // return new RestHighLevelClient(RestClient.builder(new HttpHost(HOST, PORT)));
    }
}
