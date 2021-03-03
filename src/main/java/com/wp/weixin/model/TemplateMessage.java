package com.wp.weixin.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

@XStreamAlias("xml")
public class TemplateMessage {

    //接收者openid
	private String  touser;
	//模板ID
    private String  template_id;
    //模板跳转链接（海外帐号没有跳转能力）
    private String  url;
    //跳小程序所需数据，不需跳小程序可不用传该数据
    private String  miniprogram;
    //模板数据
    private Map<String,Object>  data;


    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(String miniprogram) {
        this.miniprogram = miniprogram;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
