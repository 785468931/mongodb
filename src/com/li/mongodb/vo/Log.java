package com.li.mongodb.vo;


public class Log  {
	/**
	 * 
	 */
	private String  phoneNo;
	private String orderNo;
	private String createTime;
	private String sendContent;
	private String retContent;
	private String errorCode;
	private String errorMessage;
	private String encryptSendContent;
	private String encryptRetContent;
	
	
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSendContent() {
		return sendContent;
	}
	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}
	public String getRetContent() {
		return retContent;
	}
	public void setRetContent(String retContent) {
		this.retContent = retContent;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getEncryptSendContent() {
		return encryptSendContent;
	}
	public void setEncryptSendContent(String encryptSendContent) {
		this.encryptSendContent = encryptSendContent;
	}
	public String getEncryptRetContent() {
		return encryptRetContent;
	}
	public void setEncryptRetContent(String encryptRetContent) {
		this.encryptRetContent = encryptRetContent;
	}
	
	
}
