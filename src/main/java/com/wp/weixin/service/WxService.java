package com.wp.weixin.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


import com.google.gson.Gson;
import com.wp.weixin.model.*;
import com.wp.weixin.utils.HttpRequest;
import com.wp.weixin.utils.TulingUtil;
import com.wp.weixin.utils.Util;
import com.wp.weixin.utils.XmlUtil;


import org.springframework.stereotype.Service;

@Service
public class WxService {

	private static final String APPKEY="1fec136dbd19f44743803f89bd55ca62";
	private static final String GET_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//微信公众号
	private static final String APPID="wxb6777fffdf5b64a4";
	private static final String APPSECRET="6b55d3bb4d9c5373c8a30915d900ca13";
	//百度AI
	public static final String APP_ID = "11519092";
	public static final String API_KEY = "q3TlGWWqEBG9uGvlFIBtpvY5";
	public static final String SECRET_KEY = "A14W5VRNG8my1GXYYAyNND0RjzBwxI8A";
	
	//用于存储token
	private static AccessToken at;

    /**
     * 向处暴露的获取token的方法
     * @return
     * by 罗召勇 Q群193557337
     */
    public  String getAccessToken() {
        if(at==null||at.isExpired()) {
            getToken();
        }
        return at.getAccessToken();
    }
	
	/**
	 * 获取token
	 * by 罗召勇 Q群193557337
	 */
	private void getToken() {
		String url = Constant.GET_ACCESS_TOKEN_URL.replace("APPID", Constant.APP_ID).replace("APPSECRET", Constant.APPSECRET);
		String resultStr = Util.get(url);
        System.out.println(resultStr);
        Map resultMap = new Gson().fromJson(resultStr,Map.class);
        String access_token = String.valueOf(resultMap.get("access_token"));
        String expireIn = String.valueOf(resultMap.get("expires_in"));
		//创建token对象,并存起来。
		at = new AccessToken(access_token, expireIn);
	}




	/**
	 * 用于处理所有的事件和消息的回复
	 * @param requestMap
	 * @return 返回的是xml数据包
	 * by 罗召勇 Q群193557337
	 */
	public String getRespose(Map<String, String> requestMap) {
		BaseMessage msg=null;
		String msgType = requestMap.get("MsgType");
		switch (msgType) {
			//处理文本消息
			case "text":
				msg=dealTextMessage(requestMap);
				break;
			case "image":
				//msg=dealImage(requestMap);
				break;
			case "voice":
				
				break;
			case "video":
				
				break;
			case "shortvideo":
				
				break;
			case "location":
				
				break;
			case "link":
				
				break;
			case "event":
				msg = dealEvent(requestMap);
				break;
			default:
				break;
		}
		//把消息对象处理为xml数据包
		if(msg!=null) {
			return XmlUtil.beanToXml(msg);
		}
		return null;
	}

	/**
	 * 进行图片识别
	 * @param requestMap
	 * @return
	 * by 罗召勇 Q群193557337
	 */
	/*private static BaseMessage dealImage(Map<String, String> requestMap) {
		// 初始化一个AipOcr
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
		// 调用接口
		String path = requestMap.get("PicUrl");
		
		//进行网络图片的识别
		org.json.JSONObject res = client.generalUrl(path, new HashMap<String,String>());
		String json = res.toString();
		//转为jsonObject
		JSONObject jsonObject = JSONObject.fromObject(json);
		JSONArray jsonArray = jsonObject.getJSONArray("words_result");
		Iterator<JSONObject> it = jsonArray.iterator();
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			JSONObject next = it.next();
			sb.append(next.getString("words"));
		}
		return new TextMessage(requestMap, sb.toString());
	}*/

	/**
	 * 处理事件推送
	 * @param requestMap
	 * @return
	 * by 罗召勇 Q群193557337
	 */
	private BaseMessage dealEvent(Map<String, String> requestMap) {
		String event = requestMap.get("Event");
		switch (event) {
			case "CLICK":
				return dealClick(requestMap);
			case "VIEW":
				return dealView(requestMap);
			default:
				break;
		}
		return null;
	}

	/**
	 * 处理view类型的按钮的菜单
	 * @param requestMap
	 * @return
	 * by 罗召勇 Q群193557337
	 */
	private BaseMessage dealView(Map<String, String> requestMap) {
		
		return null;
	}

	/**
	 * 处理click菜单
	 * @param requestMap
	 * @return
	 * by 罗召勇 Q群193557337
	 */
	private BaseMessage dealClick(Map<String, String> requestMap) {
		String key = requestMap.get("EventKey");
		switch (key) {
			//点击一菜单点
			case "1":
				//处理点击了第一个一级菜单
				return new TextMessage(requestMap, "你点了一点第一个一级菜单");
			case "32":
				//处理点击了第三个一级菜单的第二个子菜单
				break;
			default:
				break;
		}
		return null;
	}



	/**
	 * 处理文本消息
	 * @param requestMap
	 * @return
	 * by 罗召勇 Q群193557337
	 */
	private BaseMessage dealTextMessage(Map<String, String> requestMap) {
		//用户发来的内容
		String msg = requestMap.get("Content");
        if(msg.equals("余额")) {
            TextMessage tm = new TextMessage(requestMap, "您的余额为：0");
            return tm;
        }
		if(msg.equals("会员")) {
			List<Article> articles = new ArrayList<>();
			articles.add(new Article("湘云按摩", "会员会有更多的优惠活动！", "http://mmbiz.qpic.cn/mmbiz_jpg/dtRJz5K066YczqeHmWFZSPINM5evWoEvW21VZcLzAtkCjGQunCicDubN3v9JCgaibKaK0qGrZp3nXKMYgLQq3M6g/0", "http://www.baidu.com"));
			NewsMessage nm = new NewsMessage(requestMap, articles);
			return nm;
		}
		if(msg.equals("登录")) {
			String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb6777fffdf5b64a4&redirect_uri=http://www.6sdd.com/weixin/GetUserInfo&response_type=code&scope=snsapi_userinfo#wechat_redirect";
			TextMessage tm = new TextMessage(requestMap, "点击<a href=\""+url+"\">这里</a>登录");
			return tm;
		}
		//调用方法返回聊天的内容
        String resp=TulingUtil.getTulingResult(msg);
		//String resp = chat(msg);
		TextMessage tm = new TextMessage(requestMap, resp);
		return tm;
	}

	/**
	 * 上传临时素材
	 * @param path	上传的文件的路径
	 * @param type	上传的文件类型
	 * @return
	 * by 罗召勇 Q群193557337
	 */
	public String upload(String path,String type) {
		File file = new File(path);
		//地址
		String url="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		url = url.replace("ACCESS_TOKEN", getAccessToken()).replace("TYPE", type);
		try {
			URL urlObj = new URL(url);
			//强转为案例连接
			HttpsURLConnection conn = (HttpsURLConnection) urlObj.openConnection();
			//设置连接的信息
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			//设置请求头信息
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "utf8");
			//数据的边界
			String boundary = "-----"+System.currentTimeMillis();
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			//获取输出流
			OutputStream out = conn.getOutputStream();
			//创建文件的输入流
			InputStream is = new FileInputStream(file);
			//第一部分：头部信息
			//准备头部信息
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition:form-data;name=\"media\";filename=\""+file.getName()+"\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			out.write(sb.toString().getBytes());
			System.out.println(sb.toString());
			//第二部分：文件内容
			byte[] b = new byte[1024];
			int len;
			while((len=is.read(b))!=-1) {
				out.write(b, 0, len);
			}
			is.close();
			//第三部分：尾部信息
			String foot = "\r\n--"+boundary+"--\r\n";
			out.write(foot.getBytes());
			out.flush();
			out.close();
			//读取数据
			InputStream is2 = conn.getInputStream();
			StringBuilder resp = new StringBuilder();
			while((len=is2.read(b))!=-1) {
				resp.append(new String(b,0,len));
			}
			return resp.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取带参数二维码的ticket
	 * @return
	 * by 罗召勇 Q群193557337
	 */
	public String getQrCodeTicket() {
		String at = getAccessToken();
		String url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+at;
		//生成临时字符二维码
		String data="{\"expire_seconds\": 600, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"llzs\"}}}";
		String resultStr = Util.post(url, data);
        Map resultMap = new Gson().fromJson(resultStr, Map.class);
        String ticket = String.valueOf(resultMap.get("ticket"));

        //String ticket = JSONObject.fromObject(result).getString("ticket");
		return ticket;
	}
	
	/**
	 * 获取用户的基本信息
	 * @return
	 * by 罗召勇 Q群193557337
	 */
	public String getUserInfo(String openid) {
		String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url = url.replace("ACCESS_TOKEN", getAccessToken()).replace("OPENID", openid);
		String result = Util.get(url);
		return result;
	}



    /**
     * 发送模板消息
     * by 罗召勇 Q群193557337
     */
    public String sendTemplateMessage() {
        Gson gson = new Gson();
        String url=Constant.SEND_TEMPLATE_MSG_URL.replace("ACCESS_TOKEN",getAccessToken());

        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setTouser("owQ726Yhjp5PD1WFHkFMeOD8gZcs");
        templateMessage.setTemplate_id("VcUh2amycqEH1vI0Y64c44Da3iPIfVvT87UZQR3OnlA");
        templateMessage.setUrl("www.baidu.com");

        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> first = new HashMap<>();
        first.put("value","您有新的反馈信息啦!");
        first.put("color","#173177!");
        hashMap.put("first",first);

        HashMap<String, Object> company = new HashMap<>();
        company.put("value","51HG公司");
        company.put("color","#1f1f1f!");
        hashMap.put("company",company);

        HashMap<String, Object> result = new HashMap<>();
        result.put("value","面试通过");
        result.put("color","#1f1f1f!");
        hashMap.put("result",result);

        HashMap<String, Object> time = new HashMap<>();
        time.put("value","2020年11月11日");
        time.put("color","#1f1f1f!");
        hashMap.put("time",time);

        HashMap<String, Object> remark = new HashMap<>();
        remark.put("value","请和本公司人事专员联系！");
        remark.put("color","#1f1f1f!");
        hashMap.put("remark",remark);

        templateMessage.setData(hashMap);


        System.out.println(gson.toJson(templateMessage));
        return HttpRequest.post(url,gson.toJson(templateMessage));
    }

}
