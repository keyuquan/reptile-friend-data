package com.reptile.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * http 请求工具类
 */
public class HttpUtils {


    /**
     * 发送 get 请求: 参数形式: url?name
     *
     * @param url 请求路径
     * @param map 请求参数
     */
    public static String doGet(String url, Map<String, Object> map, String accessToken) {
        // 构造请求
        HttpEntityEnclosingRequestBase httpEntity = new HttpEntityEnclosingRequestBase() {
            @Override
            public String getMethod() {
                return "GET";
            }
        };
        if (StringUtils.isNotEmpty(accessToken)) {
            httpEntity.setHeader("token", accessToken);
        }
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            client = HttpClientBuilder.create().build();
            URIBuilder uriBuilder = new URIBuilder(url);
            for (String key : map.keySet()) {
                uriBuilder.setParameter(key, new JSONObject(map).getString(key));
            }
            httpEntity.setURI(uriBuilder.build());
            response = client.execute(httpEntity);
            if (response != null) {
                String entity = EntityUtils.toString(response.getEntity(), "GBK");
                JSONObject json = JSONObject.parseObject(entity);
                return json.toString();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 发送 get 请求: 参数形式: url?name
     *
     * @param url 请求路径
     * @param map 请求参数
     */
    public static String doGet(String url, Map<String, Object> map, String accessToken, Map<String, Object> header) {
        // 构造请求
        HttpEntityEnclosingRequestBase httpEntity = new HttpEntityEnclosingRequestBase() {
            @Override
            public String getMethod() {
                return "GET";
            }
        };
        if (StringUtils.isNotEmpty(accessToken)) {
            httpEntity.setHeader("token", accessToken);
        }
        if (header.size()>0){
            for (String key : header.keySet()) {
                httpEntity.setHeader(key, new JSONObject(header).getString(key));
            }
        }
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            client = HttpClientBuilder.create().build();
            URIBuilder uriBuilder = new URIBuilder(url);
            for (String key : map.keySet()) {
                uriBuilder.setParameter(key, new JSONObject(map).getString(key));
            }
            httpEntity.setURI(uriBuilder.build());
            response = client.execute(httpEntity);
            if (response != null) {
                String entity = EntityUtils.toString(response.getEntity(), "GBK");
                JSONObject json = JSONObject.parseObject(entity);
                return json.toString();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 发送 POST 请求
     *
     * @param url
     * @param map
     * @return
     */
    public static String doPost(String url, Map<String, Object> map, String accessToken) {
        // 构造请求
        HttpEntityEnclosingRequestBase httpEntity = new HttpEntityEnclosingRequestBase() {
            @Override
            public String getMethod() {
                return "POST";
            }
        };
        if (StringUtils.isNotEmpty(accessToken)) {
            httpEntity.setHeader("Access-Token", accessToken);
        }
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            client = HttpClientBuilder.create().build();
            httpEntity.setURI(URI.create(url));
            httpEntity.setEntity(new StringEntity(JSONObject.toJSONString(map), ContentType.APPLICATION_JSON));
            response = client.execute(httpEntity);
            if (response != null) {
                String entity = EntityUtils.toString(response.getEntity(), "GBK");
                JSONObject json = JSONObject.parseObject(entity);
                return json.toString();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}