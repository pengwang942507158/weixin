package com.wp.weixin.utils;

import java.io.Writer;
import java.util.*;

import com.baidu.aip.ocr.AipOcr;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wp.weixin.model.*;

/*
 * 封装返回给微信的xml数据
 */
public class FormatXmlResult {
    /*
     * 将对象转换为xml格式
     */
    public static String textMsgToXml(ReceiveXmlTextModel receiveXmlTextModel) {
      //修改根节点（微信要求的跟节点是“xml”，而使用XStream生成的xml的根节点默认是实体类名，所以要修改）
        xstream.alias("xml", receiveXmlTextModel.getClass());
        return xstream.toXML(receiveXmlTextModel);
    }

    
    //改写XStream中的方法（因为使用XStream将类解析为xml的时候是不会加cdata的，所以我们得改写，使它能加上cdata）
    public static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                boolean flag = true;
                protected void writeText(QuickWriter writer, String text) {
                    if (flag) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }else{
                        writer.write(text);
                    }
                }
            };
        }
    });



}
