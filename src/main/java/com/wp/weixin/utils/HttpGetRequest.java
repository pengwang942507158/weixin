package com.wp.weixin.utils;


import com.sun.deploy.net.HttpResponse;
import sun.net.www.http.HttpClient;

public class HttpGetRequest {
	public static String get(String url) {
		try {
			HttpGet request = new HttpGet(url);
			HttpClient httpClient = new DefaultHttpClient();
			// 执行http get请求					
			HttpResponse httpResponse = httpClient.execute(request);
			// 根据请求码来判断请求是否成功
			String result = "";
			int resCode=httpResponse.getStatusLine().getStatusCode();
			String resMag=httpResponse.getStatusLine().getReasonPhrase();
			if (resCode == 200) {
				System.out.println("调用成功：状态码："+resCode+",状态信息："+resMag);
				result = EntityUtils.toString(httpResponse.getEntity());
				return result;
			}else{
				System.out.println("错误码："+resCode+",错误原因："+resMag);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
