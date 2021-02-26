package com.wp.weixin.utils;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wp.weixin.model.Constant;
import com.wp.weixin.model.ReceiveXmlTextModel;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/*
 * 解析从微信接受到xml,返回map集合
 */
public class ParseReceiveXmlUtil {
    public static ReceiveXmlTextModel parseXmlToModel(Document document){
    	ReceiveXmlTextModel receiveXmlTextModel=new ReceiveXmlTextModel();
        Map<String,String> map=new HashMap<String, String>();
        try {
            //获取文档的根节点
            Element root= document.getRootElement();
            System.out.println("root:"+root);
            //遍历跟节点下的所有子节点
            List<Element> list=root.elements();
           for (Element element : list) {
        	   map.put(element.getName(), element.getText());
		   }        
           if(Constant.MSG_TYPE_TEXT.equals(map.get("MsgType"))){//如果消息类型是文本
        	   receiveXmlTextModel.setToUserName(map.get("ToUserName"));
               receiveXmlTextModel.setFromUserName(map.get("FromUserName"));
               receiveXmlTextModel.setMsgId(map.get("MsgId"));
               receiveXmlTextModel.setMsgType(map.get("MsgType"));
               receiveXmlTextModel.setCreateTime(new Date().getTime());
               receiveXmlTextModel.setContent(map.get("Content"));         
           }
           
        /*    Iterator<?> iter =root.elementIterator();
            //利用反射，调用对象的set方法
            Class<?> clazz=Class.forName("com.hgo.model.ReceiveXmlTextModel");
            //创建实体类
            receiveXmlTextModel=(ReceiveXmlTextModel)clazz.newInstance();
            receiveXmlTextModel.getContent();
            while(iter.hasNext()){
               Element ele= (Element) iter.next();
               System.out.println(ele.getName());
               //获取实体类的属性
               Field file=clazz.getDeclaredField(ele.getName());
               System.out.println("file:"+file);
               //获取set方法
               Method method=clazz.getDeclaredMethod("set"+ele.getName(), file.getType());
               //调用set方法
               method.invoke(receiveXmlTextModel, ele.getText());
            }   */                  
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }     
        return receiveXmlTextModel;     
    }

/*	public static ReceiveXmlTextModel getMsgModel(Document document) {
		// TODO Auto-generated method stub
		ReceiveXmlTextModel ReceiveXmlTextModel=new ReceiveXmlTextModel();
		 Map<String,String> map= ParseReceiveXmlUtil.parseXmlToMap(document);
		 //Constant.MSG_TYPE_TEXT;
		 String ToUserName =map.get("ToUserName");
		 String FromUserName =map.get("FromUserName");
		 String MsgType =map.get("MsgType");
		 if(Constant.MSG_TYPE_TEXT.equals(MsgType)){//如果消息类型是文本
			 ReceiveXmlTextModel.setToUserName(toUserName);
		 }
		 return null;
	}*/

}
