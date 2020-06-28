package com.facebook.presto.udf.utils;


import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class UdfHttpClient {

    public Integer post(ConfidenceQueryCredibilityRequest request) {

        System.out.println("可信度查询入参:" + JSON.toJSONString(request));

        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Post请求
        HttpPost httpPost = new HttpPost("http://192.168.3.62:19800/api/v1/mm/confidence/query");

        String jsonString = JSON.toJSONString(request);

        StringEntity entity = new StringEntity(jsonString, "UTF-8");

        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {

                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                String content = EntityUtils.toString(responseEntity);
                System.out.println("响应内容为:" + content);
                Map<String,Object> map = JSON.parseObject(content, Map.class);
                Object code = map.get("code");
                if(code != null && "0".equals(code.toString())){
                    Object data = map.get("data");
                    ConfidenceQueryCredibilityResponse resonse = JSON.parseObject(data.toString(), ConfidenceQueryCredibilityResponse.class);
                    System.out.println("可信值:" + JSON.toJSONString(resonse));
                    return resonse.getCredibility();
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("default.confidence:" + 1000);
        return 1000;
    }

    public static void main(String[] args){

        UdfHttpClient udfHttpClient = new UdfHttpClient();

        ConfidenceQueryCredibilityRequest request = new ConfidenceQueryCredibilityRequest();
        request.setDimensionId("1");
        request.setDsId("2");
        request.setTableName("3");
        request.setRowId("4");
        request.setColumnName("5");

        udfHttpClient.post(request);

    }
}
