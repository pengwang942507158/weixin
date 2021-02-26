package com.wp.weixin.controller;

import net.sf.json.JSONObject;

import com.hgo.tuling.HttpGetRequest;

/*
 * 图灵机器人流程控制类
 */
public class TulingController {
	public String getTulingResult(String info){
		String url="http://www.tuling123.com/openapi/api?key=7472facb44564e669d53861fbd7a71d2&info="+info;
		String tlResult=HttpGetRequest.get(url);
		System.out.println("tlResult:"+tlResult);
		//解析图灵机器人返回的json数据
		if(null!=tlResult&&!tlResult.equals("")){
			JSONObject json=JSONObject.fromObject(tlResult);
			System.out.println(json);
			//获取文本内容
			tlResult=json.getString("text");
			System.out.println("结果："+tlResult);
			return tlResult;			
		}else{
			return null;
		}
	}

}
