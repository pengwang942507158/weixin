package com.wp.weixin.model;

/*
 * 从微信端接受文件类型为文本（text）的实体类
 */
public class ReceiveXmlTextModel extends ReceiveXmBaselModel{
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }

}
