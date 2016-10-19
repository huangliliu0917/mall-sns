/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jin on 2016/10/18.
 */
@Service
public class SensitiveServiceImpl implements SensitiveService {
    CloseableHttpClient httpClient=new DefaultHttpClient();
    @Override
    public boolean ContainSensitiveWords(String content) throws IOException {

        Boolean flag=checkFirstWay(content);
        if(flag==null){
            flag=checkSecondWay(content);
            if(flag==null){
                return true;
            }
        }else {
            return flag;
        }
        return false;
    }
    /**
     * 将HttpEntity 转换为Stirng
     * @param entity
     * @return
     * @throws IOException
     */
    private String getString(HttpEntity entity) throws IOException{
        String json="";
        if(entity!=null){
            InputStream inputStream=entity.getContent();
            json= IOUtils.toString(inputStream);
        }
        return json;

    }

    private Boolean checkFirstWay(String content) throws IOException{
        HttpPost httpPost=new HttpPost("http://www.67960.com/index/check");
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("text", content));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
        httpPost.setEntity(urlEncodedFormEntity);
        CloseableHttpResponse closeableHttpResponse =httpClient.execute(httpPost);
        HttpEntity entity=closeableHttpResponse.getEntity();
        String json=getString(entity);
        JSONObject object=JSONObject.parseObject(json);
        Object o=object.get("data");
        if(new Integer(0).equals(o)){
            return true;
        }
        return false;
    }

    private Boolean checkSecondWay(String content) throws IOException{
        HttpPost httpPost=new HttpPost("http://www.hoapi.com/index.php/Home/Api/check");
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("str", content));
        formparams.add(new BasicNameValuePair("token", "1bfc6c8a90c45c29ff4b8a3ec0d411ee"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
        httpPost.setEntity(urlEncodedFormEntity);
        CloseableHttpResponse closeableHttpResponse =httpClient.execute(httpPost);
        HttpEntity entity=closeableHttpResponse.getEntity();
        String json=getString(entity);
        JSONObject object=JSONObject.parseObject(json);
        Object o=object.get("status");
        if(new Boolean(true).equals(o)){
            return true;
        }
        return false;

//        CloseableHttpClient client = new DefaultHttpClient();
//
//        HttpGet httpGet=new HttpGet("http://www.hoapi.com/index.php/Home/Api/check?str=%E6%93%8D%E4%BD%A0%E5%A6%88&token=1bfc6c8a90c45c29ff4b8a3ec0d411ee");
//        HttpResponse response = client.execute(httpGet);
//        HttpEntity entity = response.getEntity();
//        String json=getString(entity);
//        return null;
    }


}
