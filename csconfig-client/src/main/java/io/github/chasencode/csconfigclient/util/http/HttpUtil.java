package io.github.chasencode.csconfigclient.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.SneakyThrows;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public interface HttpUtil {
//    Logger log = LoggerFactory.getLogger(HttpInvoker.class);

    HttpUtil Default = new OkHttpUtil(500);

    String post(String requestString, String url);
    String get(String url);
    @SneakyThrows
    static <T> T httpGet(String url, Class<T> clazz) {
//        log.debug(" =====>>>>>> httpGet: " + url);
        String respJson = Default.get(url);
//        log.debug(" =====>>>>>> response: " + respJson);
        return JSON.parseObject(respJson, clazz);
    }

    @SneakyThrows
    static <T> T httpGet(String url, TypeReference<T> typeReference) {
//        log.debug(" =====>>>>>> httpGet: " + url);
        String respJson = Default.get(url);
//        log.debug(" =====>>>>>> response: " + respJson);
        return JSON.parseObject(respJson, typeReference);
    }

    @SneakyThrows
    static <T> T httpPost(String requestString,String url, Class<T> clazz) {
//        log.debug(" =====>>>>>> httpGet: " + url);
        String respJson = Default.post(requestString, url);
//        log.debug(" =====>>>>>> response: " + respJson);
        return JSON.parseObject(respJson, clazz);
    }
}
