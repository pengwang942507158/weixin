package com.wp.weixin.model;

import com.thoughtworks.xstream.mapper.Mapper;

import java.util.Date;

/*
 * 常量类
 */
public class Constant {

    //基础配置
    public static final String TOKEN="51hgo";//token
    public static final String APP_ID="wx67f8508cf1b98513";//appID
    public static final String APPSECRET="4b54e66dbb79026de37cea3708250559";//appID

	//消息类型
	public static final String MSG_TYPE_TEXT="text";//文本
	public static final String MSG_TYPE_IMAGE="image";//图片
	public static final String MSG_TYPE_VOICE="voice";//语音
	public static final String MSG_TYPE_VIDEO="video";//视频
	public static final String MSG_TYPE_SHORTVIDEO="shortvideo";//短视频
	public static final String MSG_TYPE_LOCATION="location";//地理位置
	public static final String MSG_TYPE_LINK="link";//链接




	//接口url
    //获取access_token
	public static final String GET_ACCESS_TOKEN_URL ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //创建菜单
	public static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //设置行业
	public static final String SET_INDUSTRY_URL="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
    //获取设置的行业信息
    public static final String GET_INDUSTRY_URL="https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
    //发送模版消息
	public static final String SEND_TEMPLATE_MSG_URL="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";


}






