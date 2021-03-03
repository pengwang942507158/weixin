package com.wp.weixin.utils;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpRequest {


    public static String get(String url) {
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(url, String.class);
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return responseEntity.getBody();
        }else {
            return null;
        }

	}

    public static String post(String url, String jsonData){
        RestTemplate client = new RestTemplate();
        JSONObject jsonObject = new Gson().fromJson(jsonData, JSONObject.class);
        JSONObject resJson = client.postForEntity(url, jsonObject, JSONObject.class).getBody();
        System.out.println(resJson.toString());
        return resJson.toString();
    }

}
