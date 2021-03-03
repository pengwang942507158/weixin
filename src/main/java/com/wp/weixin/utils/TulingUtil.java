package com.wp.weixin.utils;

import com.google.gson.Gson;

import java.util.Map;

public class TulingUtil {

    public static String getTulingResult(String info){
        String url="http://www.tuling123.com/openapi/api?key=7472facb44564e669d53861fbd7a71d2&info="+info;
        String tlResult=HttpRequest.get(url);
        System.out.println("tlResult:"+tlResult);
        //解析图灵机器人返回的json数据
        if(null!=tlResult&&!tlResult.equals("")){
            Map tlMap = new Gson().fromJson(tlResult, Map.class);

            //获取文本内容
            String tlResultText=String.valueOf(tlMap.get("text"));
            return tlResultText;
        }else{
            return null;
        }
    }
}
