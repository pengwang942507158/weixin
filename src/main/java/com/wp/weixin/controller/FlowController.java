package com.wp.weixin.controller;

import java.util.Date;

import com.wp.weixin.model.Constant;
import com.wp.weixin.model.ReceiveXmlTextModel;
import com.wp.weixin.utils.FormatXmlResult;
import com.wp.weixin.utils.ParseReceiveXmlUtil;
import org.dom4j.Document;


/*
 * 微信流程控制类
 */
public class FlowController {

	/*
	 * 图灵机器人流程控制
	 */
	public String tuLingFlow(Document document) {
		// 解析微信传过来的xml数据，转为对象
		ReceiveXmlTextModel receiveXmlTextModel = ParseReceiveXmlUtil
				.parseXmlToModel(document);
		// 调用图灵机器人接口模块，获取结果
		System.out.println(receiveXmlTextModel.getContent());
		String tuLingResult = new TulingController()
				.getTulingResult(receiveXmlTextModel.getContent());
		System.out.println("tuLingResult:" + tuLingResult);
		// 封装返回微信的结果数据
		if (tuLingResult != null) {
			ReceiveXmlTextModel resultXmlTextModel = new ReceiveXmlTextModel();
			resultXmlTextModel.setToUserName(receiveXmlTextModel
					.getFromUserName());
			resultXmlTextModel.setFromUserName(receiveXmlTextModel
					.getToUserName());
			resultXmlTextModel.setCreateTime(new Date().getTime());
			resultXmlTextModel.setMsgId(receiveXmlTextModel.getMsgId());
			resultXmlTextModel.setMsgType(Constant.MSG_TYPE_TEXT);
			resultXmlTextModel.setContent(tuLingResult);
			String xmlResult = FormatXmlResult.textMsgToXml(resultXmlTextModel);
			System.out.println("xmlResult:" + xmlResult);
			return xmlResult;
		} else {
			return null;
		}
	}

}
