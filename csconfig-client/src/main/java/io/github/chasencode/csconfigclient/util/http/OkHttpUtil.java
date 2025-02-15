package io.github.chasencode.csconfigclient.util.http;

import okhttp3.*;

import java.util.concurrent.TimeUnit;
//@Slf4j
public class OkHttpUtil implements HttpUtil {
    final static MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client;

    public OkHttpUtil(int timeout) {
        client = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(16, 60, TimeUnit.SECONDS))
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public String post(String requestString, String url) {
//        log.debug(" ===> post  url = {}, requestString = {}", requestString, url);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(requestString, JSONTYPE))
                .build();
        try {
            String respJson = client.newCall(request).execute().body().string();
//            log.debug(" ===> respJson = " + respJson);
            return respJson;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String get(String url) {
//        log.debug(" ===> get url = " + url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            String respJson = client.newCall(request).execute().body().string();
            if (respJson == null || respJson.isEmpty()) {
                throw new RuntimeException("Response is empty");
            }
//            log.debug(" ===> respJson = " + respJson);
            return respJson;
        } catch (Exception e) {
//            log.error(" ===> Exception in get request", e);
            throw new RuntimeException(e);
        }
    }
}
