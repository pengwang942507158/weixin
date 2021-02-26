package com.wp.weixin.model;

/*
 * 从微信端接受xml的实体类父类
 */
public class ReceiveXmBaselModel {

	private String ToUserName;
	private String FromUserName;
	private long CreateTime;
	private String MsgType;
	private String MsgId;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		this.CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		this.MsgId = msgId;
	}

}
